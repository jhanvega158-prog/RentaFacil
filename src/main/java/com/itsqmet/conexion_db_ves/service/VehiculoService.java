package com.itsqmet.conexion_db_ves.service;

import com.itsqmet.conexion_db_ves.model.Vehiculo;
import com.itsqmet.conexion_db_ves.model.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Mostrar todos los vehículos
    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    // Guardar vehículo
    public Vehiculo guardar(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // Buscar por ID
    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    // Eliminar
    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
    }

    // Consulta personalizada 1
    // Vehículos de una categoría con unidades disponibles
    public List<Vehiculo> buscarPorCategoriaDisponibles(String categoria) {
        return vehiculoRepository
                .findByCategoriaAndUnidadesDisponiblesGreaterThan(
                        categoria, 0
                );
    }

    // Consulta personalizada 2
    // Vehículos por rango de precio
    public List<Vehiculo> buscarPorRangoPrecio(
            Double minimo,
            Double maximo
    ) {
        return vehiculoRepository.findByPrecioPorDiaBetween(
                minimo,
                maximo
        );
    }

    // Consulta personalizada 3
    // Buscar por modelo
    public List<Vehiculo> buscarPorModelo(String modelo) {
        return vehiculoRepository
                .findByModeloContainingIgnoreCase(modelo);
    }
}