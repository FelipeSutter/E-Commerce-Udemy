package com.projeto.ecommerceudemy.view.model.pedido;

import com.projeto.ecommerceudemy.model.Cliente;

public class PedidoResponse {

    private Long id;

    private Double descontoTotal;

    private Double valorTotal;

    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
