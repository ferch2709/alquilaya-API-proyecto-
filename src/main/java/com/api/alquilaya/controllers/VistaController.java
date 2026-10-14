package com.api.alquilaya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.api.alquilaya.service.VehiculoService;
import com.api.alquilaya.service.AlquilerService;
import com.api.alquilaya.service.ClienteService;

@Controller
public class VistaController {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("vehiculos", vehiculoService.obtenerTodos());
        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("alquileres", alquilerService.obtenerTodos());
        return "index";
    }
}