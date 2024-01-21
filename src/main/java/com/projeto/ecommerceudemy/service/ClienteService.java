package com.projeto.ecommerceudemy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ecommerceudemy.exception.NoSuchElementException;
import com.projeto.ecommerceudemy.model.Cliente;
import com.projeto.ecommerceudemy.repository.ClienteRepository;
import com.projeto.ecommerceudemy.shared.ClienteDTO;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = repository.findAll();

        List<ClienteDTO> dto = clientes
                .stream()
                .map(Cliente -> new ModelMapper().map(Cliente, ClienteDTO.class))
                .collect(Collectors.toList());

        return dto;
    }

    public Optional<ClienteDTO> findById(Long id) {

        Optional<Cliente> cliente = repository.findById(id);

        if (cliente.isEmpty()) {
            throw new NoSuchElementException("Cliente", id);
        }

        ClienteDTO dto = new ModelMapper().map(cliente.get(), ClienteDTO.class);

        return Optional.of(dto);

    }

    public ClienteDTO create(ClienteDTO clienteDTO) {

        clienteDTO.setId(null);

        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);

        cliente = repository.save(cliente);

        clienteDTO.setId(cliente.getId());

        return clienteDTO;

    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {

        Optional<Cliente> clienteExiste = repository.findById(id);

        if (clienteExiste.isEmpty()) {
            throw new NoSuchElementException("Cliente", id);
        }

        clienteDTO.setId(id);
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);

        cliente = repository.save(cliente);

        return clienteDTO;

    }

    public void delete(Long id) {

        Optional<Cliente> cliente = repository.findById(id);

        if (cliente.isEmpty()) {
            throw new NoSuchElementException("Cliente", id);
        }

        repository.deleteById(id);

    }
}
