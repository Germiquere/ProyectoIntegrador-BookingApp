package com.dh.bmn.services;

public interface ICaracteristicaBicicletaService {
    void agregarCaracteristicaABicicleta(Long bicicletaId, Long caracteristicaId);
    void quitarCaracteristicaDeBicicleta(Long bicicletaId, Long caracteristicaId);
}
