package com.example.ccgr12024b_javm.model

import java.util.Date

data class Empleado(
    val id: Int,                     // Identificador único del empleado
    var idEmpresa: Int,              // ID de la empresa asociada (clave foránea)
    var nombre: String,              // Nombre del empleado
    var edad: Int,                   // Edad del empleado
    var puesto: String,              // Puesto o cargo del empleado
    val fechaContratacion: Date,     // Fecha de contratación del empleado
    var salario: Double              // Salario del empleado
) {
    override fun toString(): String {
        return "$nombre\n$puesto"
    }
}
