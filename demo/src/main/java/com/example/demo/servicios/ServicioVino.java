// ServicioVino.java
package com.example.demo.servicios;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.model.Vino;
import com.example.demo.repositorios.RepositorioVino;

@Service
public class ServicioVino {

    private final RepositorioVino vinoRepository;

    public ServicioVino(RepositorioVino vinoRepository) {
        this.vinoRepository = vinoRepository;
    }

    public Optional<Vino> findById(Long id) {
        return vinoRepository.findById(id);
    }

    public Vino getById(Long id) {
        return vinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vino no encontrado: " + id));
    }

    public Page<Vino> buscarVinos(String starts_with, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        if (starts_with != null && !starts_with.isEmpty()) {
            return vinoRepository.findByNombreContaining(starts_with, paginable);
        } else {
            return vinoRepository.findAll(paginable);
        }
    }
}