package com.mchi.ej4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mchi.ej4.data.AppDatabase
import com.mchi.ej4.data.ArticuloDao
import com.mchi.ej4.databinding.ActivityListaBinding

class Lista : AppCompatActivity() {
    private lateinit var binding: ActivityListaBinding
    private lateinit var viewModel: ArticuloViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ViewModel para como se ve
        viewModel = ViewModelProvider(this)[ArticuloViewModel::class.java]

        //Adapter y la lista para el manejo de los registros
        val adapter = ArticuloAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //el adapter recibe la lista
        viewModel.articulos.observe(this) { lista -> adapter.submitList(lista) }
    }
}