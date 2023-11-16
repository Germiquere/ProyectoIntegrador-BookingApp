package com.dh.bmn.repositories;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Politica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPoliticaRepository extends JpaRepository<Politica, Long> {

    @Query("SELECT p FROM Politica p WHERE p.titulo = ?1")
    Optional<Politica> findByTitulo(String titulo);

}
