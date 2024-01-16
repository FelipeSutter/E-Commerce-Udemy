package com.projeto.ecommerceudemy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.ecommerceudemy.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
