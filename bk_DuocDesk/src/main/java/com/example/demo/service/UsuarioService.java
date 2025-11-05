package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;

// Service para manejar la lógica de negocio relacionada con Usuario

@Service
public class UsuarioService {
@Autowired
    private UsuarioRepository usuarioRepository;

    // CAMBIO: Ahora devuelve una lista de DTOs
    public List<UsuarioDTO> getAllUsuarios() {
        // 1. Obtener la lista de Entidades de la BD
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        // 2. Convertir cada Entidad "Usuario" a un "UsuarioDTO"
        return usuarios.stream()
                .map(UsuarioDTO::new) // Llama al constructor: new UsuarioDTO(usuario)
                .collect(Collectors.toList());
    }

    // El resto de tus métodos (saveUsuario, getUsuarioById)
    // También deberían modificarse para usar DTOs, pero por ahora
    // dejemos solo el de listar.

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
