// Controller_ListaVinos.java
package com.example.demo.controladores;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exceptions.UsuarioNotFoundException;
import com.example.demo.model.ListaVinos;
import com.example.demo.model.Vino;
import com.example.demo.servicios.ServicioListaVinos;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/usuarios")
public class Controller_ListaVinos {

    private final ServicioListaVinos listaVinosService;

    public Controller_ListaVinos(ServicioListaVinos listaVinosService) {
        this.listaVinosService = listaVinosService;
    }

    @PostMapping(value = "/{id}/vinos", consumes = { "application/json", "application/xml" })
    public ResponseEntity<EntityModel<ListaVinos>> anadirVino(
            @PathVariable Long id,
            @RequestParam Long vinoId,
            @RequestParam double puntuacion,
            @RequestParam String fechaAnadido) {

        ListaVinos nuevo = listaVinosService.anadirVino(id, vinoId, puntuacion, fechaAnadido);

        EntityModel<ListaVinos> model = EntityModel.of(nuevo,
                linkTo(methodOn(Controller_ListaVinos.class)
                        .getVino(id, vinoId)).withSelfRel());

        URI location = linkTo(methodOn(Controller_ListaVinos.class).getVino(id, vinoId)).toUri();

        return ResponseEntity.created(location).body(model);
    }

    /*
     * @GetMapping(value = "/{id}/vinos", produces = { "application/json" })
     * public ResponseEntity<List<ListaVinos>> getVinos(@PathVariable Long id) {
     * return ResponseEntity.ok(listaVinosService.getVinosDeUsuario(id));
     * }
     */

    @GetMapping(value = "/{id}/vinos", produces = { "application/json" })
    public ResponseEntity<Page<ListaVinos>> getVinos(
            @PathVariable Long id,
            @RequestParam(defaultValue = "", required = false) String starts_with,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<ListaVinos> listavinos = listaVinosService.buscarVinos(starts_with, page, size, id);
        return ResponseEntity.ok(listavinos);
    }

    /*
     * @GetMapping(value = "/{id}/vinos/{vinoId}", produces = { "application/json"
     * })
     * public ResponseEntity<ListaVinos> getVino(@PathVariable Long
     * id, @PathVariable Long vinoId) {
     * return ResponseEntity.ok(listaVinosService.getVinoDeUsuario(id, vinoId));
     * }
     */

    @GetMapping(value = "/{id}/vinos/{vinoId}", produces = { "application/json" })
    public ResponseEntity<EntityModel<ListaVinos>> getVino(@PathVariable Long id, @PathVariable Long vinoId) {
        ListaVinos listavinos = listaVinosService.getVinoDeUsuario(id, vinoId)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        EntityModel<ListaVinos> model = EntityModel.of(listavinos,
                linkTo(methodOn(Controller_ListaVinos.class).getVino(id, vinoId)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}/vinos/{vinoId}")
    public ResponseEntity<ListaVinos> actualizarPuntuacion(@PathVariable Long id,
            @PathVariable Long vinoId,
            @RequestParam double puntuacion) {
        return ResponseEntity.ok(listaVinosService.actualizarPuntuacion(id, vinoId, puntuacion));
    }

    @DeleteMapping("/{id}/vinos/{vinoId}")
    public ResponseEntity<Void> borrarVino(@PathVariable Long id, @PathVariable Long vinoId) {
        listaVinosService.borrarVino(id, vinoId);
        return ResponseEntity.noContent().build();
    }
}