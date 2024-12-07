package main

import models.Empresa
import models.Empleado
import services.CRUD
import java.util.*

fun main() {
    val crud = CRUD()
    var empresas = crud.readEmpresas().toMutableList()

    while (true) {
        println("=== Menú Principal ===")
        println("1. Empresa")
        println("2. Empleado")
        println("3. Salir")
        print("Selecciona una opción: ")

        when (readln().toIntOrNull()) {
            1 -> empresaMenu(crud, empresas)
            2 -> empleadoMenu(crud, empresas)
            3 -> {
                println("Saliendo del programa...")
                return
            }
            else -> println("Opción inválida. Intente nuevamente.")
        }
    }
}

fun empresaMenu(crud: CRUD, empresas: MutableList<Empresa>) {
    while (true) {
        println("\n=== Menú Empresa ===")
        println("1. Crear Empresa")
        println("2. Listar Empresas")
        println("3. Actualizar Empresa")
        println("4. Eliminar Empresa")
        println("5. Volver al Menú Principal")
        print("Selecciona una opción: ")

        when (readln().toIntOrNull()) {
            1 -> {
                println("Ingrese el nombre de la empresa:")
                val nombre = readln()
                println("Ingrese la ubicación de la empresa:")
                val ubicacion = readln()
                println("Ingrese los ingresos anuales:")
                val ingresos = readln().toDoubleOrNull() ?: 0.0

                val empresa = Empresa(
                    id = (empresas.maxOfOrNull { it.id } ?: 0) + 1,
                    nombre = nombre,
                    ubicacion = ubicacion,
                    fechaCreacion = Date(),
                    numeroEmpleados = 0,
                    ingresosAnuales = ingresos
                )
                crud.createEmpresa(empresa)
                empresas.clear()
                empresas.addAll(crud.readEmpresas())
                println("Empresa creada exitosamente.")
            }

            2 -> {
                println("\n=== Lista de Empresas ===")
                if (empresas.isEmpty()) {
                    println("No hay empresas registradas.")
                } else {
                    empresas.forEach { println(it) }
                }
            }

            3 -> {
                println("Ingrese el ID de la empresa a actualizar:")
                val id = readln().toIntOrNull()
                val empresa = empresas.find { it.id == id }
                if (empresa != null) {
                    println("Ingrese el nuevo nombre de la empresa:")
                    empresa.nombre = readln()
                    println("Ingrese la nueva ubicación de la empresa:")
                    empresa.ubicacion = readln()
                    println("Ingrese los nuevos ingresos anuales:")
                    empresa.ingresosAnuales = readln().toDoubleOrNull() ?: empresa.ingresosAnuales
                    crud.updateEmpresa(id!!, empresa)
                    empresas.clear()
                    empresas.addAll(crud.readEmpresas())
                    println("Empresa actualizada exitosamente.")
                } else {
                    println("Empresa no encontrada.")
                }
            }

            4 -> {
                println("Ingrese el ID de la empresa a eliminar:")
                val id = readln().toIntOrNull()
                if (id != null && empresas.any { it.id == id }) {
                    crud.deleteEmpresa(id)
                    empresas.clear()
                    empresas.addAll(crud.readEmpresas())
                    println("Empresa eliminada exitosamente.")
                } else {
                    println("ID inválido o empresa no encontrada.")
                }
            }

            5 -> return
            else -> println("Opción inválida. Intente nuevamente.")
        }
    }
}

