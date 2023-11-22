package com.dh.bmn.repositories;

import com.dh.bmn.entity.Bicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBicicletaRepository extends JpaRepository<Bicicleta, Long> {

    @Query("SELECT b FROM Bicicleta b WHERE b.nombre = ?1")
    Optional<Bicicleta> findByNombre(String nombre);

    @Query("SELECT DISTINCT b FROM Bicicleta b " +
            "LEFT JOIN b.caracteristicas cb " +
            "LEFT JOIN b.categorias cat " +
            "WHERE LOWER(b.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cb.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cat.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.nombre) IN :words " +
            "   OR LOWER(b.descripcion) IN :words " +
            "   OR LOWER(cb.nombre) IN :words " +
            "   OR LOWER(cat.nombre) IN :words ")
    List<Bicicleta> buscarBicicletasPorQuery(@Param("query") String query, @Param("words") List<String> words);

    @Query("SELECT DISTINCT b FROM Bicicleta b " +
            "LEFT JOIN b.caracteristicas cb " +
            "LEFT JOIN b.categorias cat " +
            "LEFT JOIN b.reservas r " +
            "WHERE (LOWER(b.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cb.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(cat.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.nombre) IN :words " +
            "   OR LOWER(b.descripcion) IN :words " +
            "   OR LOWER(cb.nombre) IN :words " +
            "   OR LOWER(cat.nombre) IN :words) " +
            "   AND NOT EXISTS (SELECT 1 FROM Reserva r2 " +
            "                   WHERE r2.bicicleta = b " +
            "                   AND (:fechaInicio BETWEEN r2.fechaInicio AND r2.fechaFin " +
            "                   OR :fechaFin BETWEEN r2.fechaInicio AND r2.fechaFin))")
    List<Bicicleta> buscarBicicletasPorQueryYDisponibilidad(@Param("query") String query, @Param("words") List<String> words,
                                                            @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);


}
