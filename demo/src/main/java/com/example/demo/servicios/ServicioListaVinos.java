// ServicioListaVinos.java
package com.example.demo.servicios;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.model.ListaVinos;
import com.example.demo.model.Usuario;
import com.example.demo.model.Vino;
import com.example.demo.repositorios.RepositorioListaVinos;

@Service
public class ServicioListaVinos {

    private final RepositorioListaVinos listaVinosRepository;
    private final ServicioUsuario servicioUsuario;
    private final ServicioVino servicioVino;

    public ServicioListaVinos(RepositorioListaVinos listaVinosRepository,
            ServicioUsuario servicioUsuario,
            ServicioVino servicioVino) {
        this.listaVinosRepository = listaVinosRepository;
        this.servicioUsuario = servicioUsuario;
        this.servicioVino = servicioVino;
    }

    public ListaVinos anadirVino(Long usuarioId, Long vinoId, double puntuacion, String fechaAnadido) {
        // Usamos getById para obtener la entidad directamente (lanza excepción si no
        // existe)
        Usuario usuario = servicioUsuario.getById(usuarioId);
        Vino vino = servicioVino.getById(vinoId);

        ListaVinos entrada = new ListaVinos();
        entrada.setUsuario(usuario);
        entrada.setVino(vino);
        entrada.setPuntuacion(puntuacion);
        entrada.setFechaAnadido(fechaAnadido);

        return listaVinosRepository.save(entrada);
    }

    public Optional<ListaVinos> getVinoDeUsuario(Long usuarioId, Long vinoId) {
        return listaVinosRepository.findByUsuarioIdAndVinoId(usuarioId, vinoId);
    }

    // Versión interna que lanza excepción — para borrar y actualizar
    private ListaVinos getVinoDeUsuarioOrThrow(Long usuarioId, Long vinoId) {
        return listaVinosRepository.findByUsuarioIdAndVinoId(usuarioId, vinoId)
                .orElseThrow(() -> new RuntimeException("Vino no encontrado en la lista del usuario"));
    }

    public Page<ListaVinos> buscarVinos(String fecha, int page, int size, Long usuarioId) {
        Pageable paginable = PageRequest.of(page, size);
        if (fecha != null && !fecha.isEmpty()) {
            return listaVinosRepository.findByUsuarioIdAndFechaAnadidoContaining(usuarioId, fecha, paginable);
        } else {
            return listaVinosRepository.findByUsuarioId(usuarioId, paginable);
        }
    }

    public void borrarVino(Long usuarioId, Long vinoId) {
        ListaVinos entrada = getVinoDeUsuarioOrThrow(usuarioId, vinoId);
        listaVinosRepository.delete(entrada);
    }

    public ListaVinos actualizarPuntuacion(Long usuarioId, Long vinoId, double nuevaPuntuacion) {
        ListaVinos entrada = getVinoDeUsuarioOrThrow(usuarioId, vinoId);
        entrada.setPuntuacion(nuevaPuntuacion);
        return listaVinosRepository.save(entrada);
    }
}