package com.projeto.ecommerceudemy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ecommerceudemy.model.ItemPedido;
import com.projeto.ecommerceudemy.model.Pedido;
import com.projeto.ecommerceudemy.repository.PedidoRepository;
import com.projeto.ecommerceudemy.shared.PedidoDTO;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PedidoDTO> findAll() {
        List<Pedido> pedidos = repository.findAll();

        List<PedidoDTO> dto = pedidos
                .stream()
                .map(Pedido -> new ModelMapper().map(Pedido, PedidoDTO.class))
                .collect(Collectors.toList());

        return dto;
    }

    public Optional<PedidoDTO> findById(Long id) {

        Optional<Pedido> pedido = repository.findById(id);

        // TODO: Fazer verificação p/ ver se o id existe ou não e colocar exception

        PedidoDTO dto = new ModelMapper().map(pedido.get(), PedidoDTO.class);

        return Optional.of(dto);

    }

    public PedidoDTO create(PedidoDTO pedidoDTO) {

        pedidoDTO.setId(null);

        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);

        pedido = repository.save(pedido);

        pedidoDTO.setId(pedido.getId());

        return pedidoDTO;

    }

    public PedidoDTO update(Long id, PedidoDTO pedidoDTO) {

        Optional<Pedido> pedidoExiste = repository.findById(id);

        if (pedidoExiste.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível atualizar o produto com o id " + id + " pois ele não existe.");
        }

        pedidoDTO.setId(id);
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);

        pedido = repository.save(pedido);

        return pedidoDTO;

    }

    public void delete(Long id) {

        Optional<Pedido> pedido = repository.findById(id);

        if (pedido.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível deletar o produto com o id " + id + " pois ele não existe.");
        }

        repository.deleteById(id);

    }

    public void gerarValorTotal(Pedido pedido) {
        Double valorTotal = 0.0;
        Double descontoTotal = 0.0;

        if (pedido.getItensPedidos().isEmpty()) {
            new RuntimeException("Não existe item pedido para esse pedido");
        } else {
            for (ItemPedido itemPedido : pedido.getItensPedidos()) {
                valorTotal = valorTotal + (itemPedido.getProduto().getValorVenda() * itemPedido.getQuantidade());
                descontoTotal = descontoTotal + itemPedido.getDesconto();
            }
            valorTotal = valorTotal - descontoTotal;
            pedido.setValorTotal(valorTotal);
            pedido.setDescontoTotal(descontoTotal);
            repository.save(pedido);
        }

    }

    // TODO: Fazer um método para calcular o desconto total, tambem incluir a adição
    // da qtd dos itens escolhidos na compra
}
