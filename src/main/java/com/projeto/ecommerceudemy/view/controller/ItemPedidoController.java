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

import com.projeto.ecommerceudemy.service.ItemPedidoService;
import com.projeto.ecommerceudemy.shared.ItemPedidoDTO;
import com.projeto.ecommerceudemy.view.model.itemPedido.ItemPedidoRequest;
import com.projeto.ecommerceudemy.view.model.itemPedido.ItemPedidoResponse;

@RestController
@RequestMapping("/itemPedido")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponse>> obterTodos() {
        List<ItemPedidoDTO> ItemPedidos = service.findAll();

        List<ItemPedidoResponse> resposta = ItemPedidos.stream()
                .map(ItemPedidoDto -> mapper.map(ItemPedidoDto, ItemPedidoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ItemPedidoResponse>> obterPorId(@PathVariable Long id) {
        Optional<ItemPedidoDTO> dto = service.findById(id);
        ItemPedidoResponse ItemPedido = mapper.map(dto.get(), ItemPedidoResponse.class);
        return new ResponseEntity<>(Optional.of(ItemPedido), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponse> adicionar(@RequestBody ItemPedidoRequest itemPedidoRequest) {

        // Transforma o request em dto
        ItemPedidoDTO dto = mapper.map(itemPedidoRequest, ItemPedidoDTO.class);

        // Salva o dto no banco
        dto = service.create(dto);

        // Converte esse dto para response p/ exibir somente as informações necessárias
        return new ResponseEntity<>(mapper.map(dto, ItemPedidoResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> atualizar(@RequestBody ItemPedidoRequest itemPedidoRequest,
            @PathVariable Long id) {

        // Mesma coisa que o atualizar, a diferença é o método que passa o id do
        // ItemPedido.
        ItemPedidoDTO dto = mapper.map(itemPedidoRequest, ItemPedidoDTO.class);

        dto = service.update(id, dto);

        return new ResponseEntity<>(
                mapper.map(dto, ItemPedidoResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
