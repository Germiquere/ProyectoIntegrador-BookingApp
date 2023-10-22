package com.dh.bmn.repositories;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.CategoriaBicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBicicletaRepository extends JpaRepository<Bicicleta, Long> {

    @Query("SELECT b FROM Bicicleta b WHERE b.nombre = ?1 AND b.descripcion =?2")
    Optional<Bicicleta> findByNombreAndDescripcion(String nombre, String descripcion);
}
