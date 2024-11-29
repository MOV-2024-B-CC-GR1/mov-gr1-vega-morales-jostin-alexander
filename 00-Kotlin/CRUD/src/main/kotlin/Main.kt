package main

import models.Empresa
import models.Empleado
import services.CRUD
import java.util.*

fun main() {
    val crud = CRUD()
    val empresas = crud.readEmpresas().toMutableList()

    while (true) {
        println("=== Menú CRUD ===")
        println("1. Crear Empresa")
        println("2. Listar Empresas")
        println("3. Actualizar Empresa")
        println("4. Eliminar Empresa")
        println("5. Añadir Empleado a Empresa")
        println("6. Eliminar Empleado de Empresa")
        println("7. Salir")
        print("Selecciona una opción: ")

        when (readln().toInt()) {
            1 -> {
                println("Ingrese el nombre de la empresa:")
                val nombre = readln()
                println("Ingrese la ubicación de la empresa:")
                val ubicacion = readln()
                println("Ingrese los ingresos anuales:")
                val ingresos = readln().toDouble()

                val empresa = Empresa(
                    id = empresas.size + 1,
                    nombre = nombre,
                    ubicacion = ubicacion,
                    fechaCreacion = Date(),
                    numeroEmpleados = 0,
                    ingresosAnuales = ingresos
                )
                crud.createEmpresa(empresa)
                println("Empresa creada exitosamente.")
            }

            2 -> {
                println("=== Lista de Empresas ===")
                empresas.forEach { println(it) }
            }

            3 -> {
                println("Ingrese el ID de la empresa a actualizar:")
                val id = readln().toInt()
                val empresa = empresas.find { it.id == id }
                if (empresa != null) {
                    println("Ingrese el nuevo nombre de la empresa:")
                    empresa.nombre = readln()
                    crud.updateEmpresa(id, empresa)
                    println("Empresa actualizada exitosamente.")
                } else {
                    println("Empresa no encontrada.")
                }
            }

            4 -> {
                println("Ingrese el ID de la empresa a eliminar:")
                val id = readln().toInt()
                crud.deleteEmpresa(id)
                println("Empresa eliminada exitosamente.")
            }

            5 -> {
                println("Ingrese el ID de la empresa:")
                val empresaId = readln().toInt()
                println("Ingrese el nombre del empleado:")
                val nombre = readln()
                println("Ingrese la edad del empleado:")
                val edad = readln().toInt()
                println("Ingrese el puesto del empleado:")
                val puesto = readln()
                println("Ingrese la fecha de contratación:")
                val fecha = readln()
                println("Ingrese el salario del empleado:")
                val salario = readln().toDouble()

                val empleado = Empleado(
                    id = empresas.sumOf { it.empleados.size } + 1,
                    nombre = nombre,
                    edad = edad,
                    puesto = puesto,
                    fechaContratacion = fecha,
                    salario = salario
                )
                crud.addEmpleadoToEmpresa(empresaId, empleado)
                println("Empleado añadido exitosamente.")
            }

            6 -> {
                println("Ingrese el ID de la empresa:")
                val empresaId = readln().toInt()
                println("Ingrese el ID del empleado a eliminar:")
                val empleadoId = readln().toInt()
                crud.removeEmpleadoFromEmpresa(empresaId, empleadoId)
                println("Empleado eliminado exitosamente.")
            }

            7 -> {
                println("Saliendo del programa...")
                return
            }

            else -> println("Opción inválida.")
        }
    }
}
