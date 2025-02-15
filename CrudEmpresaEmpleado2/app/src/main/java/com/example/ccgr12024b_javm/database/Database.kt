package com.example.ccgr12024b_javm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "EmpresaEmpleadoDB.db"
        private const val DATABASE_VERSION = 2  // Incrementamos la versión

        private const val TABLE_EMPRESA = "Empresa"
        private const val TABLE_EMPLEADO = "Empleado"

        private const val CREATE_TABLE_EMPRESA = """
            CREATE TABLE $TABLE_EMPRESA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                ubicacion TEXT NOT NULL,
                fecha_creacion TEXT NOT NULL,
                numero_empleados INTEGER NOT NULL,
                ingresos_anuales REAL NOT NULL,
                latitud REAL NOT NULL DEFAULT 0.0,
                longitud REAL NOT NULL DEFAULT 0.0
            )
        """

        private const val CREATE_TABLE_EMPLEADO = """
            CREATE TABLE $TABLE_EMPLEADO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_empresa INTEGER NOT NULL,
                nombre TEXT NOT NULL,
                edad INTEGER NOT NULL,
                puesto TEXT NOT NULL,
                fecha_contratacion TEXT NOT NULL,
                salario REAL NOT NULL,
                FOREIGN KEY(id_empresa) REFERENCES $TABLE_EMPRESA(id) ON DELETE CASCADE
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_EMPRESA)
        db.execSQL(CREATE_TABLE_EMPLEADO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Agregar las nuevas columnas
            db.execSQL("ALTER TABLE $TABLE_EMPRESA ADD COLUMN latitud REAL NOT NULL DEFAULT 0.0")
            db.execSQL("ALTER TABLE $TABLE_EMPRESA ADD COLUMN longitud REAL NOT NULL DEFAULT 0.0")
        }
    }
}