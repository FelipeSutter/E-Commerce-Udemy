package com.projeto.ecommerceudemy.view.model.cliente;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projeto.ecommerceudemy.model.Endereco;

public class ClienteRequest {

    private String nome;

    private String cpf;

    private String email;

    @JsonManagedReference
    private Endereco endereco;

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

}
