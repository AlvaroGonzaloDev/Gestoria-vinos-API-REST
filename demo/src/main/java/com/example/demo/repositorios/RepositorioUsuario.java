// RepositorioUsuario.java
package com.example.demo.repositorios;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);

    Page<Usuario> findByNombreContaining(String patron, Pageable pageable);
}