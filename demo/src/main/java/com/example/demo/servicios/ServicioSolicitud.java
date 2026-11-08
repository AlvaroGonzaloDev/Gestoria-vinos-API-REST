// ServicioSolicitud.java
package com.example.demo.servicios;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.model.Solicitud;
import com.example.demo.model.Usuario;
import com.example.demo.repositorios.RepositorioSolicitud;

@Service
public class ServicioSolicitud {

    private final RepositorioSolicitud solicitudRepository;
    private final ServicioUsuario servicioUsuario;

    public ServicioSolicitud(RepositorioSolicitud solicitudRepository,
            ServicioUsuario servicioUsuario) {
        this.solicitudRepository = solicitudRepository;
        this.servicioUsuario = servicioUsuario;
    }

    public Solicitud enviarSolicitud(Long solicitanteId, Long solicitadoId) {
        // getById devuelve Usuario directamente, sin Optional
        Usuario solicitante = servicioUsuario.getById(solicitanteId);
        Usuario solicitado = servicioUsuario.getById(solicitadoId);

        Solicitud s = new Solicitud();
        s.setUsuarioSolicitante(solicitante);
        s.setUsuarioSolicitado(solicitado);
        s.setEstado("pendiente");
        return solicitudRepository.save(s);
    }

    // Solicitudes que yo (solicitanteId) he enviado
    public Page<Solicitud> getSolicitudesEnviadas(String starts_with, int page, int size, Long solicitanteId) {
        Pageable paginable = PageRequest.of(page, size);
        if (starts_with != null && !starts_with.isEmpty()) {
            // Filtra por nombre del usuario solicitado
            return solicitudRepository
                    .findByUsuarioSolicitanteIdAndUsuarioSolicitanteNombreContaining(solicitanteId, starts_with,
                            paginable);
        }
        return solicitudRepository.findByUsuarioSolicitanteId(solicitanteId, paginable);
    }

    // Solicitudes que yo (solicitadoId) he recibido
    public Page<Solicitud> getSolicitudesRecibidas(String starts_with, int page, int size, Long solicitadoId) {
        Pageable paginable = PageRequest.of(page, size);
        if (starts_with != null && !starts_with.isEmpty()) {
            return solicitudRepository
                    .findByUsuarioSolicitadoIdAndUsuarioSolicitanteNombreContaining(solicitadoId, starts_with,
                            paginable);
        }
        return solicitudRepository.findByUsuarioSolicitadoId(solicitadoId, paginable);
    }

    public Solicitud getSolicitudEnviada(Long solicitanteId, Long solicitadoId) {
        return solicitudRepository
                .findByUsuarioSolicitanteIdAndUsuarioSolicitadoId(solicitanteId, solicitadoId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    public Solicitud getSolicitudRecibida(Long solicitadoId, Long solicitanteId) {
        return solicitudRepository
                .findByUsuarioSolicitanteIdAndUsuarioSolicitadoId(solicitanteId, solicitadoId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    public Solicitud aceptarSolicitud(Long solicitadoId, Long solicitanteId) {
        Solicitud s = getSolicitudRecibida(solicitadoId, solicitanteId);
        s.setEstado("aceptada");
        return solicitudRepository.save(s);
    }

    public Solicitud rechazarSolicitud(Long solicitadoId, Long solicitanteId) {
        Solicitud s = getSolicitudRecibida(solicitadoId, solicitanteId);
        s.setEstado("rechazada");
        return solicitudRepository.save(s);
    }

    public void cancelarSolicitud(Long solicitanteId, Long solicitadoId) {
        Solicitud s = getSolicitudEnviada(solicitanteId, solicitadoId);
        solicitudRepository.delete(s);
    }
}