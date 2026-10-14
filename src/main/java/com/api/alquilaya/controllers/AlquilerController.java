package com.api.alquilaya.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.alquilaya.models.AlquilerModel;
import com.api.alquilaya.service.AlquilerService;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public ResponseEntity<List<AlquilerModel>> obtenerTodos() {
        return ResponseEntity.ok(alquilerService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlquilerModel> obtenerPorId(@PathVariable Long id) {
        return alquilerService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<AlquilerModel>> obtenerPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(alquilerService.obtenerPorCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<AlquilerModel> crear(@RequestBody AlquilerModel alquiler) {
        try {
            return new ResponseEntity<>(alquilerService.guardar(alquiler), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlquilerModel> actualizar(@PathVariable Long id, @RequestBody AlquilerModel alquiler) {
        try {
            return ResponseEntity.ok(alquilerService.actualizar(id, alquiler));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (alquilerService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}