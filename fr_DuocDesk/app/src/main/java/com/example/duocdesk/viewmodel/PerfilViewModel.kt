package com.example.duocdesk.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.internal.RetrofitInstance
import com.google.gson.Gson
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
        val currentUser = UserSession.currentUser ?: return
        var file: File? = null

        try {
            file = uriToFile(context, uri) ?: return

            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("foto", file.name, requestFile)

            val response = RetrofitInstance.api.subirFoto(currentUser._id!!, body)

            if (response.isSuccessful && response.body() != null) {

                val resp = response.body()!!
                val usuarioJson = resp["usuario"]

                if (usuarioJson != null) {
                    val usuarioActualizado = Gson().fromJson(usuarioJson, Usuario::class.java)
                    UserSession.currentUser = usuarioActualizado
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (file != null && file.exists()) file.delete()
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        val tempFile = File.createTempFile("temp_perfil", ".jpg", context.cacheDir)

        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                val bmp = BitmapFactory.decodeStream(input) ?: return null

                val rotated = rotateBitmap(bmp, getRotationFromUri(context, uri))
                val scaled = getResizedBitmap(rotated, 800)

                FileOutputStream(tempFile).use {
                    scaled.compress(Bitmap.CompressFormat.JPEG, 50, it)
                }
            }
            return tempFile
        } catch (e: Exception) {
            return null
        }
    }

    private fun getRotationFromUri(context: Context, uri: Uri): Int {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return 0
        val exif = ExifInterface(inputStream)
        return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        if (degrees == 0) return bitmap
        val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        val ratio = image.width.toFloat() / image.height
        val width: Int
        val height: Int

        if (ratio > 1) {
            width = maxSize
            height = (maxSize / ratio).toInt()
        } else {
            height = maxSize
            width = (maxSize * ratio).toInt()
        }

        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}
