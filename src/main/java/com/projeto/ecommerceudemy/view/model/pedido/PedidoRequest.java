package com.projeto.ecommerceudemy.view.model.pedido;

import com.projeto.ecommerceudemy.model.Cliente;

public class PedidoRequest {

    private Double descontoTotal;

    private Double valorTotal;

    private Cliente cliente;

    // TODO: Descobrir pq esta dando erro de deserialization.

    public Double getDescontoTotal() {
        return descontoTotal;
    }

    public void setDescontoTotal(Double descontoTotal) {
        this.descontoTotal = descontoTotal;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
