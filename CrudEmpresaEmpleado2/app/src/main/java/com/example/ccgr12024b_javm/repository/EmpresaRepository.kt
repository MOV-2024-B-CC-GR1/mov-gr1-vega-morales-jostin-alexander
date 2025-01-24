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
        }
        val result = db.insert("Empresa", null, values)
        db.close()
        return result
    }

    fun actualizarEmpresa(empresa: Empresa): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", empresa.nombre)
            put("ubicacion", empresa.ubicacion)
            put("fecha_creacion", dateFormat.format(empresa.fechaCreacion))
            put("numero_empleados", empresa.numeroEmpleados)
            put("ingresos_anuales", empresa.ingresosAnuales)
        }
        val result = db.update("Empresa", values, "id = ?", arrayOf(empresa.id.toString()))
        db.close()
        return result
    }

    fun eliminarEmpresa(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete("Empresa", "id = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun obtenerTodas(): List<Empresa> {
        val empresas = mutableListOf<Empresa>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Empresa", null)

        if (cursor.moveToFirst()) {
            do {
                try {
                    val empresa = Empresa(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")),
                        fechaCreacion = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_creacion"))) ?: Date(),
                        numeroEmpleados = cursor.getInt(cursor.getColumnIndexOrThrow("numero_empleados")),
                        ingresosAnuales = cursor.getDouble(cursor.getColumnIndexOrThrow("ingresos_anuales"))
                    )
                    empresas.add(empresa)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return empresas
    }

    fun obtenerPorId(id: Int): Empresa? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Empresa WHERE id = ?", arrayOf(id.toString()))
        var empresa: Empresa? = null

        if (cursor.moveToFirst()) {
            empresa = Empresa(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")),
                fechaCreacion = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_creacion"))) ?: Date(),
                numeroEmpleados = cursor.getInt(cursor.getColumnIndexOrThrow("numero_empleados")),
                ingresosAnuales = cursor.getDouble(cursor.getColumnIndexOrThrow("ingresos_anuales"))
            )
        }

        cursor.close()
        db.close()
        return empresa
    }
}
