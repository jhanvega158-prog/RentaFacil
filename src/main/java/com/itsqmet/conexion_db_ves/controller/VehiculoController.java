package com.itsqmet.conexion_db_ves.controller;

import com.itsqmet.conexion_db_ves.model.Vehiculo;
import com.itsqmet.conexion_db_ves.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // Obtener todos los vehículos
    @GetMapping
    public List<Vehiculo> obtenerTodos() {
        return vehiculoService.obtenerTodos();
    }

    // Obtener vehículo por ID
    @GetMapping("/{id}")
    public Vehiculo obtenerPorId(@PathVariable Long id) {
        return vehiculoService.buscarPorId(id);
    }

    // Guardar vehículo
    @PostMapping
    public Vehiculo guardar(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.guardar(vehiculo);
    }

    // Actualizar vehículo
    @PutMapping("/{id}")
    public Vehiculo actualizar(
            @PathVariable Long id,
            @RequestBody Vehiculo vehiculo) {

        Vehiculo existente = vehiculoService.buscarPorId(id);

        if (existente != null) {
            existente.setModelo(vehiculo.getModelo());
            existente.setCategoria(vehiculo.getCategoria());
            existente.setDescripcion(vehiculo.getDescripcion());
            existente.setPrecioPorDia(vehiculo.getPrecioPorDia());
            existente.setUnidadesDisponibles(
                    vehiculo.getUnidadesDisponibles()
            );

            return vehiculoService.guardar(existente);
        }

        return null;
    }

    // Eliminar vehículo
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return "Vehículo eliminado correctamente";
    }

    // CONSULTA PERSONALIZADA 1
    // Buscar por categoría con unidades disponibles
    @GetMapping("/categoria/{categoria}")
    public List<Vehiculo> buscarPorCategoria(
            @PathVariable String categoria) {

        return vehiculoService
                .buscarPorCategoriaDisponibles(categoria);
    }

    // CONSULTA PERSONALIZADA 2
    // Buscar por rango de precio
    @GetMapping("/precio")
    public List<Vehiculo> buscarPorPrecio(
            @RequestParam Double minimo,
            @RequestParam Double maximo) {

        return vehiculoService
                .buscarPorRangoPrecio(minimo, maximo);
    }

    // CONSULTA PERSONALIZADA 3
    // Buscar por modelo
    @GetMapping("/modelo/{modelo}")
    public List<Vehiculo> buscarPorModelo(
            @PathVariable String modelo) {

        return vehiculoService.buscarPorModelo(modelo);
    }
}