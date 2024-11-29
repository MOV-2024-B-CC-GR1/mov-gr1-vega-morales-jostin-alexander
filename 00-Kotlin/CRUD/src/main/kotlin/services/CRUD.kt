package services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Empresa
import models.Empleado
import java.io.File
import java.util.*

class CRUD {
    private val gson = Gson()
    private val empresasFile = File("empresas.json")

    // Lista para manejar las empresas
    private var empresas: MutableList<Empresa> = mutableListOf()

    init {
        loadFromFile()
    }

    private fun loadFromFile() {
        if (empresasFile.exists()) {
            val json = empresasFile.readText()
            val listType = object : TypeToken<MutableList<Empresa>>() {}.type
            empresas = gson.fromJson(json, listType) ?: mutableListOf()
        }
    }

    private fun saveToFile() {
        val json = gson.toJson(empresas)
        empresasFile.writeText(json)
    }

    // CRUD para Empresa
    fun createEmpresa(empresa: Empresa) {
        empresas.add(empresa)
        saveToFile()
    }

    fun readEmpresas(): List<Empresa> = empresas

    fun updateEmpresa(id: Int, updatedEmpresa: Empresa) {
        val index = empresas.indexOfFirst { it.id == id }
        if (index != -1) {
            empresas[index] = updatedEmpresa
            saveToFile()
        } else {
            println("Empresa no encontrada")
        }
    }

    fun deleteEmpresa(id: Int) {
        empresas.removeIf { it.id == id }
        saveToFile()
    }

    // CRUD para Empleados dentro de una Empresa
    fun addEmpleadoToEmpresa(empresaId: Int, empleado: Empleado) {
        val empresa = empresas.find { it.id == empresaId }
        if (empresa != null) {
            empresa.empleados.add(empleado)
            saveToFile()
        } else {
            println("Empresa no encontrada")
        }
    }

    fun removeEmpleadoFromEmpresa(empresaId: Int, empleadoId: Int) {
        val empresa = empresas.find { it.id == empresaId }
        if (empresa != null) {
            empresa.empleados.removeIf { it.id == empleadoId }
            saveToFile()
        } else {
            println("Empresa no encontrada")
        }
    }
}
