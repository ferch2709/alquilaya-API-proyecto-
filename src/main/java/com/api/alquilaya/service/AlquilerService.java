package com.api.alquilaya.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.alquilaya.models.AlquilerModel;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IAlquilerRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class AlquilerService {

    @Autowired
    private IAlquilerRepository alquilerRepository;

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    public List<AlquilerModel> obtenerTodos() {
        return alquilerRepository.findAll();
    }

    public Optional<AlquilerModel> obtenerPorId(Long id) {
        return alquilerRepository.findById(id);
    }

    public List<AlquilerModel> obtenerPorCliente(Long idCliente) {
        return alquilerRepository.findByClienteIdCliente(idCliente);
    }

    public AlquilerModel guardar(AlquilerModel alquiler) {
        VehiculoModel vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no existe"));

        long dias = ChronoUnit.DAYS.between(alquiler.getFechaAlquiler(), alquiler.getFechaDevolucion());
        if (dias <= 0) {
            dias = 1;
        }

        BigDecimal total = vehiculo.getTarifaDiaria().multiply(BigDecimal.valueOf(dias));
        alquiler.setPrecioTotal(total);

        vehiculo.setEstado("ALQUILADO");
        vehiculoRepository.save(vehiculo);

        return alquilerRepository.save(alquiler);
    }

    public AlquilerModel actualizar(Long id, AlquilerModel nuevo) {
        AlquilerModel alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado con ID: " + id));

        alquiler.setFechaDevolucion(nuevo.getFechaDevolucion());
        alquiler.setEstado(nuevo.getEstado());

        if ("COMPLETADO".equalsIgnoreCase(nuevo.getEstado())) {
            VehiculoModel vehiculo = alquiler.getVehiculo();
            vehiculo.setEstado("DISPONIBLE");
            vehiculoRepository.save(vehiculo);
        }

        return alquilerRepository.save(alquiler);
    }

    public boolean eliminar(Long id) {
        if (alquilerRepository.existsById(id)) {
            alquilerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}