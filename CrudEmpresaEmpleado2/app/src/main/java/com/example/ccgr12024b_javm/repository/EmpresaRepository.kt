package com.example.ccgr12024b_javm.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.ccgr12024b_javm.database.Database
import com.example.ccgr12024b_javm.model.Empresa
import java.text.SimpleDateFormat
import java.util.*

class EmpresaRepository(context: Context) {
    private val dbHelper = Database(context)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun insertarEmpresa(empresa: Empresa): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", empresa.nombre)
            put("ubicacion", empresa.ubicacion)
            put("fecha_creacion", dateFormat.format(empresa.fechaCreacion))
            put("numero_empleados", empresa.numeroEmpleados)
            put("ingresos_anuales", empresa.ingresosAnuales)
            put("latitud", empresa.latitud)
            put("longitud", empresa.longitud)
        }

        return try {
            db.beginTransaction()
            val result = db.insert("Empresa", null, values)
            db.setTransactionSuccessful()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun actualizarEmpresa(empresa: Empresa): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", empresa.nombre)
            put("ubicacion", empresa.ubicacion)
            put("fecha_creacion", dateFormat.format(empresa.fechaCreacion))
            put("numero_empleados", empresa.numeroEmpleados)
            put("ingresos_anuales", empresa.ingresosAnuales)
            put("latitud", empresa.latitud)
            put("longitud", empresa.longitud)
        }

        return try {
            db.beginTransaction()
            val result = db.update("Empresa", values, "id = ?", arrayOf(empresa.id.toString()))
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

    fun eliminarEmpresa(id: Int): Int {
        val db = dbHelper.writableDatabase
        return try {
            db.beginTransaction()
            val result = db.delete("Empresa", "id = ?", arrayOf(id.toString()))
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

    fun obtenerTodas(): List<Empresa> {
        val empresas = mutableListOf<Empresa>()
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM Empresa", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val empresa = Empresa(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")),
                        fechaCreacion = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_creacion"))) ?: Date(),
                        numeroEmpleados = cursor.getInt(cursor.getColumnIndexOrThrow("numero_empleados")),
                        ingresosAnuales = cursor.getDouble(cursor.getColumnIndexOrThrow("ingresos_anuales")),
                        latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud")),
                        longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))
                    )
                    empresas.add(empresa)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }
        return empresas
    }

    fun obtenerPorId(id: Int): Empresa? {
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null
        var empresa: Empresa? = null

        try {
            cursor = db.rawQuery("SELECT * FROM Empresa WHERE id = ?", arrayOf(id.toString()))
            if (cursor?.moveToFirst() == true) {
                empresa = Empresa(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")),
                    fechaCreacion = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_creacion"))) ?: Date(),
                    numeroEmpleados = cursor.getInt(cursor.getColumnIndexOrThrow("numero_empleados")),
                    ingresosAnuales = cursor.getDouble(cursor.getColumnIndexOrThrow("ingresos_anuales")),
                    latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud")),
                    longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }
        return empresa
    }
}