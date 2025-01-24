package com.example.ccgr12024b_javm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button


class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cintent_explicito_parametros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenador")
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton.setOnClickListener {
            val intentDevolverRespuesta = Intent()
            intentDevolverRespuesta.putExtra("nombreModificado", nombre)
            intentDevolverRespuesta.putExtra("edad", edad)
            intentDevolverRespuesta.putExtra("apellido", apellido)
            intentDevolverRespuesta.putExtra("entrenador", entrenador.toString())

            setResult(RESULT_OK, intentDevolverRespuesta)
            finish()
        }


    }
}

