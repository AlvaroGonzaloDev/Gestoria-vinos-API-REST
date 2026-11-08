// Vino.java
package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "vino")
public class Vino implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String bodega;
    private int anioBotella;
    private String origen;
    private String tipo;

    @OneToMany(mappedBy = "vino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Uva> listaUvas = new ArrayList<>();

    public Vino() {
    }

    public Vino(String nombre, String bodega, int anioBotella, String origen, String tipo) {
        this.nombre = nombre;
        this.bodega = bodega;
        this.anioBotella = anioBotella;
        this.origen = origen;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public int getAnioBotella() {
        return anioBotella;
    }

    public void setAnioBotella(int anioBotella) {
        this.anioBotella = anioBotella;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Uva> getListaUvas() {
        return listaUvas;
    }

    public void setListaUvas(List<Uva> listaUvas) {
        this.listaUvas = listaUvas;
    }
}