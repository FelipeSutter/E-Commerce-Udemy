package com.projeto.ecommerceudemy.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.ecommerceudemy.service.ProdutoService;
import com.projeto.ecommerceudemy.shared.ProdutoDTO;
import com.projeto.ecommerceudemy.view.model.produto.ProdutoRequest;
import com.projeto.ecommerceudemy.view.model.produto.ProdutoResponse;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos() {
        List<ProdutoDTO> Produtos = service.findAll();

        List<ProdutoResponse> resposta = Produtos.stream()
                .map(ProdutoDto -> mapper.map(ProdutoDto, ProdutoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> obterPorId(@PathVariable Long id) {
        Optional<ProdutoDTO> dto = service.findById(id);
        ProdutoResponse Produto = mapper.map(dto.get(), ProdutoResponse.class);
        return new ResponseEntity<>(Optional.of(Produto), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoRequest) {

        // Transforma o request em dto
        ProdutoDTO dto = mapper.map(produtoRequest, ProdutoDTO.class);

        // Salva o dto no banco
        dto = service.create(dto);

        // Converte esse dto para response p/ exibir somente as informações necessárias
        return new ResponseEntity<>(mapper.map(dto, ProdutoResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoRequest,
            @PathVariable Long id) {

        // Mesma coisa que o atualizar, a diferença é o método que passa o id do
        // Produto.
        ProdutoDTO dto = mapper.map(produtoRequest, ProdutoDTO.class);

        dto = service.update(id, dto);

        return new ResponseEntity<>(
                mapper.map(dto, ProdutoResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
