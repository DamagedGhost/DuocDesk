package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.network.external.GitHubRetrofit
import com.example.duocdesk.network.GitRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.duocdesk.network.external.GitHubApiService


class GitHubViewModel(
    private val api: GitHubApiService = GitHubRetrofit.api
) : ViewModel() {

    private val _repos = MutableStateFlow<List<GitRepo>>(emptyList())
    val repos = _repos

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error = _error

    fun loadRepos(rawToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _error.value = null

            try {
                val response = api.getMyRepos("token $rawToken").execute()

                if (response.isSuccessful) {
                    _repos.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error ${response.code()}"
                }

            } catch (e: Exception) {
                _error.value = e.message
            }

            _isLoading.value = false
        }
    }
}

