package com.itsqmet.conexion_db_ves.controller;


import com.itsqmet.conexion_db_ves.model.Producto;
import com.itsqmet.conexion_db_ves.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos(){
        List<Producto> productos = productoService.obtenerTodo();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return productoService.buscarporId(id)
                .map(producto -> ResponseEntity.ok((Object) producto))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Producto con id " + id + " no encontrado")));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Producto producto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        Producto nuevo = productoService.crearproducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado correctamente")); // 200
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Producto con id " + id + " no encontrado")); // 404
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar( @PathVariable Long id, @Valid @RequestBody Producto producto,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        return productoService.actualizar(id, producto)
                .map(actualizado -> ResponseEntity.ok((Object) actualizado))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Producto con id " + id + " no encontrado")));
    }

}
