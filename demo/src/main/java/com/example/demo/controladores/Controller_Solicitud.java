// Controller_Solicitud.java
package com.example.demo.controladores;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ListaVinos;
import com.example.demo.model.Solicitud;
import com.example.demo.servicios.ServicioSolicitud;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/usuarios")
public class Controller_Solicitud {

    private final ServicioSolicitud solicitudService;

    public Controller_Solicitud(ServicioSolicitud solicitudService) {
        this.solicitudService = solicitudService;
    }

    /*
     * @PostMapping(value = "/{id}/solicitudes/{otherId}", consumes = {
     * "application/json", "application/xml" })
     * public ResponseEntity<Solicitud> mandarSolicitud(@PathVariable Long id,
     * 
     * @PathVariable Long otherId) {
     * Solicitud solicitudNueva = solicitudService.enviarSolicitud(id, otherId);
     * return ResponseEntity.created(linkTo(Controller_Solicitud.class).slash(
     * solicitudNueva.getId()).toUri()).build();
     * }
     */

    @PostMapping(value = "/{id}/solicitudes/{otherId}", consumes = { "application/json", "application/xml" })
    public ResponseEntity<EntityModel<Solicitud>> mandarSolicitud(@PathVariable Long id, @PathVariable Long otherId) {

        Solicitud solicitudNueva = solicitudService.enviarSolicitud(id, otherId);

        EntityModel<Solicitud> model = EntityModel.of(solicitudNueva,
                linkTo(methodOn(Controller_Solicitud.class).getSolicitudesEnviadas(id, null, 0, 2))
                        .withRel("solicitudes_enviadas"),
                linkTo(methodOn(Controller_Solicitud.class).getSolicitudesRecibidas(otherId, null, 0, 2))
                        .withRel("solicitudes_recibidas"));

        URI location = linkTo(methodOn(Controller_Solicitud.class).getSolicitudesEnviadas(id, null, 0, 2)).toUri();

        return ResponseEntity.created(location).body(model);
    }

    /*
     * @GetMapping(value = "/{id}/solicitudes/enviadas", produces = {
     * "application/json" })
     * public ResponseEntity<List<Solicitud>> getSolicitudesEnviadas(@PathVariable
     * Long id) {
     * return ResponseEntity.ok(solicitudService.getSolicitudesEnviadas(id));
     * }
     */

    @GetMapping(value = "/{id}/solicitudes/enviadas", produces = { "application/json" })
    public ResponseEntity<Page<Solicitud>> getSolicitudesEnviadas(
            @PathVariable Long id,
            @RequestParam(defaultValue = "", required = false) String starts_with,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<Solicitud> solicitud = solicitudService.getSolicitudesEnviadas(starts_with, page, size, id);
        return ResponseEntity.ok(solicitud);
    }

    /*
     * @GetMapping(value = "/{id}/solicitudes/recibidas", produces = {
     * "application/json" })
     * public ResponseEntity<List<Solicitud>> getSolicitudesRecibidas(@PathVariable
     * Long id) {
     * return ResponseEntity.ok(solicitudService.getSolicitudesRecibidas(id));
     * }
     */

    @GetMapping(value = "/{id}/solicitudes/recibidas", produces = { "application/json" })
    public ResponseEntity<Page<Solicitud>> getSolicitudesRecibidas(
            @PathVariable Long id,
            @RequestParam(defaultValue = "", required = false) String starts_with,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<Solicitud> solicitud = solicitudService.getSolicitudesRecibidas(starts_with, page, size, id);
        return ResponseEntity.ok(solicitud);
    }

    @PutMapping("/{id}/solicitudes/{otherId}/aceptar")
    public ResponseEntity<Solicitud> aceptarSolicitud(@PathVariable Long id,
            @PathVariable Long otherId) {
        return ResponseEntity.ok(solicitudService.aceptarSolicitud(id, otherId));
    }

    @PutMapping("/{id}/solicitudes/{otherId}/rechazar")
    public ResponseEntity<Solicitud> rechazarSolicitud(@PathVariable Long id,
            @PathVariable Long otherId) {
        return ResponseEntity.ok(solicitudService.rechazarSolicitud(id, otherId));
    }

    @DeleteMapping("/{id}/solicitudes/{otherId}")
    public ResponseEntity<Void> cancelarSolicitud(@PathVariable Long id,
            @PathVariable Long otherId) {
        solicitudService.cancelarSolicitud(id, otherId);
        return ResponseEntity.noContent().build();
    }
}