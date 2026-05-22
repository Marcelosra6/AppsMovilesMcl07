package com.mchi.ej4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mchi.ej4.data.AppDatabase
import com.mchi.ej4.data.Articulo
import com.mchi.ej4.data.ArticuloDao
import com.mchi.ej4.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //crea el objeto para llamar al repository
    private lateinit var repo: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = AppDatabase.getInstance(this).articuloDao()
        repo = Repository(dao)
        // Listeners
        binding.btnRegistrar.setOnClickListener { registrar() }
        binding.btnBuscar.setOnClickListener { buscar() }
        binding.btnModificar.setOnClickListener { modificar() }
        binding.btnEliminar.setOnClickListener { eliminar() }
        binding.btnListar.setOnClickListener { listar() }
    }
    private fun toast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    private fun limpiarCampos() {
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
    }

    private fun registrar() {
        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()
        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Debe llenar todos los campos")
            return
        }
        val articulo = Articulo(
            codigo = codigo.toInt(),
            descripcion = descripcion,
            precio = precio.toDouble()
        )
        lifecycleScope.launch {
            try {
                repo.insertar(articulo)
                limpiarCampos()
                toast("Registro exitoso")
            } catch (e: Exception) {
                toast("Error al registrar: ${e.message}")
            }
        }
    }
    private fun buscar() {
        val codigo = binding.txtCodigo.text.toString()
        if (codigo.isEmpty()) {
            toast("Debe introducir el código del artículo")
            return
        }
        lifecycleScope.launch {
            val articulo = repo.buscarPorCodigo(codigo.toInt())
            if (articulo != null) {
                binding.txtDescripcion.setText(articulo.descripcion)
                binding.txtPrecio.setText(articulo.precio.toString())
            } else {
                toast("No existe el artículo")
            }
        }
    }
    private fun eliminar() {
        val codigo = binding.txtCodigo.text.toString()
        if (codigo.isEmpty()) {
            toast("Debe introducir el código del artículo")
            return
        }
        lifecycleScope.launch {
            val filasEliminadas = repo.eliminarPorCodigo(codigo.toInt())
            limpiarCampos()
            if (filasEliminadas == 1) {
                toast("Artículo eliminado exitosamente")
            } else {
                toast("El artículo no existe")
            }
        }
    }
    private fun modificar() {
        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()
        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Debe llenar todos los campos")
            return
        }
        val articulo = Articulo(
            codigo = codigo.toInt(),
            descripcion = descripcion,
            precio = precio.toDouble()
        )
        lifecycleScope.launch {
            val filasActualizadas = repo.actualizar(articulo)
            if (filasActualizadas == 1) {
                toast("Artículo modificado correctamente")
            } else {
                toast("El artículo no existe")
            }
        }
    }
    private fun listar(){
        val intent = Intent(this, Lista::class.java)
        startActivity(intent)
    }
}