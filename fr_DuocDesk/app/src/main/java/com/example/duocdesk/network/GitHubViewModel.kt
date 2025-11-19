package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.network.GitHubRetrofit
import com.example.duocdesk.network.GitRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GitHubViewModel : ViewModel() {

    private val _repos = MutableStateFlow<List<GitRepo>>(emptyList())
    val repos: StateFlow<List<GitRepo>> = _repos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadRepos(rawToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _error.value = null

            try {
                // 👇 Aquí armamos correctamente el header para PAT clásico
                val authHeader = "token $rawToken"

                val response = GitHubRetrofit.api.getMyRepos(authHeader).execute()

                if (response.isSuccessful) {
                    _repos.value = response.body() ?: emptyList()
                } else {
                    _error.value =
                        "Error ${response.code()}: ${response.message()}"
                    _repos.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
                _repos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
