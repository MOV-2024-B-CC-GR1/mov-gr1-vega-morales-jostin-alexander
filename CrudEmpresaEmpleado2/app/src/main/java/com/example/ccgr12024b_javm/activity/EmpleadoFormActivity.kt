package com.example.ccgr12024b_javm.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_javm.R
import com.example.ccgr12024b_javm.model.Empleado
import com.example.ccgr12024b_javm.repository.EmpleadoRepository
import java.util.*

class EmpleadoFormActivity : AppCompatActivity() {
    private lateinit var empleadoRepository: EmpleadoRepository

    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etPuesto: EditText
    private lateinit var etSalario: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_form)

        empleadoRepository = EmpleadoRepository(this)

        etNombre = findViewById(R.id.etNombreEmpleado)
        etEdad = findViewById(R.id.etEdadEmpleado)
        etPuesto = findViewById(R.id.etPuestoEmpleado)
        etSalario = findViewById(R.id.etSalarioEmpleado)
        btnGuardar = findViewById(R.id.btnGuardarEmpleado)

        val empresaId = intent.getIntExtra("empresaId", -1)
        val empleadoId = intent.getIntExtra("empleadoId", -1)
        if (empleadoId != -1) {
            cargarEmpleado(empleadoId)
        }

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                val nombre = etNombre.text.toString()
                val edad = etEdad.text.toString().toInt()
                val puesto = etPuesto.text.toString()
                val salario = etSalario.text.toString().toDouble()

                val empleado = Empleado(
                    id = if (empleadoId == -1) 0 else empleadoId,
                    idEmpresa = empresaId,
                    nombre = nombre,
                    edad = edad,
                    puesto = puesto,
                    fechaContratacion = Date(),
                    salario = salario
                )

                val resultado = if (empleadoId == -1) {
                    empleadoRepository.insertarEmpleado(empleado) > 0
                } else {
                    empleadoRepository.actualizarEmpleado(empleado) > 0
                }

                if (resultado) {
                    Toast.makeText(this, "Empleado guardado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar el empleado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarEmpleado(empleadoId: Int) {
        val empleado = empleadoRepository.obtenerPorId(empleadoId)
        empleado?.let {
            etNombre.setText(it.nombre)
            etEdad.setText(it.edad.toString())
            etPuesto.setText(it.puesto)
            etSalario.setText(it.salario.toString())
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val edad = etEdad.text.toString().trim()
        val puesto = etPuesto.text.toString().trim()
        val salario = etSalario.text.toString().trim()

        return when {
            nombre.isEmpty() -> {
                etNombre.error = "El nombre es obligatorio"
                false
            }
            edad.isEmpty() -> {
                etEdad.error = "La edad es obligatoria"
                false
            }
            puesto.isEmpty() -> {
                etPuesto.error = "El puesto es obligatorio"
                false
            }
            salario.isEmpty() -> {
                etSalario.error = "El salario es obligatorio"
                false
            }
            else -> true
        }
    }
}
