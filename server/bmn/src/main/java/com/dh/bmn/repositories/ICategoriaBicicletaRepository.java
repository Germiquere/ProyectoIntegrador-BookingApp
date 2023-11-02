package com.dh.bmn.repositories;

import com.dh.bmn.entity.CategoriaBicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoriaBicicletaRepository extends JpaRepository<CategoriaBicicleta, Long> {
    @Query("SELECT c FROM CategoriaBicicleta c WHERE c.nombre = ?1")
    Optional<CategoriaBicicleta> findByNombre(String nombre);

}
