package com.example.ccgr12024b_javm.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.ccgr12024b_javm.database.Database
import com.example.ccgr12024b_javm.model.Empleado
import java.text.SimpleDateFormat
import java.util.*

class EmpleadoRepository(context: Context) {
    private val dbHelper = Database(context)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun insertarEmpleado(empleado: Empleado): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_empresa", empleado.idEmpresa)
            put("nombre", empleado.nombre)
            put("edad", empleado.edad)
            put("puesto", empleado.puesto)
            put("fecha_contratacion", dateFormat.format(empleado.fechaContratacion))
            put("salario", empleado.salario)
        }
        return try {
            db.beginTransaction()
            val id = db.insert("Empleado", null, values)
            db.setTransactionSuccessful()
            id
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun eliminarEmpleado(id: Int): Int {
        val db = dbHelper.writableDatabase
        return try {
            db.beginTransaction()
            val result = db.delete("Empleado", "id = ?", arrayOf(id.toString()))
            db.setTransactionSuccessful()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            0
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun obtenerPorEmpresa(idEmpresa: Int): List<Empleado> {
        val empleados = mutableListOf<Empleado>()
        val db = dbHelper.readableDatabase

        try {
            val cursor = db.query(
                "Empleado",
                null,
                "id_empresa = ?",
                arrayOf(idEmpresa.toString()),
                null,
                null,
                null
            )

            cursor.use {
                if (it.moveToFirst()) {
                    do {
                        val empleado = Empleado(
                            id = it.getInt(it.getColumnIndexOrThrow("id")),
                            idEmpresa = idEmpresa,
                            nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                            edad = it.getInt(it.getColumnIndexOrThrow("edad")),
                            puesto = it.getString(it.getColumnIndexOrThrow("puesto")),
                            fechaContratacion = dateFormat.parse(it.getString(it.getColumnIndexOrThrow("fecha_contratacion"))) ?: Date(),
                            salario = it.getDouble(it.getColumnIndexOrThrow("salario"))
                        )
                        empleados.add(empleado)
                    } while (it.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return empleados
    }

    fun obtenerPorId(id: Int): Empleado? {
        val db = dbHelper.readableDatabase
        var empleado: Empleado? = null

        try {
            val cursor = db.query(
                "Empleado",
                null,
                "id = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            cursor.use {
                if (it.moveToFirst()) {
                    empleado = Empleado(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        idEmpresa = it.getInt(it.getColumnIndexOrThrow("id_empresa")),
                        nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                        edad = it.getInt(it.getColumnIndexOrThrow("edad")),
                        puesto = it.getString(it.getColumnIndexOrThrow("puesto")),
                        fechaContratacion = dateFormat.parse(it.getString(it.getColumnIndexOrThrow("fecha_contratacion"))) ?: Date(),
                        salario = it.getDouble(it.getColumnIndexOrThrow("salario"))
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return empleado
    }

    fun actualizarEmpleado(empleado: Empleado): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_empresa", empleado.idEmpresa)
            put("nombre", empleado.nombre)
            put("edad", empleado.edad)
            put("puesto", empleado.puesto)
            put("fecha_contratacion", dateFormat.format(empleado.fechaContratacion))
            put("salario", empleado.salario)
        }

        return try {
            db.beginTransaction()
            val rowsAffected = db.update(
                "Empleado",
                values,
                "id = ?",
                arrayOf(empleado.id.toString())
            )
            db.setTransactionSuccessful()
            rowsAffected // Devuelve el número de filas afectadas
        } catch (e: Exception) {
            e.printStackTrace()
            0 // Devuelve 0 si hay un error
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}
