package com.projeto.ecommerceudemy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ecommerceudemy.model.ItemPedido;
import com.projeto.ecommerceudemy.model.Produto;
import com.projeto.ecommerceudemy.repository.ItemPedidoRepository;
import com.projeto.ecommerceudemy.repository.PedidoRepository;
import com.projeto.ecommerceudemy.repository.ProdutoRepository;
import com.projeto.ecommerceudemy.shared.ItemPedidoDTO;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    public List<ItemPedidoDTO> findAll() {
        List<ItemPedido> itensPedidos = repository.findAll();

        List<ItemPedidoDTO> dto = itensPedidos
                .stream()
                .map(ItemPedido -> new ModelMapper().map(ItemPedido, ItemPedidoDTO.class))
                .collect(Collectors.toList());

        return dto;
    }

    public Optional<ItemPedidoDTO> findById(Long id) {

        Optional<ItemPedido> itemPedido = repository.findById(id);

        // TODO: Fazer verificação p/ ver se o id existe ou não e colocar exception

        ItemPedidoDTO dto = new ModelMapper().map(itemPedido.get(), ItemPedidoDTO.class);

        return Optional.of(dto);

    }

    public ItemPedidoDTO create(ItemPedidoDTO itemPedidoDTO) {

        Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId()).orElse(null);

        itemPedidoDTO.setId(null);

        if (produto.getEstoque() >= itemPedidoDTO.getQuantidade()) {
            Integer qtd = produto.getEstoque() - itemPedidoDTO.getQuantidade();
            produto.setEstoque(qtd);
        } else {
            new RuntimeException("Não há estoque o suficiente");
            return null;
        }

        ItemPedido itemPedido = modelMapper.map(itemPedidoDTO, ItemPedido.class);

        pedidoService.gerarValorTotal(pedidoRepository.findById(itemPedido.getPedido().getId()).orElse(null));

        itemPedido = repository.save(itemPedido);

        itemPedidoDTO.setId(itemPedido.getId());

        return itemPedidoDTO;

    }

    public ItemPedidoDTO update(Long id, ItemPedidoDTO itemPedidoDTO) {

        Optional<ItemPedido> itemPedidoExiste = repository.findById(id);

        if (itemPedidoExiste.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível atualizar o produto com o id " + id + " pois ele não existe.");
        }

        itemPedidoDTO.setId(id);
        ItemPedido itemPedido = modelMapper.map(itemPedidoDTO, ItemPedido.class);

        itemPedido = repository.save(itemPedido);

        return itemPedidoDTO;

    }

    public void delete(Long id) {

        Optional<ItemPedido> itemPedido = repository.findById(id);

        if (itemPedido.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível deletar o produto com o id " + id + " pois ele não existe.");
        }

        repository.deleteById(id);

    }
}
