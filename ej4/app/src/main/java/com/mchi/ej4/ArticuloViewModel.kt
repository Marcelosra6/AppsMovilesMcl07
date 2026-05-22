package com.mchi.ej4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mchi.ej4.data.AppDatabase
import com.mchi.ej4.data.Articulo
import kotlinx.coroutines.flow.Flow

class ArticuloViewModel(app: Application) : AndroidViewModel(app) {
    // 1. Quitamos el lateinit y creamos el repositorio al toque
    private val repo: Repository

    // 2. Declaramos la variable de los artículos
    val articulos: LiveData<List<Articulo>>

    // 3. Inicializamos todo en el bloque init
    init {
        val database = AppDatabase.getInstance(app) // Usamos la app como contexto
        repo = Repository(database.articuloDao())
        articulos = repo.listarTodos().asLiveData() // Pasamos el Flow a LiveData
    }
}