// ServicioUsuario.java
package com.example.demo.servicios;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.UsuarioMenorDeEdadException;
import com.example.demo.model.Usuario;
import com.example.demo.repositorios.RepositorioUsuario;

@Service
public class ServicioUsuario {

    private final RepositorioUsuario usuarioRepository;

    public ServicioUsuario(RepositorioUsuario usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(Usuario usuario) {
        LocalDate fechaNacimiento = LocalDate.parse(usuario.getFechaNacimiento(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < 18) {
            throw new UsuarioMenorDeEdadException(edad);
        }
        return usuarioRepository.save(usuario);
    }

    // Devuelve Optional — para que el controlador pueda lanzar su propia excepción
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Devuelve Usuario directamente — para uso interno en otros servicios
    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }

    public Page<Usuario> buscarUsuarios(String starts_with, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        if (starts_with != null && !starts_with.isEmpty()) {
            return usuarioRepository.findByNombreContaining(starts_with, paginable);
        } else {
            return usuarioRepository.findAll(paginable);
        }
    }

    public Usuario update(Long id, Usuario datos) {
        Usuario existente = getById(id);
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setFechaNacimiento(datos.getFechaNacimiento());
        return usuarioRepository.save(existente);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}