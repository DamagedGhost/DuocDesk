package com.example.duocdesk.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.duocdesk.model.Usuario

@Dao
interface UsuarioDao {
    // Inserta un nuevo usuario. Si falla (ej. email duplicado), abortará.
    @Insert
    suspend fun insertarUsuario(usuario: Usuario)

    // Busca un usuario por email y contraseña.
    // Si lo encuentra, devuelve el objeto Usuario. Si no, devuelve null.
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun validarLogin(correo: String, contrasena: String): Usuario?
}