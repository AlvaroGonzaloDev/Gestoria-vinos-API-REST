// Solicitud.java
package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "solicitud")
public class Solicitud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_solicitado_id")
    private Usuario usuarioSolicitado;

    @ManyToOne
    @JoinColumn(name = "usuario_solicitante_id")
    private Usuario usuarioSolicitante;

    private String estado; // "pendiente", "aceptada", "rechazada"

    public Solicitud() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuarioSolicitado() {
        return usuarioSolicitado;
    }

    public void setUsuarioSolicitado(Usuario usuarioSolicitado) {
        this.usuarioSolicitado = usuarioSolicitado;
    }

    public Usuario getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}