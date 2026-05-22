package com.mchi.ej3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.mchi.ej3.data.AppDatabase

//View Model permite adaptar la tabla a la rotación de pantalla
class ArticuloViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).articuloDao()
    val articulos = dao.listarTodos().asLiveData()
}