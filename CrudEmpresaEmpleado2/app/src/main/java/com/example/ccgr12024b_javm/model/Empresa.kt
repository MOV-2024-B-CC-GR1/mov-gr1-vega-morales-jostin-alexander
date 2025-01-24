package com.example.ccgr12024b_javm.model

import java.util.Date

data class Empresa(
    val id: Int,                     // Identificador único de la empresa
    var nombre: String,              // Nombre de la empresa
    var ubicacion: String,           // Ubicación de la empresa
    val fechaCreacion: Date,         // Fecha de creación de la empresa
    var numeroEmpleados: Int,        // Número de empleados en la empresa
    var ingresosAnuales: Double      // Ingresos anuales de la empresa
) {
    override fun toString(): String {
        return "$nombre\n$ubicacion\n$$ingresosAnuales\n$numeroEmpleados"
    }
}

