package com.dh.bmn.repositories;

import com.dh.bmn.entity.Bicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBicicletaRepository extends JpaRepository<Bicicleta, Long> {
}
