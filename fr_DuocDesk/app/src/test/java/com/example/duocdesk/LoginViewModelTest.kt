package com.example.duocdesk

import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.internal.ApiService
import com.example.duocdesk.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.After
import retrofit2.Response

class LoginViewModelTest {

    private val api = mockk<ApiService>()
    private val vm = LoginViewModel(api)
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login exitoso guarda usuario y loginSuccess en true`() = runTest {
        val usuario = Usuario(email = "test@gmail.com")

        coEvery { api.login(any()) } returns Response.success(usuario)

        vm.onEmailChange("test@gmail.com")
        vm.onPasswordChange("1234")
        vm.onLoginClick()

        assertTrue(vm.uiState.value.loginSuccess)
        assertNotNull(vm.uiState.value.usuario)
    }

    @Test
    fun `login fallido muestra error general`() = runTest {
        coEvery { api.login(any()) } returns Response.error(
            401,
            ResponseBody.create(null, "Unauthorized")
        )

        vm.onEmailChange("a@a.cl")
        vm.onPasswordChange("1234")
        vm.onLoginClick()

        assertNotNull(vm.uiState.value.errores.general)
    }
}
