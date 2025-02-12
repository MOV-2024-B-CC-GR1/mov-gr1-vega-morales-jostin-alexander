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
import com.example.ccgr12024b_javm.model.Empresa
import com.example.ccgr12024b_javm.repository.EmpresaRepository

class EmpresaListActivity : AppCompatActivity() {
    private lateinit var empresaRepository: EmpresaRepository
    private lateinit var lvEmpresas: ListView
    private lateinit var btnAgregarEmpresa: Button
    private var empresas = mutableListOf<Empresa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_list)

        empresaRepository = EmpresaRepository(this)
        lvEmpresas = findViewById(R.id.lvEmpresas)
        btnAgregarEmpresa = findViewById(R.id.btnAgregarEmpresa)

        // Cargar lista de empresas
        loadEmpresas()

        // Configurar clic en el botón de agregar empresa
        btnAgregarEmpresa.setOnClickListener {
            val intent = Intent(this, EmpresaFormActivity::class.java)
            startActivity(intent) // Abre la actividad para agregar una nueva empresa
        }

        // Configurar clic en un elemento de la lista
        lvEmpresas.setOnItemClickListener { _, _, position, _ ->
            val empresaSeleccionada = empresas[position]
            mostrarOpciones(empresaSeleccionada)
        }
    }

    override fun onResume() {
        super.onResume()
        loadEmpresas() // Recargar la lista al volver a esta actividad
    }

    private fun loadEmpresas() {
        empresas.clear()
        empresas.addAll(empresaRepository.obtenerTodas())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Usamos el layout simple predeterminado
            empresas // `toString()` se usará automáticamente para cada elemento
        )
        lvEmpresas.adapter = adapter
    }

    private fun mostrarOpciones(empresa: Empresa) {
        // Agregamos "Ver en Mapa" a las opciones
        val opciones = arrayOf("Actualizar", "Eliminar", "Ver Empleados", "Ver Mapa")
        AlertDialog.Builder(this)
            .setTitle("Opciones para ${empresa.nombre}")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> { // Actualizar
                        val intent = Intent(this, EmpresaFormActivity::class.java).apply {
                            putExtra("empresaId", empresa.id)
                        }
                        startActivity(intent)
                    }
                    1 -> { // Eliminar
                        confirmarEliminacion(empresa)
                    }
                    2 -> { // Ver Empleados
                        val intent = Intent(this, EmpleadoListActivity::class.java).apply {
                            putExtra("empresaId", empresa.id)
                        }
                        startActivity(intent)
                    }
                    3 -> { // Ver en Mapa
                        abrirMapa(empresa)
                    }
                }
            }
            .show()
    }

    private fun abrirMapa(empresa: Empresa) {
        val intent = Intent(this, MapaEmpresaActivity::class.java).apply {
            putExtra("latitud", empresa.latitud)
            putExtra("longitud", empresa.longitud)
            putExtra("nombre", empresa.nombre)
        }
        startActivity(intent)
    }

    private fun confirmarEliminacion(empresa: Empresa) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Empresa")
            .setMessage("¿Estás seguro de que deseas eliminar la empresa '${empresa.nombre}'?")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = empresaRepository.eliminarEmpresa(empresa.id)
                if (resultado > 0) {
                    Toast.makeText(this, "Empresa eliminada", Toast.LENGTH_SHORT).show()
                    loadEmpresas() // Recargar la lista
                } else {
                    Toast.makeText(this, "Error al eliminar la empresa", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}