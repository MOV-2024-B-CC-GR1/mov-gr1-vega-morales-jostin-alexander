package models

import java.util.Date

data class Empresa(
    val id: Int,
    var nombre: String,
    var ubicacion: String,
    var fechaCreacion: Date,
    var numeroEmpleados: Int,
    var ingresosAnuales: Double,
    val empleados: MutableList<Empleado> = mutableListOf()
)
