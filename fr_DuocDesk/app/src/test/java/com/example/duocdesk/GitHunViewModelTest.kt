package com.example.duocdesk

import com.example.duocdesk.network.GitRepo
import com.example.duocdesk.network.external.GitHubApiService
import com.example.duocdesk.viewmodel.GitHubViewModel
import io.mockk.every
import io.mockk.mockk
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class GitHubViewModelTest {

    private val api = mockk<GitHubApiService>()
    private val vm = GitHubViewModel(api)   // ✔ ahora usa el MOCK

    @Test
    fun `cuando API devuelve repos, el ViewModel actualiza lista`() {
        val fakeRepos = listOf(GitRepo("Repo1", "desc", "Kotlin", 3))

        val mockCall = mockk<Call<List<GitRepo>>>()
        every { mockCall.execute() } returns Response.success(fakeRepos)
        every { api.getMyRepos(any()) } returns mockCall

        vm.loadRepos("token123")

        Thread.sleep(300)

        assertEquals(1, vm.repos.value.size)
    }

    @Test
    fun `cuando API falla, error no es null`() {
        val mockCall = mockk<Call<List<GitRepo>>>()
        every { mockCall.execute() } returns Response.error(
            500,
            ResponseBody.create("application/json".toMediaType(), "Error")
        )
        every { api.getMyRepos(any()) } returns mockCall

        vm.loadRepos("token123")

        Thread.sleep(300)

        assertNotNull(vm.error.value)
    }
}
