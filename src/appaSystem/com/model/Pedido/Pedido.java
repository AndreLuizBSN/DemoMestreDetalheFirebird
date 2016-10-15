package appaSystem.com.model.Pedido;

public class Pedido {

    private int id;
    private String nomeCliente;
    private int condicaoPagamentoId;
    private int formaPagamentoId;
    private double valorFrete;
    private double valorDesconto;
    private int qtdeItens;
    private double valorTotal;
    private PedidoPosicao pedidoPosicao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getCondicaoPagamentoId() {
        return condicaoPagamentoId;
    }

    public void setCondicaoPagamentoId(int condicaoPagamentoId) {
        this.condicaoPagamentoId = condicaoPagamentoId;
    }

    public int getFormaPagamentoId() {
        return formaPagamentoId;
    }

    public void setFormaPagamentoId(int formaPagamentoId) {
        this.formaPagamentoId = formaPagamentoId;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public int getQtdeItens() {
        return qtdeItens;
    }

    public void setQtdeItens(int qtdeItens) {
        this.qtdeItens = qtdeItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public PedidoPosicao getPedidoPosicao() {
        return pedidoPosicao;
    }

    public void setPedidoPosicao(PedidoPosicao pedidoPosicao) {
        this.pedidoPosicao = pedidoPosicao;
    }

}
