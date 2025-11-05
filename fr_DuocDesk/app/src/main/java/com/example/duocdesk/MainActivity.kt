package com.example.duocdesk


import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.RecyclerView
import com.example.duocdesk.network.UsuarioAdapter
import com.example.duocdesk.ui.theme.MVVMMaterialAppTheme
import com.example.duocdesk.view.WelcomeScreen
import com.example.duocdesk.viewmodel.UsuarioViewModel
import com.example.duocdesk.viewmodel.WelcomeViewModel

class MainActivity : ComponentActivity() {
    // Inyecta el ViewModel usando el delegate 'viewModels()'
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private lateinit var usuarioAdapter: UsuarioAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewUsuarios)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.textViewError)

        // Configurar RecyclerView
        setupRecyclerView()

        // Observar los LiveData del ViewModel
        observeViewModel()
    }

    private fun setupRecyclerView() {
        // Inicializa el adaptador con una lista vacía
        usuarioAdapter = UsuarioAdapter(emptyList())
        recyclerView.adapter = usuarioAdapter
        // No es necesario definir el LayoutManager aquí si ya lo hiciste en XML
        // recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        // Observa cambios en la lista de usuarios
        usuarioViewModel.usuarios.observe(this) { usuarios ->
            // Actualiza el adaptador cuando la lista cambia
            usuarioAdapter.updateUsuarios(usuarios ?: emptyList()) // Maneja lista nula
        }

        // Observa cambios en el estado de carga
        usuarioViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observa si hay errores
        usuarioViewModel.error.observe(this) { errorMsg ->
            if (errorMsg != null) {
                errorTextView.text = errorMsg
                errorTextView.visibility = View.VISIBLE
            } else {
                errorTextView.visibility = View.GONE
            }
        }
    }
}