package com.dh.bmn.repositories;

import com.dh.bmn.entity.Bicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBicicletaRepository extends JpaRepository<Bicicleta, Long> {

    @Query("SELECT b FROM Bicicleta b WHERE b.nombre = ?1")
    Optional<Bicicleta> findByNombre(String nombre);
}
