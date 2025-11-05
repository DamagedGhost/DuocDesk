package com.example.demo.service;

// import com.example.demo.model.*;
// import com.example.demo.repository.*;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaService {

    // @Autowired
    // private TarjetaRepository tarjetaRepository;

    // @Autowired
    // private ListaRepository listaRepository; // Necesita validar la lista padre

    // /**
    //  * Regla de Negocio: Crea una tarjeta solo si la lista padre existe.
    //  * La URL del Controller nos debe dar idTablero y idLista.
    //  */
    // @Transactional
    // public Tarjeta crearTarjeta(Tarjeta nuevaTarjeta, int idTablero, int idLista) {
        
    //     // 1. PREPARACIÓN: Crear la clave compuesta de la lista que buscamos
    //     ListaId listaPadreId = new ListaId();
    //     listaPadreId.setIdLista(idLista);
    //     listaPadreId.setTableroId(idTablero);

    //     // 2. REGLA (Validación): Verificar que la lista padre exista
    //     Object listaObj = listaRepository.findById(listaPadreId);
    //     Lista listaPadre;
    //     if (listaObj instanceof java.util.Optional) {
    //         listaPadre = ((java.util.Optional<Lista>) listaObj)
    //                 .orElseThrow(() -> new RuntimeException("Error: La lista " + idLista + " no existe en el tablero " + idTablero));
    //     } else {
    //         if (listaObj == null) {
    //             throw new RuntimeException("Error: La lista " + idLista + " no existe en el tablero " + idTablero);
    //         }
    //         listaPadre = (Lista) listaObj;
    //     }
        
    //     // 3. PREPARACIÓN: Asignar la clave compuesta a la nueva tarjeta
    //     // (Asumiendo que el ID_TARJETA no es autogenerado, si lo fuera, la BD lo asigna)
    //     TarjetaId nuevaTarjetaId = new TarjetaId();
    //     nuevaTarjetaId.setListaId(listaPadreId);
    //     // nuevaTarjetaId.setIdTarjeta(unNuevoId); // <-- Asignar si es necesario
        
    //     // 4. ACCIÓN (Relacionar y Crear):
    //     nuevaTarjeta.setId(nuevaTarjetaId); // Seteamos la clave compuesta
    //     nuevaTarjeta.setLista(listaPadre);  // Seteamos la relación de objeto
        
    //     return tarjetaRepository.save(nuevaTarjeta);
    // }
}