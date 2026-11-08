package com.example.demo.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; //Para el linkTo

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import com.example.demo.exceptions.UsuarioNotFoundException;
import com.example.demo.model.Usuario;
import com.example.demo.servicios.ServicioUsuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class Controller_Usuario {

    private final ServicioUsuario usuarioService;

    public Controller_Usuario(ServicioUsuario usuarioService) {
        this.usuarioService = usuarioService;
    }

    /*
     * @PostMapping(value = "", consumes = { "application/json", "application/xml"
     * })
     * public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario
     * usuario) {
     * Usuario usuarioNuevo = usuarioService.save(usuario);
     * return
     * ResponseEntity.created(linkTo(Controller_Usuario.class).slash(usuarioNuevo.
     * getId()).toUri()).build();
     * }
     */

    @PostMapping(value = "", consumes = { "application/json", "application/xml" })
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioNuevo = usuarioService.save(usuario);
        EntityModel<Usuario> model = EntityModel.of(usuarioNuevo,
                linkTo(methodOn(Controller_Usuario.class).cogerUsuario(usuarioNuevo.getId())).withSelfRel());
        URI location = linkTo(methodOn(Controller_Usuario.class).cogerUsuario(usuarioNuevo.getId())).toUri();
        return ResponseEntity.created(location).body(model);
    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<EntityModel<Usuario>> cogerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
        EntityModel<Usuario> model = EntityModel.of(usuario,
                linkTo(methodOn(Controller_Usuario.class).cogerUsuario(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<Page<Usuario>> cogerUsuarios(
            @RequestParam(defaultValue = "", required = false) String starts_with,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<Usuario> usuarios = usuarioService.buscarUsuarios(starts_with, page, size);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @DeleteMapping("/{id}")
    public void borrarUsuarios(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}
