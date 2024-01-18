package com.projeto.ecommerceudemy.view.model.itemPedido;

import com.projeto.ecommerceudemy.model.Pedido;
import com.projeto.ecommerceudemy.model.Produto;

public class ItemPedidoRequest {

    private Integer quantidade;

    private Double desconto;

    private Pedido pedido;

    private Produto produto;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
