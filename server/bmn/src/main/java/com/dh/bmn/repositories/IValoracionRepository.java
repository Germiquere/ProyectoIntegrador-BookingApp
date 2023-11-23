package com.dh.bmn.repositories;

import com.dh.bmn.entity.Reserva;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IValoracionRepository extends JpaRepository<Valoracion, Long> {

    @Query("SELECT v FROM Valoracion v WHERE v.id = ?1")
    Optional<Valoracion> findById(Long id);

    @Query("SELECT v FROM Valoracion v WHERE v.bicicleta.bicicletaId = ?1")
    List<Valoracion> findByBicicletaId(Long bicicletaId);

    @Query("SELECT v FROM Valoracion v WHERE v.usuario.usuarioId = ?1")
    List<Valoracion> findByUsuarioId(Long idUsuario);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Valoracion v WHERE v.usuario = :usuario AND v.reserva = :reserva")
    boolean existsByUsuarioAndReserva(@Param("usuario") Usuario usuario, @Param("reserva") Reserva reserva);

}
