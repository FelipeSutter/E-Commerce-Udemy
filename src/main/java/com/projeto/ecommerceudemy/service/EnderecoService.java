package com.projeto.ecommerceudemy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.projeto.ecommerceudemy.model.Endereco;
import com.projeto.ecommerceudemy.repository.EnderecoRepository;
import com.projeto.ecommerceudemy.shared.EnderecoDTO;
import com.projeto.ecommerceudemy.shared.ViaCepResponse;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    public List<EnderecoDTO> findAll() {
        List<Endereco> enderecos = repository.findAll();

        List<EnderecoDTO> dto = enderecos
                .stream()
                .map(endereco -> new ModelMapper().map(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());

        return dto;
    }

    public Optional<EnderecoDTO> findById(Long id) {

        Optional<Endereco> endereco = repository.findById(id);

        // TODO: Fazer verificação p/ ver se o id existe ou não e colocar exception

        EnderecoDTO dto = new ModelMapper().map(endereco.get(), EnderecoDTO.class);

        return Optional.of(dto);

    }

    public EnderecoDTO create(EnderecoDTO enderecoDTO) {

        enderecoDTO.setId(null);

        Endereco endereco = modelMapper.map(enderecoDTO, Endereco.class);

        endereco = getEnderecoByCep(endereco);

        endereco = repository.save(endereco);

        enderecoDTO.setId(endereco.getId());

        return enderecoDTO;

    }

    public EnderecoDTO update(Long id, EnderecoDTO enderecoDTO) {

        Optional<Endereco> enderecoExiste = repository.findById(id);

        if (enderecoExiste.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível atualizar o produto com o id " + id + " pois ele não existe.");
        }

        enderecoDTO.setId(id);
        Endereco endereco = modelMapper.map(enderecoDTO, Endereco.class);

        endereco = repository.save(endereco);

        return enderecoDTO;

    }

    public void delete(Long id) {

        Optional<Endereco> endereco = repository.findById(id);

        if (endereco.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível deletar o produto com o id " + id + " pois ele não existe.");
        }

        repository.deleteById(id);

    }

    public Endereco getEnderecoByCep(Endereco endereco) {
        RestTemplate restTemplate = new RestTemplate();
        String viaCepUrl = VIA_CEP_URL + endereco.getCep() + "/json";
        ViaCepResponse viaCepResponse = restTemplate.getForObject(viaCepUrl, ViaCepResponse.class);
        try {
            endereco.setCep(viaCepResponse.getCep());
            endereco.setRua(viaCepResponse.getLogradouro());
            endereco.setBairro(viaCepResponse.getBairro());
            endereco.setCidade(viaCepResponse.getLocalidade());
            endereco.setComplemento(viaCepResponse.getComplemento());
            endereco.setUf(viaCepResponse.getUf());
        } catch (NullPointerException e) {
            System.out.println("O cep deve ser válido. Mensagem: " + e.getMessage());
        }

        return endereco;
    }

}
