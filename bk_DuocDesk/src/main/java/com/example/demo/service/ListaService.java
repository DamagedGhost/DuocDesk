package com.example.demo.service;

import java.util.List;
import java.util.Optional; // Importar Optional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Lista;
import com.example.demo.model.ListaId; // Importar la clase ID
import com.example.demo.repository.ListaRepository;

@Service
public class ListaService {
    
    @Autowired
    private ListaRepository listaRepository;

    public List<Lista> getAllListas() {
        return listaRepository.findAll();
    }

    public Lista saveLista(Lista lista) {
        // Aquí podrías necesitar lógica para asegurar que el ID compuesto esté bien formado
        return listaRepository.save(lista);
    }

    // CORRECCIÓN: El método para buscar por ID debe recibir ListaId
    public Optional<Lista> getListaById(ListaId id) { // Cambiado a Optional<Lista> para mejor manejo de nulos
        return listaRepository.findById(id);
    }

    // CORRECCIÓN: El método para borrar por ID debe recibir ListaId
    public void deleteLista(ListaId id) {
        listaRepository.deleteById(id);
    }
}