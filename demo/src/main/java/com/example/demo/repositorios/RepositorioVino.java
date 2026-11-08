// RepositorioVino.java
package com.example.demo.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Vino;

public interface RepositorioVino extends JpaRepository<Vino, Long> {
    Page<Vino> findByNombreContaining(String nombre, Pageable pageable);

    Page<Vino> findByTipo(String tipo, Pageable pageable);

    Page<Vino> findByOrigen(String origen, Pageable pageable);

    Page<Vino> findByBodega(String bodega, Pageable pageable);

    Page<Vino> findByAnioBotella(int anio, Pageable pageable);
}