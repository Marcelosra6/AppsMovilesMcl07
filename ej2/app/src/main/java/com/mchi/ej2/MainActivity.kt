package com.mchi.ej2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mchi.ej2.data.AppDatabase
import com.mchi.ej2.data.Cita
import com.mchi.ej2.data.CitaDao
import com.mchi.ej2.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dao: CitaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = AppDatabase.getInstance(this).citaDao()

        binding.btnRegistrar.setOnClickListener { registrar() }
        binding.btnBuscar.setOnClickListener { buscar() }
        binding.btnModificar.setOnClickListener { modificar() }
        binding.btnEliminar.setOnClickListener { eliminar() }
    }

    private fun toast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        binding.txtId.setText("")
        binding.txtEspecialista.setText("")
        binding.txtDescripcion.setText("")
        binding.txtFecha.setText("")
        binding.txtEstado.setText("")
    }

    private fun registrar() {
        val especialista = binding.txtEspecialista.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val fecha = binding.txtFecha.text.toString()
        val estado = binding.txtEstado.text.toString()

        if (especialista.isEmpty() || descripcion.isEmpty() ||
            fecha.isEmpty() || estado.isEmpty()) {
            toast("Debe llenar todos los campos")
            return
        }

        val cita = Cita(
            especialista = especialista,
            descripcion = descripcion,
            fecha = fecha,
            estado = estado
        )

        lifecycleScope.launch {
            try {
                val id = dao.insertar(cita)
                limpiarCampos()
                toast("Cita registrada con ID: $id")
            } catch (e: Exception) {
                toast("Error al registrar: ${e.message}")
            }
        }
    }

    private fun buscar() {
        val idTexto = binding.txtId.text.toString()
        if (idTexto.isEmpty()) {
            toast("Ingrese el ID de la cita")
            return
        }

        lifecycleScope.launch {
            val cita = dao.buscarPorId(idTexto.toInt())
            if (cita != null) {
                binding.txtEspecialista.setText(cita.especialista)
                binding.txtDescripcion.setText(cita.descripcion)
                binding.txtFecha.setText(cita.fecha)
                binding.txtEstado.setText(cita.estado)
            } else {
                toast("No existe la cita con ese ID")
            }
        }
    }

    private fun modificar() {
        val idTexto = binding.txtId.text.toString()
        val especialista = binding.txtEspecialista.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val fecha = binding.txtFecha.text.toString()
        val estado = binding.txtEstado.text.toString()

        if (idTexto.isEmpty() || especialista.isEmpty() || descripcion.isEmpty() ||
            fecha.isEmpty() || estado.isEmpty()) {
            toast("Debe llenar todos los campos")
            return
        }

        val cita = Cita(
            id = idTexto.toInt(),
            especialista = especialista,
            descripcion = descripcion,
            fecha = fecha,
            estado = estado
        )

        lifecycleScope.launch {
            val filas = dao.actualizar(cita)
            if (filas == 1) {
                toast("Cita modificada correctamente")
            } else {
                toast("No existe la cita con ese ID")
            }
        }
    }

    private fun eliminar() {
        val idTexto = binding.txtId.text.toString()
        if (idTexto.isEmpty()) {
            toast("Ingrese el ID de la cita")
            return
        }

        lifecycleScope.launch {
            val filas = dao.eliminarPorId(idTexto.toInt())
            limpiarCampos()
            if (filas == 1) {
                toast("Cita eliminada exitosamente")
            } else {
                toast("No existe la cita con ese ID")
            }
        }
    }
}