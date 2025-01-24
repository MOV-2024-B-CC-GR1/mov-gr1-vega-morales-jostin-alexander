package com.example.ccgr12024b_javm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_javm.R
import com.example.ccgr12024b_javm.model.Empleado
import com.example.ccgr12024b_javm.repository.EmpleadoRepository

class EmpleadoListActivity : AppCompatActivity() {
    private lateinit var empleadoRepository: EmpleadoRepository
    private lateinit var lvEmpleados: ListView
    private lateinit var btnAgregarEmpleado: Button
    private var empleados = mutableListOf<Empleado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_list)

        empleadoRepository = EmpleadoRepository(this)
        lvEmpleados = findViewById(R.id.lvEmpleados)
        btnAgregarEmpleado = findViewById(R.id.btnAgregarEmpleado)

        val empresaId = intent.getIntExtra("empresaId", -1)
        loadEmpleados(empresaId)

        // Configurar clic en el botón de agregar empleado
        btnAgregarEmpleado.setOnClickListener {
            val intent = Intent(this, EmpleadoFormActivity::class.java).apply {
                putExtra("empresaId", empresaId) // Pasa el ID de la empresa asociada
            }
            startActivity(intent) // Abre la actividad para agregar un nuevo empleado
        }

        lvEmpleados.setOnItemClickListener { _, _, position, _ ->
            val empleadoSeleccionado = empleados[position]
            mostrarOpciones(empleadoSeleccionado, empresaId)
        }
    }

    override fun onResume() {
        super.onResume()
        val empresaId = intent.getIntExtra("empresaId", -1)
        loadEmpleados(empresaId)
    }

    private fun loadEmpleados(empresaId: Int) {
        empleados.clear()
        empleados.addAll(empleadoRepository.obtenerPorEmpresa(empresaId))

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Usamos el layout simple predeterminado
            empleados // `toString()` se usará automáticamente para cada elemento
        )
        lvEmpleados.adapter = adapter
    }


    private fun mostrarOpciones(empleado: Empleado, empresaId: Int) {
        val opciones = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Opciones para ${empleado.nombre}")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> { // Actualizar
                        val intent = Intent(this, EmpleadoFormActivity::class.java).apply {
                            putExtra("empleadoId", empleado.id)
                            putExtra("empresaId", empresaId)
                        }
                        startActivity(intent)
                    }
                    1 -> { // Eliminar
                        confirmarEliminacion(empleado, empresaId)
                    }
                }
            }
            .show()
    }

    private fun confirmarEliminacion(empleado: Empleado, empresaId: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Empleado")
            .setMessage("¿Estás seguro de que deseas eliminar al empleado '${empleado.nombre}'?")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = empleadoRepository.eliminarEmpleado(empleado.id)
                if (resultado > 0) {
                    Toast.makeText(this, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                    loadEmpleados(empresaId)
                } else {
                    Toast.makeText(this, "Error al eliminar el empleado", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
