package com.example.combustiblegt

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity() {

    // Nombre del archivo donde se guardará todo
    private val NOMBRE_ARCHIVO = "precios_combustible.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los componentes del XML
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val rgTipo = findViewById<RadioGroup>(R.id.rgTipo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnVer = findViewById<Button>(R.id.btnVer)
        val tvSalida = findViewById<TextView>(R.id.tvSalida)

        // --- LÓGICA PARA GUARDAR ---
        btnGuardar.setOnClickListener {
            val precio = etPrecio.text.toString()
            val idSeleccionado = rgTipo.checkedRadioButtonId

            if (precio.isNotEmpty() && idSeleccionado != -1) {
                val rbSeleccionado = findViewById<RadioButton>(idSeleccionado)
                val tipo = rbSeleccionado.text.toString()

                // Formato de línea: Gasolina - Precio
                val registro = "$tipo: Q$precio\n"

                try {
                    // Abrimos el archivo en modo APPEND para no borrar lo anterior
                    val fos = openFileOutput(NOMBRE_ARCHIVO, Context.MODE_APPEND)
                    fos.write(registro.toByteArray())
                    fos.close()

                    Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show()
                    etPrecio.text.clear()
                    rgTipo.clearCheck()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // --- LÓGICA PARA LEER EL HISTORIAL ---
        btnVer.setOnClickListener {
            try {
                val fis = openFileInput(NOMBRE_ARCHIVO)
                // Leemos todo el contenido del archivo de un solo golpe
                val contenido = fis.bufferedReader().use { it.readText() }
                tvSalida.text = if (contenido.isEmpty()) "Sin registros" else contenido
                fis.close()
            } catch (e: Exception) {
                tvSalida.text = "No hay registros todavía."
            }
        }
    }
}