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

import com.projeto.ecommerceudemy.service.EnderecoService;
import com.projeto.ecommerceudemy.shared.EnderecoDTO;
import com.projeto.ecommerceudemy.view.model.endereco.EnderecoRequest;
import com.projeto.ecommerceudemy.view.model.endereco.EnderecoResponse;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<EnderecoResponse>> obterTodos() {
        List<EnderecoDTO> Enderecos = service.findAll();

        List<EnderecoResponse> resposta = Enderecos.stream()
                .map(EnderecoDto -> mapper.map(EnderecoDto, EnderecoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EnderecoResponse>> obterPorId(@PathVariable Long id) {
        Optional<EnderecoDTO> dto = service.findById(id);
        EnderecoResponse Endereco = mapper.map(dto.get(), EnderecoResponse.class);
        return new ResponseEntity<>(Optional.of(Endereco), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<EnderecoResponse> adicionar(@RequestBody EnderecoRequest enderecoRequest) {

        // Transforma o request em dto
        EnderecoDTO dto = mapper.map(enderecoRequest, EnderecoDTO.class);

        // Salva o dto no banco
        dto = service.create(dto);

        // Converte esse dto para response p/ exibir somente as informações necessárias
        return new ResponseEntity<>(mapper.map(dto, EnderecoResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> atualizar(@RequestBody EnderecoRequest EnderecoRequest,
            @PathVariable Long id) {

        // Mesma coisa que o atualizar, a diferença é o método que passa o id do
        // Endereco.
        EnderecoDTO dto = mapper.map(EnderecoRequest, EnderecoDTO.class);

        dto = service.update(id, dto);

        return new ResponseEntity<>(
                mapper.map(dto, EnderecoResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
