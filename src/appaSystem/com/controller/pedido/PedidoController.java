package appaSystem.com.controller.pedido;

import appaSystem.com.kernel.Acao;
import appaSystem.com.model.Pedido.Pedido;
import appaSystem.com.model.Pedido.PedidoModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoController {
    
    private List<Pedido> pedidos;
    private int id;
    
    public PedidoController(int id){
        pedidos = new ArrayList<>();
        this.id = id;
    }
    
    public List<Pedido> consulta(String condicao){
        
        PedidoModel pedido = new PedidoModel();
        return pedido.consultar(this.id, condicao);
        
    }
    
    public List<Pedido> salvar(Pedido pedido, Acao acao){
        
        PedidoModel pedidoM = new PedidoModel();
        
        if(acao.ordinal() == 1){
            return pedidoM.inserirPedido(pedido);
        }else if(acao.ordinal() == 2){
            return pedidoM.alterarPedido(pedido);
        }else{
            JOptionPane.showMessageDialog(null, "Não foi possível determinar qual a ação a ser feita para esse pedido!");
            return null;
        }
        
    }
    
    public void excluir(int id){
        
        PedidoModel pm = new PedidoModel();
        pm.excluirPedido(id);
        
    }
    
    public void liberar(){
        
        PedidoModel pm = new PedidoModel();
        pm.liberarPedido(this.id);
        
    }

    public void cancelar(){
        
        PedidoModel pm = new PedidoModel();
        pm.cancelarPedido(this.id);
        
    }
    
}
