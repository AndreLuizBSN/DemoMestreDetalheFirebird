package appaSystem.com.model.Pedido;

import appaSystem.com.db.Conexao;
import appaSystem.com.db.DadosConexao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoModel {

    Conexao con = new Conexao();
    DadosConexao dcon = new DadosConexao();

    public List<Pedido> consultar(int id, String condicao){
        
        List<Pedido> pedidos = new ArrayList<>();

        Pedido pedido = new Pedido();

        //Carregar o cabeçalho
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        String sql = "SELECT * FROM PEDIDO";

        if(id > 0){
            sql += " WHERE ID = "+id;
            if(!condicao.equals("") || !condicao.isEmpty()){
                sql += " AND "+condicao;
            }
        }else if(!condicao.equals("") || !condicao.isEmpty()){
            sql += " WHERE "+condicao;
        }
        
        
        con.executeConsulta(sql);

        try{
            while(con.rs.next()){

                pedido = new Pedido();
                
                pedido.setId(Integer.parseInt(con.rs.getString("ID")));
                pedido.setNomeCliente(con.rs.getString("NOME_CLIENTE"));
                pedido.setCondicaoPagamentoId(Integer.parseInt(con.rs.getString("CONDICAO_PAGAMENTO_ID")));
                pedido.setFormaPagamentoId(Integer.parseInt(con.rs.getString("FORMA_PAGAMENTO_ID")));
                pedido.setQtdeItens(Integer.parseInt(con.rs.getString("QTDE_ITENS")));
                pedido.setValorFrete(Double.parseDouble(con.rs.getString("VALOR_FRETE")));
                pedido.setValorDesconto(Double.parseDouble(con.rs.getString("VALOR_DESCONTO")));
                pedido.setValorTotal(Double.parseDouble(con.rs.getString("VALOR_TOTAL")));
                if(Integer.parseInt(con.rs.getString("POSICAO"))==0){
                    pedido.setPedidoPosicao(PedidoPosicao.ABERTO);
                }else if(Integer.parseInt(con.rs.getString("POSICAO"))==1){
                    pedido.setPedidoPosicao(PedidoPosicao.LIBERADO);
                }else{
                    pedido.setPedidoPosicao(PedidoPosicao.CANCELADO);
                }

                pedidos.add(pedido);

            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados para exibir no cabelalho!\n"+ex);
        }

        con.desconecta();

        return pedidos;

    }
    
    public List<Pedido> alterarPedido(Pedido pedido){
        
        PedidoItemModel pim = new PedidoItemModel();
        List<PedidoItem> pedidoItens = pim.consultar(pedido.getId());
        int quantidade = 0;
        double valorTotal = 0;
        
        for (PedidoItem pedidoIten : pedidoItens) {
            quantidade += pedidoIten.getQuantidade();
            valorTotal += pedidoIten.getValorTotal();
        }
        
        valorTotal += pedido.getValorFrete();
        valorTotal -= pedido.getValorDesconto();
        
        int posicaoInt;
        if(pedido.getPedidoPosicao().equals(PedidoPosicao.ABERTO)){
            posicaoInt = 0;
        }else if(pedido.getPedidoPosicao().equals(PedidoPosicao.LIBERADO)){
            posicaoInt = 1;
        }else{
            posicaoInt = 2;
        }
        
        
        String sql = "UPDATE PEDIDO SET "
                +" NOME_CLIENTE = '"+pedido.getNomeCliente()+"',"
                +" CONDICAO_PAGAMENTO_ID = "+pedido.getCondicaoPagamentoId()+","
                +" FORMA_PAGAMENTO_ID = "+pedido.getFormaPagamentoId()+","
                +" VALOR_FRETE = "+pedido.getValorFrete()+","
                +" VALOR_DESCONTO = "+pedido.getValorDesconto()+","
                +" QTDE_ITENS = "+quantidade+","
                +" VALOR_TOTAL = "+valorTotal+", "
                +" POSICAO = '"+posicaoInt+"' "
                +" WHERE ID = "+pedido.getId();
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
        return consultar(pedido.getId(),"");
        
    }
    
    public List<Pedido> inserirPedido(Pedido pedido){
        
        String sql = "INSERT INTO PEDIDO (NOME_CLIENTE, CONDICAO_PAGAMENTO_ID"
                +", FORMA_PAGAMENTO_ID, VALOR_FRETE, VALOR_DESCONTO, QTDE_ITENS, POSICAO"
                + ", VALOR_TOTAL) VALUES ("
                +"'"+pedido.getNomeCliente()+"',"
                +" "+pedido.getCondicaoPagamentoId()+","
                +" "+pedido.getFormaPagamentoId()+","
                +" "+pedido.getValorFrete()+","
                +" "+pedido.getValorDesconto()+","
                +" "+pedido.getQtdeItens()+","
                +" "+pedido.getPedidoPosicao().ordinal()+","
                +" "+pedido.getValorTotal()+") ";
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        sql = "SELECT MAX(ID) AS ID FROM PEDIDO";
        
        con.executeConsulta(sql);
        int id = 0;
        try{
            while(con.rs.next()){
                id = Integer.parseInt(con.rs.getString("ID"));
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar os dados após inserir o pedido!\n"+ex);
        }
        con.desconecta();
        
        if(id != 0){
            return consultar(id,"");
        }else{
            return null;
        }
    }
    
    public void excluirPedido(int id){
        
        String sql = "DELETE FROM PEDIDO "
                +" WHERE ID = "+id+" ";
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
    }

    public void liberarPedido(int id){
        
        String sql = "UPDATE PEDIDO SET POSICAO = 1 "
                +" WHERE ID = "+id+" ";
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
    }
    
    public void cancelarPedido(int id){
        
        String sql = "UPDATE PEDIDO SET POSICAO = 2 "
                +" WHERE ID = "+id+" ";
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
    }
    
}
