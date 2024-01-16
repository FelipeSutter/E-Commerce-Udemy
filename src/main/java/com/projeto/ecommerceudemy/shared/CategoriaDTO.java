package com.projeto.ecommerceudemy.shared;

import java.util.List;

import com.projeto.ecommerceudemy.model.Produto;

public class CategoriaDTO {

    private Long id;

    private String descricao;

    private List<Produto> produtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