fun empleadoMenu(crud: CRUD, empresas: MutableList<Empresa>) {
    while (true) {
        println("\n=== Menú Empleado ===")
        println("1. Añadir Empleado a Empresa")
        println("2. Eliminar Empleado de Empresa")
        println("3. Listar Empleados de una Empresa")
        println("4. Actualizar Empleado de una Empresa")
        println("5. Volver al Menú Principal")
        print("Selecciona una opción: ")

        when (readln().toIntOrNull()) {
            1 -> {
                println("Ingrese el ID de la empresa:")
                val empresaId = readln().toIntOrNull()
                val empresa = empresas.find { it.id == empresaId }
                if (empresa != null) {
                    println("Ingrese el nombre del empleado:")
                    val nombre = readln()
                    println("Ingrese la edad del empleado:")
                    val edad = readln().toIntOrNull() ?: 0
                    println("Ingrese el puesto del empleado:")
                    val puesto = readln()
                    println("Ingrese la fecha de contratación (yyyy-MM-dd):")
                    val fecha = readln()
                    println("Ingrese el salario del empleado:")
                    val salario = readln().toDoubleOrNull() ?: 0.0

                    val empleado = Empleado(
                        id = (empresa.empleados.maxOfOrNull { it.id } ?: 0) + 1,
                        nombre = nombre,
                        edad = edad,
                        puesto = puesto,
                        fechaContratacion = fecha,
                        salario = salario
                    )
                    crud.addEmpleadoToEmpresa(empresaId!!, empleado)
                    empresas.clear()
                    empresas.addAll(crud.readEmpresas())
                    println("Empleado añadido exitosamente.")
                } else {
                    println("Empresa no encontrada.")
                }
            }

            2 -> {
                println("Ingrese el ID de la empresa:")
                val empresaId = readln().toIntOrNull()
                val empresa = empresas.find { it.id == empresaId }
                if (empresa != null) {
                    println("Ingrese el ID del empleado a eliminar:")
                    val empleadoId = readln().toIntOrNull()
                    if (empleadoId != null && empresa.empleados.any { it.id == empleadoId }) {
                        crud.removeEmpleadoFromEmpresa(empresaId!!, empleadoId)
                        empresas.clear()
                        empresas.addAll(crud.readEmpresas())
                        println("Empleado eliminado exitosamente.")
                    } else {
                        println("Empleado no encontrado en esta empresa.")
                    }
                } else {
                    println("Empresa no encontrada.")
                }
            }

            3 -> {
                println("Ingrese el ID de la empresa:")
                val empresaId = readln().toIntOrNull()
                val empleados = crud.readEmpleados(empresaId ?: -1)
                if (empleados != null && empleados.isNotEmpty()) {
                    println("\n=== Empleados de la Empresa ID: $empresaId ===")
                    empleados.forEach { println(it) }
                } else {
                    println("No se encontraron empleados o la empresa no existe.")
                }
            }

            4 -> {
                println("Ingrese el ID de la empresa:")
                val empresaId = readln().toIntOrNull()
                val empresa = empresas.find { it.id == empresaId }
                if (empresa != null) {
                    println("Ingrese el ID del empleado a actualizar:")
                    val empleadoId = readln().toIntOrNull()
                    val empleado = empresa.empleados.find { it.id == empleadoId }
                    if (empleado != null) {
                        println("Ingrese el nuevo nombre del empleado:")
                        empleado.nombre = readln()
                        println("Ingrese la nueva edad del empleado:")
                        empleado.edad = readln().toIntOrNull() ?: empleado.edad
                        println("Ingrese el nuevo puesto del empleado:")
                        empleado.puesto = readln()
                        println("Ingrese la nueva fecha de contratación (yyyy-MM-dd):")
                        empleado.fechaContratacion = readln()
                        println("Ingrese el nuevo salario del empleado:")
                        empleado.salario = readln().toDoubleOrNull() ?: empleado.salario
                        crud.updateEmpleado(empresaId!!, empleadoId!!, empleado)
                        empresas.clear()
                        empresas.addAll(crud.readEmpresas())
                        println("Empleado actualizado exitosamente.")
                    } else {
                        println("Empleado no encontrado.")
                    }
                } else {
                    println("Empresa no encontrada.")
                }
            }

            5 -> return
            else -> println("Opción inválida. Intente nuevamente.")
        }
    }
}
