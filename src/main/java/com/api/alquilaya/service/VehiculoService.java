package com.api.alquilaya.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    public List<VehiculoModel> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    public List<VehiculoModel> obtenerDisponibles() {
        return vehiculoRepository.findByEstado("DISPONIBLE");
    }

    public Optional<VehiculoModel> obtenerPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    public VehiculoModel guardar(VehiculoModel vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoModel actualizar(Long id, VehiculoModel nuevo) {
        VehiculoModel vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con ID: " + id));

        vehiculo.setMarca(nuevo.getMarca());
        vehiculo.setModelo(nuevo.getModelo());
        vehiculo.setAnio(nuevo.getAnio());
        vehiculo.setMatricula(nuevo.getMatricula());
        vehiculo.setTarifaDiaria(nuevo.getTarifaDiaria());
        vehiculo.setEstado(nuevo.getEstado());

        return vehiculoRepository.save(vehiculo);
    }

    public boolean eliminar(Long id) {
        if (vehiculoRepository.existsById(id)) {
            vehiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public VehiculoModel cambiarEstado(Long id, String nuevoEstado) {
        VehiculoModel vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));
        vehiculo.setEstado(nuevoEstado.toUpperCase());
        return vehiculoRepository.save(vehiculo);
    }
}