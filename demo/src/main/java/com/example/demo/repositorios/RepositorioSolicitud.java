// RepositorioSolicitud.java
package com.example.demo.repositorios;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Solicitud;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long> {
    Page<Solicitud> findByUsuarioSolicitanteId(Long id, Pageable pageable);

    Page<Solicitud> findByUsuarioSolicitadoId(Long id, Pageable pageable);

    Page<Solicitud> findByUsuarioSolicitanteIdAndUsuarioSolicitanteNombreContaining(
            Long id, String nombre, Pageable pageable);

    Page<Solicitud> findByUsuarioSolicitadoIdAndUsuarioSolicitanteNombreContaining(
            Long id, String nombre, Pageable pageable);

    Optional<Solicitud> findByUsuarioSolicitanteIdAndUsuarioSolicitadoId(
            Long solicitanteId, Long solicitadoId);
}