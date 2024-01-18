package com.projeto.ecommerceudemy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ecommerceudemy.model.Categoria;
import com.projeto.ecommerceudemy.repository.CategoriaRepository;
import com.projeto.ecommerceudemy.shared.CategoriaDTO;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = repository.findAll();

        List<CategoriaDTO> dto = categorias
                .stream()
                .map(Categoria -> new ModelMapper().map(Categoria, CategoriaDTO.class))
                .collect(Collectors.toList());

        return dto;
    }

    public Optional<CategoriaDTO> findById(Long id) {

        Optional<Categoria> categoria = repository.findById(id);

        // TODO: Fazer verificação p/ ver se o id existe ou não e colocar exception

        CategoriaDTO dto = new ModelMapper().map(categoria.get(), CategoriaDTO.class);

        return Optional.of(dto);

    }

    public CategoriaDTO create(CategoriaDTO categoriaDTO) {

        categoriaDTO.setId(null);

        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);

        categoria = repository.save(categoria);

        categoriaDTO.setId(categoria.getId());

        return categoriaDTO;

    }

    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {

        Optional<Categoria> categoriaExiste = repository.findById(id);

        if (categoriaExiste.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível atualizar o produto com o id " + id + " pois ele não existe.");
        }

        categoriaDTO.setId(id);
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);

        categoria = repository.save(categoria);

        return categoriaDTO;

    }

    public void delete(Long id) {

        Optional<Categoria> categoria = repository.findById(id);

        if (categoria.isEmpty()) {
            throw new RuntimeException(
                    "Não foi possível deletar o produto com o id " + id + " pois ele não existe.");
        }

        repository.deleteById(id);

    }
}
