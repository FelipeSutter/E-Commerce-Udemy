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

import com.projeto.ecommerceudemy.service.PedidoService;
import com.projeto.ecommerceudemy.shared.PedidoDTO;
import com.projeto.ecommerceudemy.view.model.pedido.PedidoRequest;
import com.projeto.ecommerceudemy.view.model.pedido.PedidoResponse;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> obterTodos() {
        List<PedidoDTO> Pedidos = service.findAll();

        List<PedidoResponse> resposta = Pedidos.stream()
                .map(PedidoDto -> mapper.map(PedidoDto, PedidoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PedidoResponse>> obterPorId(@PathVariable Long id) {
        Optional<PedidoDTO> dto = service.findById(id);
        PedidoResponse Pedido = mapper.map(dto.get(), PedidoResponse.class);
        return new ResponseEntity<>(Optional.of(Pedido), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<PedidoResponse> adicionar(@RequestBody PedidoRequest pedidoRequest) {

        // Transforma o request em dto
        PedidoDTO dto = mapper.map(pedidoRequest, PedidoDTO.class);

        // Salva o dto no banco
        dto = service.create(dto);

        // Converte esse dto para response p/ exibir somente as informações necessárias
        return new ResponseEntity<>(mapper.map(dto, PedidoResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizar(@RequestBody PedidoRequest PedidoRequest,
            @PathVariable Long id) {

        // Mesma coisa que o atualizar, a diferença é o método que passa o id do
        // Pedido.
        PedidoDTO dto = mapper.map(PedidoRequest, PedidoDTO.class);

        dto = service.update(id, dto);

        return new ResponseEntity<>(
                mapper.map(dto, PedidoResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
