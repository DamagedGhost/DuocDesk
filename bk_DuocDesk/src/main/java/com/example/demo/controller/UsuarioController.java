package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // Ruta base para todos los endpoints de este controlador
public class UsuarioController {
@Autowired
    private UsuarioService usuarioService;

    // CAMBIO: El tipo de retorno ahora es List<UsuarioDTO>
    @GetMapping("/listar")
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // ... (resto de los métodos) ...
    @PostMapping("/crear")
    public Usuario createUsuario(@RequestBody Usuario nuevoUsuario) {
        return usuarioService.saveUsuario(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
    }

    @GetMapping("/prueba")
    public String prueba() {
        return "prueba desde UsuarioController\n" + Date.valueOf(java.time.LocalDate.now()).toString();
    }
    
}