package com.example.duocdesk

import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.internal.ApiService
import com.example.duocdesk.viewmodel.RegisterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import retrofit2.Response

class RegisterViewModelTest {

    private val api = mockk<ApiService>()
    private lateinit var vm: RegisterViewModel
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        vm = RegisterViewModel(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `registro exitoso actualiza registrationSuccess en true`() = runTest {
        val usuario = Usuario(
            nombre = "Marcelo",
            apellido = "Mancilla",
            email = "marce@gmail.com",
            password = "1234",
            carrera = "Ing",
            edad = 20
        )

        // Mock respuesta exitosa
        coEvery { api.registrar(usuario) } returns Response.success(usuario)

        vm.registrar(usuario, validar = false)

        assertTrue(vm.uiState.value.registrationSuccess)
    }

    @Test
    fun `registro fallido agrega error general`() = runTest {
        val usuario = Usuario(
            nombre = "Marcelo",
            apellido = "Mancilla",
            email = "marce@gmail.com",
            password = "1234",
            carrera = "Ing",
            edad = 20
        )

        coEvery { api.registrar(usuario) } returns Response.error(
            500,
            ResponseBody.create(null, "Error interno")
        )

        vm.registrar(usuario, validar = false)

        assertNotNull(vm.uiState.value.errores.general)
    }
}
