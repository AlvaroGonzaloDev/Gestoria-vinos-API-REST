package com.example.demo.controladores;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; //Para el linkTo

import com.example.demo.exceptions.UsuarioNotFoundException;
import com.example.demo.model.Usuario;
import com.example.demo.model.Vino;
import com.example.demo.servicios.ServicioVino;

@RestController
@RequestMapping("/vinos")
public class Controller_Vino {

    private final ServicioVino vinoService;

    public Controller_Vino(ServicioVino vinoService) {
        this.vinoService = vinoService;
    }

    /*
     * @GetMapping(value = "", produces = { "application/json" })
     * public ResponseEntity<List<Vino>> cogerVinos() {
     * return ResponseEntity.ok(vinoService.takeAll());
     * }
     */

    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<Page<Vino>> cogerVinos(
            @RequestParam(defaultValue = "", required = false) String starts_with,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<Vino> vinos = vinoService.buscarVinos(starts_with, page, size);
        return ResponseEntity.ok(vinos);
    }

    /*
     * @GetMapping(value = "/{id}", produces = { "application/json" })
     * public ResponseEntity<Vino> cogerVinoId2(@PathVariable Long id) {
     * return ResponseEntity.ok(vinoService.findById(id));
     * }
     */

    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<EntityModel<Vino>> cogerVinoId(@PathVariable Long id) {
        Vino vino = vinoService.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
        EntityModel<Vino> model = EntityModel.of(vino,
                linkTo(methodOn(Controller_Vino.class).cogerVinoId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }
}
