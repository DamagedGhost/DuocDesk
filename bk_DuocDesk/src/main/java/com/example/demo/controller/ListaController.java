package com.example.demo.controller;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Lista;
import com.example.demo.service.ListaService;

@RestController
@RequestMapping("/api/listas")
public class ListaController {
    
    @Autowired
    private ListaService listaService;

    @GetMapping("/listar")
    public List<Lista> getAllListas() {
        return listaService.getAllListas();
    }

    @PostMapping("/crear")
    public Lista createLista(@RequestBody Lista nuevaLista) {
        return listaService.saveLista(nuevaLista);
    }

    // @GetMapping("/{id}")
    // public Lista getListaById(@PathVariable Integer id) {
    //     return listaService.getListaById(id);
    // }


    @GetMapping("/prueba")
    public String prueba() {
        return "prueba desde ListaController\n" + Date.valueOf(java.time.LocalDate.now()).toString();
    }

    
}
    