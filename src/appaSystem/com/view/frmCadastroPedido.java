/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appaSystem.com.view;

import appaSystem.com.controller.pedido.PedidoController;
import appaSystem.com.controller.pedido.PedidoItemController;
import appaSystem.com.db.Conexao;
import appaSystem.com.db.DadosConexao;
import appaSystem.com.kernel.Acao;
import appaSystem.com.model.Pedido.Pedido;
import appaSystem.com.model.Pedido.PedidoItem;
import appaSystem.com.model.Pedido.PedidoPosicao;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Prince
 */
public class frmCadastroPedido extends javax.swing.JFrame {

    private int codPedido;
    private Acao acao;
    private int qtdeTotalItens;
    private double valorTotalPedido;
    Conexao con = new Conexao();
    Conexao con2 = new Conexao();
    DadosConexao dcon = new DadosConexao();
    
    DefaultTableModel lista = new DefaultTableModel();
        
    public void carregarDados(){
     
        if(this.acao.ordinal() == 2){
            
            PedidoController pc = new PedidoController(this.codPedido);
            List<Pedido> pedidos = pc.consulta("");
            
            for (Pedido pedido : pedidos) {
                
                this.txtId.setText(String.valueOf(pedido.getId()));
                this.txtNomeCliente.setText(pedido.getNomeCliente());
                this.txtCondicaoPagamento.setText(String.valueOf(pedido.getCondicaoPagamentoId()));
                this.txtFormaPagamento.setText(String.valueOf(pedido.getFormaPagamentoId()));
                this.txtQtdeItens.setText(String.valueOf(pedido.getQtdeItens()));
                this.txtValorFrete.setText(String.valueOf(pedido.getValorFrete()));
                this.txtValorDesconto.setText(String.valueOf(pedido.getValorDesconto()));
                this.txtValorTotal.setText(String.valueOf(pedido.getValorTotal()));
                this.txtPosicao.setText(pedido.getPedidoPosicao().name());
            }
            
            carregaItens();
        
        }
        
    }
    
    public void carregaItens(){
        this.lista.setColumnCount(0);
        this.lista.setRowCount(0);

        jTable2.setModel(this.lista);
        
        PedidoItemController pc = new PedidoItemController(this.codPedido);
        List<PedidoItem> pedidoItens = pc.consulta();

        this.lista.addColumn("ID");
        this.lista.addColumn("Produto");
        this.lista.addColumn("Quantidade");
        this.lista.addColumn("Valor Unitário");
        this.lista.addColumn("Valor Total");

        for (int i = 0; i<pedidoItens.toArray().length; i++) {

            this.lista.addRow(new String[]{
                String.valueOf(pedidoItens.get(i).getId()),
                pedidoItens.get(i).getProduto(),
                String.valueOf(pedidoItens.get(i).getQuantidade()),
                String.valueOf(pedidoItens.get(i).getValorUnitario()),
                String.valueOf(pedidoItens.get(i).getValorTotal())
            });
            
        }

        jTable2.setModel(this.lista);

    }
    
    public void selecao(){
        
        int linha = jTable2.getSelectedRow();
        
        txtIdItem.setText(jTable2.getValueAt(linha, 0).toString());
        txtProdutoNome.setText(jTable2.getValueAt(linha, 1).toString());
        txtQtde.setText(jTable2.getValueAt(linha, 2).toString());
        txtValorUnitário.setText(jTable2.getValueAt(linha, 3).toString());
        txtValorTotalItem.setText(jTable2.getValueAt(linha, 4).toString());
    }
    
    public void salvarPedidoCab(){
        
        List<Pedido> pedidos;
        
        PedidoController pc = new PedidoController(this.codPedido);
        Pedido pedido = new Pedido();
        pedido.setId(this.codPedido);
        pedido.setNomeCliente(txtNomeCliente.getText().trim());
        pedido.setCondicaoPagamentoId(Integer.parseInt(txtCondicaoPagamento.getText()));
        pedido.setFormaPagamentoId(Integer.parseInt(txtFormaPagamento.getText()));
        pedido.setQtdeItens(this.qtdeTotalItens);
        pedido.setValorFrete(Double.parseDouble(txtValorFrete.getText()));
        pedido.setValorDesconto(Double.parseDouble(txtValorDesconto.getText()));
        pedido.setValorTotal(this.valorTotalPedido);
        pedido.setPedidoPosicao(PedidoPosicao.ABERTO);

        //Inclusão
        if(this.codPedido == 0){
            pedidos = pc.salvar(pedido, Acao.INCLUSAO);
            this.acao = Acao.ALTERACAO;
        }else{
            pedidos = pc.salvar(pedido, Acao.ALTERACAO);
        }
        
        this.codPedido = pedidos.get(0).getId();
        txtId.setText(String.valueOf(pedidos.get(0).getId()));
        txtNomeCliente.setText(pedidos.get(0).getNomeCliente());
        txtFormaPagamento.setText(String.valueOf(pedidos.get(0).getFormaPagamentoId()));
        txtCondicaoPagamento.setText(String.valueOf(pedidos.get(0).getCondicaoPagamentoId()));
        txtQtdeItens.setText(String.valueOf(pedidos.get(0).getQtdeItens()));
        txtValorFrete.setText(String.valueOf(pedidos.get(0).getValorFrete()));
        txtValorDesconto.setText(String.valueOf(pedidos.get(0).getValorDesconto()));
        txtValorTotal.setText(String.valueOf(pedidos.get(0).getValorTotal()));
        txtPosicao.setText(pedido.getPedidoPosicao().name());
        
        this.setTitle("Alteração do Pedido "+codPedido);
        
    }
    
