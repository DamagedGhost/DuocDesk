package com.example.duocdesk.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
// Importante: Usar android.media.ExifInterface (Nativo) o androidx.exifinterface.media.ExifInterface
import android.media.ExifInterface
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
import java.io.InputStream

class PerfilViewModel : ViewModel() {

    private val _photoUri = MutableStateFlow<String?>(null)
    val photoUri = _photoUri.asStateFlow()

    fun loadSavedPhoto(context: Context) {
        // Cargar desde UserSession o Preferencias
        val currentUser = UserSession.currentUser
        if (currentUser?.fotoPerfilId != null) {
            // Si hay usuario logueado, la fuente de verdad es la URL remota
            // (La UI se encarga de mostrarla)
            _photoUri.value = null
        } else {
            val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
            _photoUri.value = prefs.getString("photo_uri", null)
        }
    }

    fun updatePhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            // 1. Mostrar visualmente de inmediato (Feedback optimista)
            _photoUri.value = uri.toString()

            // 2. Subir al servidor
            uploadImageToServer(context, uri)
        }
    }

    private suspend fun uploadImageToServer(context: Context, uri: Uri) {
        val currentUser = UserSession.currentUser
        var file: File? = null
        if (currentUser?._id == null) return

        try {
            // A. Convertir y Comprimir
            file = uriToFile(context, uri) ?: return

            // ... Crear Multipart y llamar a API ...
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("foto", file.name, requestFile)

            val response = RetrofitInstance.api.subirFoto(currentUser._id, body)

            if (response.isSuccessful && response.body() != null) {
                val resp = response.body()!!
                UserSession.currentUser = resp.usuario
                println("--> Subida exitosa.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // --- LIMPIEZA: Borrar el archivo temporal del celular ---
            try {
                if (file != null && file.exists()) {
                    file.delete()
                    println("--> Archivo temporal eliminado del celular")
                }
            } catch (e: Exception) { }
        }
    }

    // --- LÓGICA DE COMPRESIÓN Y ROTACIÓN ---
    private fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val tempFile = File.createTempFile("temp_perfil", ".jpg", context.cacheDir)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                // 1. Decodificar a Bitmap
                val originalBitmap = BitmapFactory.decodeStream(inputStream) ?: return null

                // 2. Corregir Rotación (Re-abrir stream para leer EXIF)
                val rotation = getRotationFromUri(context, uri)
                val rotatedBitmap = rotateBitmap(originalBitmap, rotation)

                // 3. Comprimir (Resize + Quality)
                // Redimensionar si es muy grande (ej: máximo 1024px de ancho)
                val scaledBitmap = getResizedBitmap(rotatedBitmap, 800)

                // 4. Guardar en archivo temporal (Calidad 70%)
                FileOutputStream(tempFile).use { outputStream ->
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                }

                // Limpiar memoria
                if (originalBitmap != scaledBitmap) originalBitmap.recycle()
                if (rotatedBitmap != scaledBitmap && rotatedBitmap != originalBitmap) rotatedBitmap.recycle()
            }
            return tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun getRotationFromUri(context: Context, uri: Uri): Int {
        var inputStream: InputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream == null) return 0
            val exif = ExifInterface(inputStream)
            return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: Exception) {
            return 0
        } finally {
            inputStream?.close()
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        if (degrees == 0) return bitmap
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}