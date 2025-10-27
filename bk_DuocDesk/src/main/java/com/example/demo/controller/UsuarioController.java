package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // Ruta base para todos los endpoints de este controlador
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para OBTENER todos los usuarios
    // Prueba en Postman: GET http://localhost:8080/api/usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Endpoint para CREAR un nuevo usuario
    // Prueba en Postman: POST http://localhost:8080/api/usuarios
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario nuevoUsuario) {
        // Nota: Necesitamos añadir 'saveUsuario' a nuestro servicio
        return usuarioService.saveUsuario(nuevoUsuario);
    }

    @GetMapping("/saludo")
    public String saludo() {
        return "¡Hola desde UsuarioController!";
    }
}