package com.itsqmet.conexion_db_ves.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByCategoria(String categoria);

    List<Vehiculo> findByCategoriaAndUnidadesDisponiblesGreaterThan(
            String categoria,
            Integer unidadesDisponibles
    );

    List<Vehiculo> findByPrecioPorDiaBetween(
            Double minimo,
            Double maximo
    );

    List<Vehiculo> findByModeloContainingIgnoreCase(
            String modelo
    );
}