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

import com.projeto.ecommerceudemy.service.CategoriaService;
import com.projeto.ecommerceudemy.shared.CategoriaDTO;
import com.projeto.ecommerceudemy.view.model.categoria.CategoriaRequest;
import com.projeto.ecommerceudemy.view.model.categoria.CategoriaResponse;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> obterTodos() {
        List<CategoriaDTO> Categorias = service.findAll();

        List<CategoriaResponse> resposta = Categorias.stream()
                .map(CategoriaDto -> mapper.map(CategoriaDto, CategoriaResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoriaResponse>> obterPorId(@PathVariable Long id) {
        Optional<CategoriaDTO> dto = service.findById(id);
        CategoriaResponse Categoria = mapper.map(dto.get(), CategoriaResponse.class);
        return new ResponseEntity<>(Optional.of(Categoria), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> adicionar(@RequestBody CategoriaRequest categoriaRequest) {

        // Transforma o request em dto
        CategoriaDTO dto = mapper.map(categoriaRequest, CategoriaDTO.class);

        // Salva o dto no banco
        dto = service.create(dto);

        // Converte esse dto para response p/ exibir somente as informações necessárias
        return new ResponseEntity<>(mapper.map(dto, CategoriaResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(@RequestBody CategoriaRequest categoriaRequest,
            @PathVariable Long id) {

        // Mesma coisa que o atualizar, a diferença é o método que passa o id do
        // Categoria.
        CategoriaDTO dto = mapper.map(categoriaRequest, CategoriaDTO.class);

        dto = service.update(id, dto);

        return new ResponseEntity<>(
                mapper.map(dto, CategoriaResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
