package com.projeto.ecommerceudemy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ecommerceudemy.model.Produto;
import com.projeto.ecommerceudemy.repository.ProdutoRepository;
import com.projeto.ecommerceudemy.shared.ProdutoDTO;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProdutoDTO> findAll() {
        List<Produto> produtos = repository.findAll();

        List<ProdutoDTO> dto = produtos
                .stream()
                .map(Produto -> new ModelMapper().map(Produto, ProdutoDTO.class))
                .collect(Collectors.toList());

        return dto;
    }

    public Optional<ProdutoDTO> findById(Long id) {

        Optional<Produto> produto = repository.findById(id);

        // TODO: Fazer verificação p/ ver se o id existe ou não e colocar exception

        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        return Optional.of(dto);

    }

    public ProdutoDTO create(ProdutoDTO produtoDTO) {

        produtoDTO.setId(null);

        Produto produto = modelMapper.map(produtoDTO, Produto.class);

        produto = repository.save(produto);

        produtoDTO.setId(produto.getId());

        return produtoDTO;

    }

    public ProdutoDTO update(Long id, ProdutoDTO produtoDTO) {

        Optional<Produto> produtoExiste = repository.findById(id);

        if (produtoExiste.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível atualizar o produto com o id " + id + " pois ele não existe.");
        }

        produtoDTO.setId(id);
        Produto produto = modelMapper.map(produtoDTO, Produto.class);

        produto = repository.save(produto);

        return produtoDTO;

    }

    public void delete(Long id) {

        Optional<Produto> produto = repository.findById(id);

        if (produto.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível deletar o produto com o id " + id + " pois ele não existe.");
        }

        repository.deleteById(id);

    }
}