    /**
     * Creates new form frmCadastroPedido
     * @param codPedido
     * @param tipoEntrada
     */
    public frmCadastroPedido(int codPedido, Acao acao) {
        initComponents();
        
        
        if(acao == Acao.INCLUSAO){
            this.setTitle("Inclusão de Novo Pedido");
        }else{
            this.setTitle("Alteração do Pedido "+codPedido);
        }
        //colocar a janela no meio da tela
        this.setLocationRelativeTo(null);
        this.codPedido = codPedido;
        this.acao = acao;
        carregarDados();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtId = new javax.swing.JTextField();
        txtQtdeItens = new javax.swing.JTextField();
        txtValorTotal = new javax.swing.JTextField();
        txtValorDesconto = new javax.swing.JTextField();
        txtValorFrete = new javax.swing.JTextField();
        txtNomeCliente = new javax.swing.JTextField();
        txtCondicaoPagamento = new javax.swing.JTextField();
        txtFormaPagamento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtIdItem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtProdutoNome = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtQtde = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtValorUnitário = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtValorTotalItem = new javax.swing.JTextField();
        btnSalvarPedido = new javax.swing.JButton();
        btnCancelarPedido = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnNovoItem = new javax.swing.JButton();
        btnExcluirItem = new javax.swing.JButton();
        btnSalvarItem = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtPosicao = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ID:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel2.setText("Nome do Cliente:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, -1, -1));

        jLabel3.setText("Condição de Pagamento:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, -1));

        jLabel4.setText("Forma de Pagamento:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(384, 92, -1, -1));

        jLabel5.setText("Valor de Frete:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 118, -1, -1));

        jLabel6.setText("Valor de Desconto:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 118, -1, -1));

        jLabel7.setText("Valor total:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 118, -1, -1));

        jLabel8.setText("Quantidade total de Itens:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(362, 40, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 141, 610, -1));

        txtId.setEnabled(false);
        getContentPane().add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 37, 109, -1));

        txtQtdeItens.setEnabled(false);
        getContentPane().add(txtQtdeItens, new org.netbeans.lib.awtextra.AbsoluteConstraints(494, 37, 126, -1));

        txtValorTotal.setEnabled(false);
        getContentPane().add(txtValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(494, 115, 126, -1));
        getContentPane().add(txtValorDesconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 115, 138, -1));
        getContentPane().add(txtValorFrete, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 115, 96, -1));

        txtNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteActionPerformed(evt);
            }
        });
        getContentPane().add(txtNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 63, 524, -1));
        getContentPane().add(txtCondicaoPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 89, 79, -1));

        txtFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFormaPagamentoActionPerformed(evt);
            }
        });
        getContentPane().add(txtFormaPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(494, 89, 126, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Manutenção do itens:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 152, -1, -1));

        jLabel10.setText("ID");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 153, -1, -1));

        txtIdItem.setEnabled(false);
        getContentPane().add(txtIdItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(552, 150, 68, -1));

        jLabel11.setText("Produto:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 181, -1, -1));
        getContentPane().add(txtProdutoNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 178, 540, -1));

        jLabel12.setText("Quantidade:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 207, -1, -1));
        getContentPane().add(txtQtde, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 204, 61, -1));

        jLabel13.setText("Valor Unitário:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 207, -1, -1));
        getContentPane().add(txtValorUnitário, new org.netbeans.lib.awtextra.AbsoluteConstraints(231, 204, 87, -1));

        jLabel14.setText("ValorTotal:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 207, -1, -1));

        txtValorTotalItem.setEnabled(false);
        getContentPane().add(txtValorTotalItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 204, 107, -1));

        btnSalvarPedido.setText("Salvar Pedido");
        btnSalvarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarPedidoActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalvarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, -1, -1));

        btnCancelarPedido.setText("Voltar para a Lista");
        btnCancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPedidoActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(501, 8, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 610, 219));

        btnNovoItem.setText("Novo");
        btnNovoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoItemActionPerformed(evt);
            }
        });
        getContentPane().add(btnNovoItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 149, -1, -1));

        btnExcluirItem.setText("Excluir");
        btnExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemActionPerformed(evt);
            }
        });
        getContentPane().add(btnExcluirItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 149, -1, -1));

        btnSalvarItem.setText("Salvar");
        btnSalvarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarItemActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalvarItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 149, -1, -1));

        jLabel15.setText("Posição:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 40, -1, -1));

        txtPosicao.setEnabled(false);
        getContentPane().add(txtPosicao, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 37, 144, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeClienteActionPerformed

    private void txtFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFormaPagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFormaPagamentoActionPerformed

    private void btnExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemActionPerformed
        
        int resp = -999; 
                
        if(txtIdItem.getText().equals("") || txtIdItem.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "É necessário escolher um item para ser excluído");
        }else{
            resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja exluir o item "+txtIdItem.getText()+"\n"
                    + txtProdutoNome.getText()+"?");
        }
        if(resp != -999){
            if(resp == JOptionPane.YES_OPTION){
                
                PedidoItemController pic = new PedidoItemController(codPedido);
                pic.excluir(Integer.parseInt(txtIdItem.getText()), codPedido);
                
                
                carregaItens();

                txtIdItem.setText("");
                txtQtde.setText("");
                txtValorTotalItem.setText("");
                txtValorUnitário.setText("");
                txtProdutoNome.setText("");

                salvarPedidoCab();

            }
        }
        
    }//GEN-LAST:event_btnExcluirItemActionPerformed

    private void btnCancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPedidoActionPerformed
        this.setVisible(false);
        
        frmListaPedidos form = new frmListaPedidos();
        form.setVisible(true);
    }//GEN-LAST:event_btnCancelarPedidoActionPerformed

    private void btnSalvarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarItemActionPerformed

        //Caso tente salvar o item no banco e o cabeçalho ainda não tenha sido salvo,
        //salvar o cabeçalho antes
        salvarPedidoCab();
        
        int id;
        
        if(txtIdItem.getText().equals("") || txtIdItem.getText().isEmpty()){
            id = 0;
        }else{
            id = Integer.parseInt(txtIdItem.getText());
        }
        
        double valorTotal = Double.parseDouble(txtQtde.getText()) * Double.parseDouble(txtValorUnitário.getText());
        
        PedidoItemController pic = new PedidoItemController(id);
        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setId(id);
        pedidoItem.setPedidoId(this.codPedido);
        pedidoItem.setProduto(txtProdutoNome.getText().trim());
        pedidoItem.setQuantidade(Integer.parseInt(txtQtde.getText()));
        pedidoItem.setValorUnitario(Double.parseDouble(txtValorUnitário.getText()));
        pedidoItem.setValorTotal(valorTotal);
        
        if(id == 0){
            pic.salvar(pedidoItem, Acao.INCLUSAO);
        }else{
            pic.salvar(pedidoItem, Acao.ALTERACAO);
        }
        
        carregaItens();
                                                    
        txtIdItem.setText("");
        txtQtde.setText("");
        txtValorTotalItem.setText("");
        txtValorUnitário.setText("");
        txtProdutoNome.setText("");
        
        salvarPedidoCab();
        
    }//GEN-LAST:event_btnSalvarItemActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        selecao();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        selecao();
    }//GEN-LAST:event_jTable2KeyReleased

    private void btnNovoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoItemActionPerformed
        txtIdItem.setText("");
        txtQtde.setText("");
        txtValorTotalItem.setText("");
        txtValorUnitário.setText("");
        txtProdutoNome.setText("");
    }//GEN-LAST:event_btnNovoItemActionPerformed

    private void btnSalvarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarPedidoActionPerformed
        
        PedidoController pc = new PedidoController(this.codPedido);
        salvarPedidoCab();
    }//GEN-LAST:event_btnSalvarPedidoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       int codPedido = 0;
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                                             
                new frmCadastroPedido(codPedido, Acao.INCLUSAO).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton btnCancelarPedido;
    javax.swing.JButton btnExcluirItem;
    javax.swing.JButton btnNovoItem;
    javax.swing.JButton btnSalvarItem;
    javax.swing.JButton btnSalvarPedido;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel11;
    javax.swing.JLabel jLabel12;
    javax.swing.JLabel jLabel13;
    javax.swing.JLabel jLabel14;
    javax.swing.JLabel jLabel15;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JSeparator jSeparator1;
    javax.swing.JTable jTable1;
    javax.swing.JTable jTable2;
    javax.swing.JTextField txtCondicaoPagamento;
    javax.swing.JTextField txtFormaPagamento;
    javax.swing.JTextField txtId;
    javax.swing.JTextField txtIdItem;
    javax.swing.JTextField txtNomeCliente;
    javax.swing.JTextField txtPosicao;
    javax.swing.JTextField txtProdutoNome;
    javax.swing.JTextField txtQtde;
    javax.swing.JTextField txtQtdeItens;
    javax.swing.JTextField txtValorDesconto;
    javax.swing.JTextField txtValorFrete;
    javax.swing.JTextField txtValorTotal;
    javax.swing.JTextField txtValorTotalItem;
    javax.swing.JTextField txtValorUnitário;
    // End of variables declaration//GEN-END:variables
}
