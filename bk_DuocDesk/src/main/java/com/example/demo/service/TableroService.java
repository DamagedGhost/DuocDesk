package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Importamos modelos y repositorios
import com.example.demo.model.Tablero;
import com.example.demo.model.Usuario;
import com.example.demo.model.Roles;
import com.example.demo.model.UsuarioTableroRol;
import com.example.demo.repository.TableroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsuarioTableroRolRepository;

@Service
public class TableroService {

    @Autowired
    private TableroRepository tableroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UsuarioTableroRolRepository utrRepository;

    @Transactional
    public Tablero crearTableroParaUsuario(Tablero nuevoTablero, int idUsuarioCreador) {
        
        Usuario creador = usuarioRepository.findById(idUsuarioCreador)
                .orElseThrow(() -> new RuntimeException("Error de negocio: El usuario " + idUsuarioCreador + " no existe."));

        // ----- CAMBIO AQUÍ -----
        // Buscamos el rol por ID = 2 (el que corresponde a OWNER en tu BD)
        Roles rolDueño = rolesRepository.findById(2) 
                .orElseThrow(() -> new RuntimeException("Error de sistema: El Rol ID 2 (OWNER) no está configurado en la BD."));
        // ----- FIN DEL CAMBIO -----

        Tablero tableroGuardado = tableroRepository.save(nuevoTablero);

        UsuarioTableroRol relacion = new UsuarioTableroRol(); 
        relacion.setUsuario(creador);
        relacion.setTablero(tableroGuardado);
        relacion.setRol(rolDueño);
        
        utrRepository.save(relacion);

        return tableroGuardado;
    }

    // --- Métodos CRUD básicos ---
    public List<Tablero> getAllTableros() {
        return tableroRepository.findAll();
    }

    public Tablero getTableroById(Integer id) {
        return tableroRepository.findById(id).orElse(null);
    }

    public void deleteTablero(Integer id) {
        tableroRepository.deleteById(id);
    }
}