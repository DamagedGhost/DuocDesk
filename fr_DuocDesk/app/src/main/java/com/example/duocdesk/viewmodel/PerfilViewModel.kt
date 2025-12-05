package com.example.duocdesk.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface // Necesario para rotación
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
import androidx.core.graphics.scale

class PerfilViewModel : ViewModel() {

    private val _photoUri = MutableStateFlow<String?>(null)
    val photoUri = _photoUri.asStateFlow()

    fun loadSavedPhoto(context: Context) {
        val currentUser = UserSession.currentUser
        if (currentUser?.fotoPerfilId != null) {
            _photoUri.value = null
        } else {
            val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
            _photoUri.value = prefs.getString("photo_uri", null)
        }
    }

    fun updatePhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            _photoUri.value = uri.toString()
            uploadImageToServer(context, uri)
        }
    }

    private suspend fun uploadImageToServer(context: Context, uri: Uri) {
        val currentUser = UserSession.currentUser
        if (currentUser?._id == null) return

        var file: File? = null

        try {
            // 1. PROCESAR (Rotar y Comprimir)
            file = uriToFile(context, uri) ?: return

            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("foto", file.name, requestFile)

            // 2. SUBIR (Backend con Librería)
            val response = RetrofitInstance.api.subirFoto(currentUser._id, body)

            if (response.isSuccessful && response.body() != null) {
                // 3. ACTUALIZAR SESIÓN
                val nuevoUsuario = response.body()!!.usuario
                UserSession.currentUser = nuevoUsuario
                println("--> Éxito: Foto subida y comprimida.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // 4. LIMPIEZA
            try { file?.delete() } catch (e: Exception) {}
        }
    }

    // --- UTILS DE COMPRESIÓN ---
    private fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val tempFile = File.createTempFile("temp_perfil", ".jpg", context.cacheDir)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val originalBitmap = BitmapFactory.decodeStream(inputStream) ?: return null

                // Rotar
                val rotation = getRotationFromUri(context, uri)
                val rotatedBitmap = rotateBitmap(originalBitmap, rotation)

                // Compactar a 600px
                val scaledBitmap = getResizedBitmap(rotatedBitmap, 600)

                // Calidad 50%
                FileOutputStream(tempFile).use { outputStream ->
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                }
            }
            return tempFile
        } catch (e: Exception) { return null }
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
        } catch (e: Exception) { return 0 } finally { inputStream?.close() }
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
        return image.scale(width, height)
    }
}