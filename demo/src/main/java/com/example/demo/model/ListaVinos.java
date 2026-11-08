// ListaVinos.java
package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "listavinos")
public class ListaVinos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "vino_id")
    private Vino vino;

    private double puntuacion; // double para permitir decimales (0-10)
    private String fechaAnadido;

    public ListaVinos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vino getVino() {
        return vino;
    }

    public void setVino(Vino vino) {
        this.vino = vino;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getFechaAnadido() {
        return fechaAnadido;
    }

    public void setFechaAnadido(String fechaAnadido) {
        this.fechaAnadido = fechaAnadido;
    }
}