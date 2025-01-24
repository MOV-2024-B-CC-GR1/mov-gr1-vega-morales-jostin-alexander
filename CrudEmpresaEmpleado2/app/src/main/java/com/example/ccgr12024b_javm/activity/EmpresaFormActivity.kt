package com.example.ccgr12024b_javm.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_javm.R
import com.example.ccgr12024b_javm.model.Empresa
import com.example.ccgr12024b_javm.repository.EmpresaRepository
import java.util.*

class EmpresaFormActivity : AppCompatActivity() {
    private lateinit var empresaRepository: EmpresaRepository

    private lateinit var etNombre: EditText
    private lateinit var etUbicacion: EditText
    private lateinit var etIngresos: EditText
    private lateinit var etNumEmpleados: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_form)

        empresaRepository = EmpresaRepository(this)

        // Referencias a los elementos del layout
        etNombre = findViewById(R.id.etNombreEmpresa)
        etUbicacion = findViewById(R.id.etUbicacionEmpresa)
        etIngresos = findViewById(R.id.etIngresosEmpresa)
        etNumEmpleados = findViewById(R.id.etNumEmpleados)
        btnGuardar = findViewById(R.id.btnGuardarEmpresa)

        // Obtener el ID de la empresa si estamos en modo edición
        val empresaId = intent.getIntExtra("empresaId", -1)
        if (empresaId != -1) {
            cargarEmpresa(empresaId)
        }

        // Acción del botón Guardar
        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                val nombre = etNombre.text.toString()
                val ubicacion = etUbicacion.text.toString()
                val ingresosAnuales = etIngresos.text.toString().toDouble()
                val numeroEmpleados = etNumEmpleados.text.toString().toInt()

                val empresa = Empresa(
                    id = if (empresaId == -1) 0 else empresaId, // Si es nuevo, ID será 0
                    nombre = nombre,
                    ubicacion = ubicacion,
                    fechaCreacion = Date(),
                    ingresosAnuales = ingresosAnuales,
                    numeroEmpleados = numeroEmpleados
                )

                val resultado = if (empresaId == -1) {
                    empresaRepository.insertarEmpresa(empresa) > 0 // Verifica si la inserción fue exitosa
                } else {
                    empresaRepository.actualizarEmpresa(empresa) > 0 // Verifica si se actualizaron filas
                }

                if (resultado) {
                    Toast.makeText(this, "Empresa guardada con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar la empresa", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarEmpresa(empresaId: Int) {
        val empresa = empresaRepository.obtenerPorId(empresaId)
        empresa?.let {
            etNombre.setText(it.nombre)
            etUbicacion.setText(it.ubicacion)
            etIngresos.setText(it.ingresosAnuales.toString())
            etNumEmpleados.setText(it.numeroEmpleados.toString())
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val ubicacion = etUbicacion.text.toString().trim()
        val ingresos = etIngresos.text.toString().trim()
        val numEmpleados = etNumEmpleados.text.toString().trim()

        return when {
            nombre.isEmpty() -> {
                etNombre.error = "El nombre es obligatorio"
                false
            }
            ubicacion.isEmpty() -> {
                etUbicacion.error = "La ubicación es obligatoria"
                false
            }
            ingresos.isEmpty() -> {
                etIngresos.error = "Los ingresos anuales son obligatorios"
                false
            }
            numEmpleados.isEmpty() -> {
                etNumEmpleados.error = "El número de empleados es obligatorio"
                false
            }
            else -> true
        }
    }
}
