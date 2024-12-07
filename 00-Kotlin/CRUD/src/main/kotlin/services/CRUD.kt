package services

import models.Empresa
import models.Empleado
import java.io.File
import java.text.SimpleDateFormat

class CRUD {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val empresasFile = File("00-kotlin/CRUD/src/main/resources/empresas.txt")

    private var empresas: MutableList<Empresa> = mutableListOf()

    init {
        println("Ruta del archivo empresas.txt: ${empresasFile.absolutePath}")

        if (!empresasFile.exists()) {
            val inputStream = javaClass.classLoader.getResourceAsStream("empresas.txt")
            if (inputStream != null) {
                empresasFile.writeText(inputStream.bufferedReader().use { it.readText() })
                println("Archivo empresas.txt copiado desde resources.")
            } else {
                println("Error: No se encontró el archivo empresas.txt en resources.")
            }
        }

        loadFromFile()
    }

    private fun loadFromFile() {
        if (empresasFile.exists()) {
            println("Cargando empresas desde el archivo...")
            empresasFile.readLines().forEach { line ->
                if (line.isNotBlank()) {
                    val parts = line.split("|")
                    if (parts.size >= 6) {
                        try {
                            val empleados = parts.drop(6).chunked(6).mapNotNull { empParts ->
                                try {
                                    Empleado(
                                        id = empParts[0].toInt(),
                                        nombre = empParts[1],
                                        edad = empParts[2].toInt(),
                                        puesto = empParts[3],
                                        fechaContratacion = empParts[4],
                                        salario = empParts[5].toDouble()
                                    )
                                } catch (e: Exception) {
                                    println("Error al cargar empleado: ${e.message}")
                                    null
                                }
                            }.toMutableList()

                            empresas.add(
                                Empresa(
                                    id = parts[0].toInt(),
                                    nombre = parts[1],
                                    ubicacion = parts[2],
                                    fechaCreacion = dateFormat.parse(parts[3]),
                                    numeroEmpleados = parts[4].toInt(),
                                    ingresosAnuales = parts[5].toDouble(),
                                    empleados = empleados
                                )
                            )
                        } catch (e: Exception) {
                            println("Error al cargar empresa: ${e.message}")
                        }
                    } else {
                        println("Línea mal formateada: $line")
                    }
                }
            }
            println("Empresas cargadas: ${empresas.size}")
        }
    }

    private fun saveToFile() {
        try {
            empresasFile.writeText("") // Limpiar el archivo antes de escribir
            empresas.forEach { empresa ->
                val empleadosText = empresa.empleados.joinToString("|") { empleado ->
                    "${empleado.id}|${empleado.nombre}|${empleado.edad}|${empleado.puesto}|${empleado.fechaContratacion}|${empleado.salario}"
                }
                val empresaText = "${empresa.id}|${empresa.nombre}|${empresa.ubicacion}|${dateFormat.format(empresa.fechaCreacion)}|${empresa.numeroEmpleados}|${empresa.ingresosAnuales}|${empleadosText}\n"
                empresasFile.appendText(empresaText)
            }
            println("Datos guardados correctamente en el archivo.")
        } catch (e: Exception) {
            println("Error al guardar en el archivo: ${e.message}")
        }
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
            empresa.numeroEmpleados = empresa.empleados.size
            saveToFile()
        } else {
            println("Empresa no encontrada")
        }
    }

    fun removeEmpleadoFromEmpresa(empresaId: Int, empleadoId: Int) {
        val empresa = empresas.find { it.id == empresaId }
        if (empresa != null) {
            empresa.empleados.removeIf { it.id == empleadoId }
            empresa.numeroEmpleados = empresa.empleados.size
            saveToFile()
        } else {
            println("Empresa no encontrada")
        }
    }

    // Listar empleados de una empresa
    fun readEmpleados(empresaId: Int): List<Empleado>? {
        val empresa = empresas.find { it.id == empresaId }
        return empresa?.empleados
    }

    // Actualizar empleado dentro de una empresa
    fun updateEmpleado(empresaId: Int, empleadoId: Int, updatedEmpleado: Empleado) {
        val empresa = empresas.find { it.id == empresaId }
        if (empresa != null) {
            val index = empresa.empleados.indexOfFirst { it.id == empleadoId }
            if (index != -1) {
                empresa.empleados[index] = updatedEmpleado
                saveToFile()
            } else {
                println("Empleado no encontrado")
            }
        } else {
            println("Empresa no encontrada")
        }
    }
}
