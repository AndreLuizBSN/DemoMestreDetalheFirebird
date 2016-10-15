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
public class jdlCadastroPedido extends javax.swing.JDialog {
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
     * Creates new form jdlCadastroPedido
     */
    public jdlCadastroPedido(java.awt.Frame parent, boolean modal, int codPedido, Acao acao) {
        super(parent, modal);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtQtde = new javax.swing.JTextField();
        txtProdutoNome = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtValorUnitário = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtValorTotalItem = new javax.swing.JTextField();
        txtIdItem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnSalvarItem = new javax.swing.JButton();
        btnExcluirItem = new javax.swing.JButton();
        btnNovoItem = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnSalvarPedido = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtPosicao = new javax.swing.JTextField();
        txtNomeCliente = new javax.swing.JTextField();
        txtCondicaoPagamento = new javax.swing.JTextField();
        txtValorFrete = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtValorDesconto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        txtFormaPagamento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtQtdeItens = new javax.swing.JTextField();
        btnCancelarPedido = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel12.setText("Quantidade:");

        jLabel11.setText("Produto:");

        jLabel13.setText("Valor Unitário:");

        jLabel14.setText("ValorTotal:");

        txtValorTotalItem.setEnabled(false);

        txtIdItem.setEnabled(false);

        jLabel10.setText("ID");

        btnSalvarItem.setText("Salvar");
        btnSalvarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarItemActionPerformed(evt);
            }
        });

        btnExcluirItem.setText("Excluir");
        btnExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemActionPerformed(evt);
            }
        });

        btnNovoItem.setText("Novo");
        btnNovoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoItemActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Manutenção do itens:");

        jLabel5.setText("Valor de Frete:");

        jLabel3.setText("Condição de Pagamento:");

        jLabel2.setText("Nome do Cliente:");

        jLabel1.setText("ID:");

        btnSalvarPedido.setText("Salvar Pedido");
        btnSalvarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarPedidoActionPerformed(evt);
            }
        });

        txtId.setEnabled(false);

        jLabel15.setText("Posição:");

        txtPosicao.setEnabled(false);

        txtNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteActionPerformed(evt);
            }
        });

        jLabel6.setText("Valor de Desconto:");

        jLabel7.setText("Valor total:");

        txtValorTotal.setEnabled(false);

        txtFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFormaPagamentoActionPerformed(evt);
            }
        });

        jLabel4.setText("Forma de Pagamento:");

        jLabel8.setText("Quantidade total de Itens:");

        txtQtdeItens.setEnabled(false);

        btnCancelarPedido.setText("Voltar para a Lista");
        btnCancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(btnSalvarPedido)
                            .addGap(394, 394, 394)
                            .addComponent(btnCancelarPedido))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(4, 4, 4)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel15)
                            .addGap(4, 4, 4)
                            .addComponent(txtPosicao, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel8)
                            .addGap(4, 4, 4)
                            .addComponent(txtQtdeItens, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(4, 4, 4)
                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(4, 4, 4)
                            .addComponent(txtCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(171, 171, 171)
                            .addComponent(jLabel4)
                            .addGap(4, 4, 4)
                            .addComponent(txtFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(4, 4, 4)
                            .addComponent(txtValorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(4, 4, 4)
                            .addComponent(jLabel6)
                            .addGap(4, 4, 4)
                            .addComponent(txtValorDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel7)
                            .addGap(4, 4, 4)
                            .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(4, 4, 4)
                            .addComponent(btnNovoItem)
                            .addGap(6, 6, 6)
                            .addComponent(btnExcluirItem)
                            .addGap(6, 6, 6)
                            .addComponent(btnSalvarItem)
                            .addGap(194, 194, 194)
                            .addComponent(jLabel10)
                            .addGap(4, 4, 4)
                            .addComponent(txtIdItem, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(28, 28, 28)
                            .addComponent(txtProdutoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(10, 10, 10)
                            .addComponent(txtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel13)
                            .addGap(4, 4, 4)
                            .addComponent(txtValorUnitário, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(139, 139, 139)
                            .addComponent(jLabel14)
                            .addGap(4, 4, 4)
                            .addComponent(txtValorTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 14, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSalvarPedido)
                        .addComponent(btnCancelarPedido))
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPosicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtQtdeItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel15)
                                .addComponent(jLabel8))))
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(jLabel2))
                        .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))))
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtValorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))))
                    .addGap(6, 6, 6)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(jLabel9))
                        .addComponent(btnNovoItem)
                        .addComponent(btnExcluirItem)
                        .addComponent(btnSalvarItem)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jLabel10))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(txtIdItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(jLabel11))
                        .addComponent(txtProdutoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(6, 6, 6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorUnitário, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValorTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        selecao();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        selecao();
    }//GEN-LAST:event_jTable2KeyReleased

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

    private void btnExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemActionPerformed

        if(txtIdItem.getText().equals("") || txtIdItem.getText().isEmpty()){
            
            JOptionPane.showMessageDialog(null, "É necessário escolher um item para ser excluído");
            
        }else{
            
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
 

    }//GEN-LAST:event_btnExcluirItemActionPerformed

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

    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeClienteActionPerformed

    private void txtFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFormaPagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFormaPagamentoActionPerformed

    private void btnCancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPedidoActionPerformed
        this.setModal(false);
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelarPedidoActionPerformed

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
            java.util.logging.Logger.getLogger(jdlCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jdlCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jdlCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jdlCadastroPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jdlCadastroPedido dialog = new jdlCadastroPedido(new javax.swing.JFrame(), true, 0,Acao.INCLUSAO);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarPedido;
    private javax.swing.JButton btnExcluirItem;
    private javax.swing.JButton btnNovoItem;
    private javax.swing.JButton btnSalvarItem;
    private javax.swing.JButton btnSalvarPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCondicaoPagamento;
    private javax.swing.JTextField txtFormaPagamento;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdItem;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtPosicao;
    private javax.swing.JTextField txtProdutoNome;
    private javax.swing.JTextField txtQtde;
    private javax.swing.JTextField txtQtdeItens;
    private javax.swing.JTextField txtValorDesconto;
    private javax.swing.JTextField txtValorFrete;
    private javax.swing.JTextField txtValorTotal;
    private javax.swing.JTextField txtValorTotalItem;
    private javax.swing.JTextField txtValorUnitário;
    // End of variables declaration//GEN-END:variables
}
