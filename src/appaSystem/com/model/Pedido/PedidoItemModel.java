package appaSystem.com.model.Pedido;

import appaSystem.com.db.Conexao;
import appaSystem.com.db.DadosConexao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoItemModel {

    Conexao con = new Conexao();
    DadosConexao dcon = new DadosConexao();

    public List<PedidoItem> consultar(int pedidoId){
        
        List<PedidoItem> pedidoItens = new ArrayList<>();

        PedidoItem pedidoItem;

        //Carregar o cabeçalho
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        String sql = "SELECT * FROM PEDIDO_ITEM WHERE PEDIDO_ID = "+pedidoId;

        con.executeConsulta(sql);

        try{
            while(con.rs.next()){

                pedidoItem = new PedidoItem();
                
                pedidoItem.setId(Integer.parseInt(con.rs.getString("ID")));
                pedidoItem.setPedidoId(Integer.parseInt(con.rs.getString("PEDIDO_ID")));
                pedidoItem.setProduto(con.rs.getString("PRODUTO"));
                pedidoItem.setQuantidade(Integer.parseInt(con.rs.getString("QUANTIDADE")));
                pedidoItem.setValorUnitario(Double.parseDouble(con.rs.getString("VALOR_UNITARIO")));
                pedidoItem.setValorTotal(Double.parseDouble(con.rs.getString("VALOR_TOTAL")));
                
                pedidoItens.add(pedidoItem);
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados para exibir no cabelalho!\n"+ex);
        }

        con.desconecta();

        return pedidoItens;

    }
    
    public void alterarPedidoItem(PedidoItem pedidoItem){
        
        String sql = "UPDATE PEDIDO_ITEM SET "
                +" PRODUTO = '"+pedidoItem.getProduto()+"',"
                +" QUANTIDADE = "+pedidoItem.getQuantidade()+","
                +" VALOR_UNITARIO = "+pedidoItem.getValorUnitario()+","
                +" VALOR_TOTAL = "+pedidoItem.getValorTotal()+" "
                +" WHERE ID = "+pedidoItem.getId();
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
    }
    
    public void inserirPedidoItem(PedidoItem pedidoItem){
        
        String sql = "INSERT INTO PEDIDO_ITEM (PEDIDO_ID, PRODUTO"
                +", QUANTIDADE, VALOR_UNITARIO, VALOR_TOTAL) VALUES ("
                +"'"+pedidoItem.getPedidoId()+"',"
                +"'"+pedidoItem.getProduto()+"',"
                +" "+pedidoItem.getQuantidade()+","
                +" "+pedidoItem.getValorUnitario()+","
                +" "+pedidoItem.getValorTotal()+") ";
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
    }
    
    public void excluirPedidoItem(int id, int pedidoId){
        
        String sql = "DELETE FROM PEDIDO_ITEM "
                +" WHERE ID = "+id+" AND PEDIDO_ID = "+pedidoId;
        
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        con.executeAtualizacao(sql);
        
        con.desconecta();
        
    }

}
