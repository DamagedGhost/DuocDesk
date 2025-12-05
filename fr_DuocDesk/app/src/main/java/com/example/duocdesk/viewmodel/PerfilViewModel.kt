package com.example.duocdesk.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.network.internal.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import androidx.core.content.edit

class PerfilViewModel : ViewModel() {

    private val _photoUri = MutableStateFlow<String?>(null)
    val photoUri = _photoUri.asStateFlow()

    // Carga la foto guardada localmente (cache visual inmediata)
    fun loadSavedPhoto(context: Context) {
        val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
        _photoUri.value = prefs.getString("photo_uri", null)
    }

    // Proceso completo: Guardar local + Subir al servidor
    fun updatePhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            // 1. Guardar preferencia local (para que se vea rápido)
            val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
            prefs.edit { putString("photo_uri", uri.toString()) }
            _photoUri.value = uri.toString()

            // 2. Subir al servidor
            uploadImageToServer(context, uri)
        }
    }

    private suspend fun uploadImageToServer(context: Context, uri: Uri) {
        val currentUser = UserSession.currentUser
        if (currentUser?._id == null) return // No podemos subir si no hay ID de usuario

        try {
            // A. Convertir URI a Archivo Temporal
            val file = uriToFile(context, uri) ?: return

            // B. Preparar el Request Multipart
            // "foto" es el nombre del campo que pusimos en upload.single("foto") en Node.js
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("foto", file.name, requestFile)

            // C. Llamar a la API
            val response = RetrofitInstance.api.subirFoto(currentUser._id, body)

            if (response.isSuccessful) {
                println("Foto subida con éxito! ID: ${response.body()?.get("fotoId")}")
                // Opcional: Podrías actualizar UserSession.currentUser aquí si quisieras
            } else {
                println("Error al subir foto: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Función auxiliar para sacar el archivo real desde la URI de la galería/cámara
    private fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val tempFile = File.createTempFile("temp_perfil", ".jpg", context.cacheDir)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}