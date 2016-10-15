package appaSystem.com.controller.pedido;

import appaSystem.com.kernel.Acao;
import appaSystem.com.model.Pedido.PedidoItem;
import appaSystem.com.model.Pedido.PedidoItemModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoItemController {
    
    private List<PedidoItem> pedidoItens;
    private int id;
    
    public PedidoItemController(int id){
        pedidoItens = new ArrayList<>();
        this.id = id;
    }
    
    public List<PedidoItem> consulta(){
        
        PedidoItemModel pedidoItem = new PedidoItemModel();
        return pedidoItem.consultar(this.id);
        
    }
    
    public void salvar(PedidoItem pedidoItem, Acao acao){
        
        PedidoItemModel pedidoM = new PedidoItemModel();
        
        if(acao.ordinal() == 1){
            pedidoM.inserirPedidoItem(pedidoItem);
        }else if(acao.ordinal() == 2){
            pedidoM.alterarPedidoItem(pedidoItem);
        }else{
            JOptionPane.showMessageDialog(null, "Não foi possível determinar qual a ação a ser feita para esse item do pedido!");
        }
        
    }

    public void excluir(int id, int pedidoId){
        
        PedidoItemModel pim = new PedidoItemModel();
        pim.excluirPedidoItem(id, pedidoId);
        
    }
    
}
