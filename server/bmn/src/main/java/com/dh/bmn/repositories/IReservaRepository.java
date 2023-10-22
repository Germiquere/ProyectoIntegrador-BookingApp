package com.dh.bmn.repositories;

import com.dh.bmn.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.bicicleta.bicicletaId = ?1 AND r.fechaInicio = ?2 AND r.fechaFin = ?3")
    Optional<Reserva> findByBicicletaAndFechaInicioAndFechaFin(Long idBicicleta, LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.usuarioId = ?1")
    List<Reserva> findReservasByUsuarioId(Long idUsuario);
}
