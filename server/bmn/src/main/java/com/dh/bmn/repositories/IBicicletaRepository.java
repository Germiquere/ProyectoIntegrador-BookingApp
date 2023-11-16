package com.dh.bmn.repositories;

import com.dh.bmn.entity.Bicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBicicletaRepository extends JpaRepository<Bicicleta, Long> {

    @Query("SELECT b FROM Bicicleta b WHERE b.nombre = ?1")
    Optional<Bicicleta> findByNombre(String nombre);

    /*@Query("SELECT DISTINCT b FROM Bicicleta b " +
            "LEFT JOIN b.caracteristicas cb " +
            "LEFT JOIN b.categorias cat " +
            "WHERE LOWER(b.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cb.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cat.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " )
    List<Bicicleta> buscarBicicletasPorQuery(@Param("query") String query);*/

    @Query("SELECT DISTINCT b FROM Bicicleta b " +
            "LEFT JOIN b.caracteristicas cb " +
            "LEFT JOIN b.categorias cat " +
            "WHERE LOWER(b.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cb.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cat.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.nombre) IN :words " +  // Nueva condición para buscar palabras en nombre
            "   OR LOWER(b.descripcion) IN :words " +  // Nueva condición para buscar palabras en descripción
            "   OR LOWER(cb.nombre) IN :words " +  // Nueva condición para buscar palabras en características
            "   OR LOWER(cat.nombre) IN :words ")  // Nueva condición para buscar palabras en categorías
    List<Bicicleta> buscarBicicletasPorQuery(@Param("query") String query, @Param("words") List<String> words);

}
