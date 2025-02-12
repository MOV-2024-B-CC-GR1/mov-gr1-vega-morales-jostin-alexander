package com.example.ccgr12024b_javm.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private lateinit var etLatitud: EditText
    private lateinit var etLongitud: EditText
    private lateinit var btnGuardar: Button
    private var empresaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_form)

        empresaRepository = EmpresaRepository(this)

        // Referencias a los elementos del layout
        etNombre = findViewById(R.id.etNombreEmpresa)
        etUbicacion = findViewById(R.id.etUbicacionEmpresa)
        etIngresos = findViewById(R.id.etIngresosEmpresa)
        etNumEmpleados = findViewById(R.id.etNumEmpleados)
        etLatitud = findViewById(R.id.etLatitud)
        etLongitud = findViewById(R.id.etLongitud)
        btnGuardar = findViewById(R.id.btnGuardarEmpresa)

        // Obtener el ID de la empresa si estamos en modo edición
        empresaId = intent.getIntExtra("empresaId", -1)
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
                val latitud = etLatitud.text.toString().toDouble()
                val longitud = etLongitud.text.toString().toDouble()

                val empresa = Empresa(
                    id = if (empresaId == -1) 0 else empresaId,
                    nombre = nombre,
                    ubicacion = ubicacion,
                    fechaCreacion = Date(),
                    ingresosAnuales = ingresosAnuales,
                    numeroEmpleados = numeroEmpleados,
                    latitud = latitud,
                    longitud = longitud
                )

                val resultado = if (empresaId == -1) {
                    empresaRepository.insertarEmpresa(empresa) > 0
                } else {
                    empresaRepository.actualizarEmpresa(empresa) > 0
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflamos el menú que contiene el botón para ver el mapa
        menuInflater.inflate(R.menu.menu_empresa_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_ver_mapa -> {
                abrirMapa()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun abrirMapa() {
        if (validarCoordenadas()) {
            val latitud = etLatitud.text.toString().toDouble()
            val longitud = etLongitud.text.toString().toDouble()
            val intent = Intent(this, MapaEmpresaActivity::class.java).apply {
                putExtra("latitud", latitud)
                putExtra("longitud", longitud)
                putExtra("nombre", etNombre.text.toString())
            }
            startActivity(intent)
        }
    }

    private fun cargarEmpresa(empresaId: Int) {
        val empresa = empresaRepository.obtenerPorId(empresaId)
        empresa?.let {
            etNombre.setText(it.nombre)
            etUbicacion.setText(it.ubicacion)
            etIngresos.setText(it.ingresosAnuales.toString())
            etNumEmpleados.setText(it.numeroEmpleados.toString())
            etLatitud.setText(it.latitud.toString())
            etLongitud.setText(it.longitud.toString())
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val ubicacion = etUbicacion.text.toString().trim()
        val ingresos = etIngresos.text.toString().trim()
        val numEmpleados = etNumEmpleados.text.toString().trim()
        val latitud = etLatitud.text.toString().trim()
        val longitud = etLongitud.text.toString().trim()

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
            latitud.isEmpty() -> {
                etLatitud.error = "La latitud es obligatoria"
                false
            }
            longitud.isEmpty() -> {
                etLongitud.error = "La longitud es obligatoria"
                false
            }
            !validarCoordenadas() -> {
                false
            }
            else -> true
        }
    }

    private fun validarCoordenadas(): Boolean {
        try {
            val latitud = etLatitud.text.toString().toDouble()
            val longitud = etLongitud.text.toString().toDouble()

            if (latitud < -90 || latitud > 90) {
                etLatitud.error = "La latitud debe estar entre -90 y 90"
                return false
            }
            if (longitud < -180 || longitud > 180) {
                etLongitud.error = "La longitud debe estar entre -180 y 180"
                return false
            }
            return true
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Las coordenadas deben ser números válidos", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}