package models

data class Empleado(
    val id: Int,
    var nombre: String,
    var edad: Int,
    var puesto: String,
    var fechaContratacion: String,
    var salario: Double
)
