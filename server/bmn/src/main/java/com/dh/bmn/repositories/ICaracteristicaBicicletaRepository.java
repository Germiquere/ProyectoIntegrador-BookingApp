package com.dh.bmn.repositories;

import com.dh.bmn.entity.CaracteristicaBicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICaracteristicaBicicletaRepository extends JpaRepository<CaracteristicaBicicleta, Long> {
    @Query("SELECT c FROM CaracteristicaBicicleta c WHERE c.nombre = ?1")
    Optional<CaracteristicaBicicleta> findByNombre(String nombre);

}
