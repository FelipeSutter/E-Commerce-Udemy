package com.projeto.ecommerceudemy.service;

import org.springframework.web.client.RestTemplate;

import com.projeto.ecommerceudemy.model.Endereco;
import com.projeto.ecommerceudemy.shared.ViaCepResponse;

public class EnderecoService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

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
