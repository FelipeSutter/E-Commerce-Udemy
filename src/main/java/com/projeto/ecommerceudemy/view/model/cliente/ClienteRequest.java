package com.projeto.ecommerceudemy.view.model.cliente;

import java.util.List;

import com.projeto.ecommerceudemy.model.Endereco;
import com.projeto.ecommerceudemy.model.Pedido;

public class ClienteRequest {

    private String nome;

    private String cpf;

    private String email;

    private Endereco endereco;

    private List<Pedido> pedidos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
