package com.example.demo.dto;

import lombok.Data;
import com.example.demo.model.Usuario;

@Data
public class UsuarioDTO {
    // Solo los campos que Android necesita
    private int idUsuario;
    private String correo;
    private String nombre;
    private String apellido;
    private String carrera;

    // Un constructor que "convierte" un Usuario (Entidad) a un UsuarioDTO
    public UsuarioDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.correo = usuario.getCorreo();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.carrera = usuario.getCarrera();
    }
    
}
