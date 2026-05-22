 package com.mchi.ej1

 import android.content.Context
 import android.os.Build
 import android.os.Bundle
 import android.widget.Toast
 import androidx.appcompat.app.AppCompatActivity
 import com.mchi.ej1.databinding.ActivityMainBinding
 import java.io.File
 import kotlin.toString

 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val FILE_NAME = "textfile.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener { onClickSave() }
        binding.btnLoad.setOnClickListener { onClickLoad() }
    }

     private fun onClickSave() {
         val texto = binding.editText.text.toString()
         if (texto.isEmpty()) {
             Toast.makeText(this, "Ingrese un texto", Toast.LENGTH_SHORT).show()
             return
         }
         try {
             val fichero = getFileExterno(FILE_NAME)
             fichero.writeText(texto)
             val rutaCompleta = fichero.absolutePath
             val toast = Toast.makeText(
                 this, "Archivo guardado en almacenamiento externo!\nRuta: $rutaCompleta",
                 Toast.LENGTH_LONG
             )
                 toast.show()
             //que dure 10 sec
             android.os.Handler(mainLooper).postDelayed({
                 toast.cancel()
                 val toast2 = Toast.makeText(
                     this,
                     "Ruta: $rutaCompleta\n",
                     Toast.LENGTH_LONG
                 )
                 toast2.show()
                 android.os.Handler(mainLooper).postDelayed({
                     toast2.cancel()
                 }, 5000)

             }, 5000)

             binding.editText.setText("")

         } catch (e: Exception) {
             e.printStackTrace()
             Toast.makeText(
                 this, "Error al guardar: ${e.message}",
                 Toast.LENGTH_SHORT
             ).show()
         }
     }

    private fun onClickLoad() {
        try {
            // Obtener el archivo externo
            val fichero = getFileExterno(FILE_NAME)

            // Verificar si el archivo existe
            if (!fichero.exists()) {
                Toast.makeText(this, "El archivo no existe en almacenamiento externo...",
                    Toast.LENGTH_SHORT).show()
                return
            }

            val cont = fichero.readText()
            binding.editText.setText(cont)

            Toast.makeText(
                this, "Archivo cargado desde almacenamiento externo!",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar: ${e.message}",
                Toast.LENGTH_SHORT).show()
        }
    }
    //manejar el archivo externo
     private fun getFileExterno(nombre: String): File {
         // /storage/emulated/0/Android/data/com.mchi.ej1/files/
         val externalDir = getExternalFilesDir(null)

         if (externalDir == null) {//disponibilidad
             throw Exception("Almacenamiento externo no disponible")
         }

         //crear si no existe
         if (!externalDir.exists()) {
             externalDir.mkdirs()
         }

         return File(externalDir, nombre)
     }
}