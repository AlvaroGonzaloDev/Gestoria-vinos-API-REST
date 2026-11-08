// RepositorioListaVinos.java
package com.example.demo.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ListaVinos;

public interface RepositorioListaVinos extends JpaRepository<ListaVinos, Long> {
    List<ListaVinos> findByUsuarioId(Long usuarioId);

    Optional<ListaVinos> findByUsuarioIdAndVinoId(Long usuarioId, Long vinoId);

    // Filtro por fecha y paginación
    Page<ListaVinos> findByUsuarioIdAndFechaAnadidoContaining(Long usuarioId, String fecha, Pageable pageable);

    Page<ListaVinos> findByUsuarioId(Long usuarioId, Pageable pageable);
}