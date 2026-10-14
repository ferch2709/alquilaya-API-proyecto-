package com.api.alquilaya.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.service.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<VehiculoModel>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<VehiculoModel>> obtenerDisponibles() {
        return ResponseEntity.ok(vehiculoService.obtenerDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoModel> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehiculoModel> crear(@RequestBody VehiculoModel vehiculo) {
        return new ResponseEntity<>(vehiculoService.guardar(vehiculo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoModel> actualizar(@PathVariable Long id, @RequestBody VehiculoModel vehiculo) {
        try {
            return ResponseEntity.ok(vehiculoService.actualizar(id, vehiculo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vehiculoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<VehiculoModel> cambiarEstado(@PathVariable Long id, @PathVariable String estado) {
        try {
            return ResponseEntity.ok(vehiculoService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}