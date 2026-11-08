// Uva.java
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "uva")
public class Uva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private float porcentaje;

    @ManyToOne
    @JoinColumn(name = "vino_id")
    private Vino vino;

    public Uva() {
    }

    public Uva(String nombre, float porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        if (porcentaje >= 0 && porcentaje <= 100)
            this.porcentaje = porcentaje;
    }

    public Vino getVino() {
        return vino;
    }

    public void setVino(Vino vino) {
        this.vino = vino;
    }
}