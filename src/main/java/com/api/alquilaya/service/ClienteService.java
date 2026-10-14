package com.api.alquilaya.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.alquilaya.models.ClienteModel;
import com.api.alquilaya.repositories.IClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    public List<ClienteModel> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public ClienteModel guardar(ClienteModel cliente) {
        return clienteRepository.save(cliente);
    }

    public ClienteModel actualizar(Long id, ClienteModel nuevo) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        cliente.setFirstName(nuevo.getFirstName());
        cliente.setLastName(nuevo.getLastName());
        cliente.setEmail(nuevo.getEmail());
        cliente.setDriversLicense(nuevo.getDriversLicense());
        cliente.setPhone(nuevo.getPhone());

        return clienteRepository.save(cliente);
    }

    public boolean eliminar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}