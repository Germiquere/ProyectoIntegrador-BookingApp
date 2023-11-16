package com.dh.bmn.repositories;

import com.dh.bmn.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IFavoritoRepository extends JpaRepository<Favorito, Long> {

    @Query("SELECT f FROM Favorito f WHERE f.usuario.usuarioId = ?1")
    List<Favorito> findFavoritosByUsuarioId(Long idUsuario);

    @Query("SELECT f FROM Favorito f WHERE f.bicicleta.bicicletaId = ?1")
    List<Favorito> findFavoritosByBicicletaId(Long idBicicleta);

    @Query("SELECT f FROM Favorito f WHERE f.usuario.usuarioId = ?1 AND f.bicicleta.bicicletaId = ?2")
    Optional<Favorito> findByUsuarioAndBicicleta(Long idUsuario, Long idBicicleta);
}
