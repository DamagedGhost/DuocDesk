package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Tablero;
import com.example.demo.service.TableroService;

@RestController
@RequestMapping("/api") // Moveremos la ruta base aquí
public class TableroController {

    @Autowired
    private TableroService tableroService;

    /**
     * Este es el nuevo endpoint que sigue la lógica RESTful.
     * Para crear un tablero, debes decir PARA QUÉ USUARIO es.
     *
     * URL: POST http://localhost:8080/api/usuarios/100/tableros
     * (Significa: "Crear un tablero para el usuario 100")
     */
    @PostMapping("/usuarios/{idUsuario}/tableros")
    public Tablero createTablero(
            @PathVariable int idUsuario, // <-- Obtiene el '100' de la URL
            @RequestBody Tablero nuevoTablero // <-- Obtiene el JSON del body
    ) {
        // Llama al SERVICIO (el cerebro) con toda la info necesaria
        return tableroService.crearTableroParaUsuario(nuevoTablero, idUsuario);
    }


    // --- Endpoints CRUD básicos ---

    // GET http://localhost:8080/api/tableros
    @GetMapping("/tableros")
    public List<Tablero> getAllTableros() {
        return tableroService.getAllTableros();
    }

    // GET http://localhost:8080/api/tableros/5
    @GetMapping("/tableros/{id}")
    public Tablero getTableroById(@PathVariable Integer id) {
        return tableroService.getTableroById(id);
    }

    // DELETE http://localhost:8080/api/tableros/5
    @DeleteMapping("/tableros/{id}")
    public void deleteTablero(@PathVariable Integer id) {
        tableroService.deleteTablero(id);
    }
}