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

import com.projeto.ecommerceudemy.service.ClienteService;
import com.projeto.ecommerceudemy.shared.ClienteDTO;
import com.projeto.ecommerceudemy.view.model.cliente.ClienteRequest;
import com.projeto.ecommerceudemy.view.model.cliente.ClienteResponse;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obterTodos() {
        List<ClienteDTO> Clientes = service.findAll();

        List<ClienteResponse> resposta = Clientes.stream()
                .map(ClienteDto -> mapper.map(ClienteDto, ClienteResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteResponse>> obterPorId(@PathVariable Long id) {
        Optional<ClienteDTO> dto = service.findById(id);
        ClienteResponse Cliente = mapper.map(dto.get(), ClienteResponse.class);
        return new ResponseEntity<>(Optional.of(Cliente), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ClienteResponse> adicionar(@RequestBody ClienteRequest clienteRequest) {

        // Transforma o request em dto
        ClienteDTO dto = mapper.map(clienteRequest, ClienteDTO.class);

        // Salva o dto no banco
        dto = service.create(dto);

        // Converte esse dto para response p/ exibir somente as informações necessárias
        return new ResponseEntity<>(mapper.map(dto, ClienteResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@RequestBody ClienteRequest ClienteRequest,
            @PathVariable Long id) {

        // Mesma coisa que o atualizar, a diferença é o método que passa o id do
        // Cliente.
        ClienteDTO dto = mapper.map(ClienteRequest, ClienteDTO.class);

        dto = service.update(id, dto);

        return new ResponseEntity<>(
                mapper.map(dto, ClienteResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
