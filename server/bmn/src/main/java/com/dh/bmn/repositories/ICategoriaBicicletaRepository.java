package com.dh.bmn.repositories;

import com.dh.bmn.entity.CategoriaBicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaBicicletaRepository extends JpaRepository<CategoriaBicicleta, Long> {
}
