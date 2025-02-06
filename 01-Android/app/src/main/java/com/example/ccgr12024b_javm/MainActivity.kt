package com.example.ccgr12024b_javm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent

import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Inicializar BDD
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener {
                irActividad(ACicloVida::class.java)
            }

        val botonIrListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonIrListView
            .setOnClickListener {
                irActividad(BListView::class.java)
            }

        val botonImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonImplicito
            .setOnClickListener {
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackContenidoIntentImplicito.launch(intentConRespuesta)
            }

        val botonExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonExplicito
            .setOnClickListener {
                val intentExplicito = Intent(
                    this, CIntentExplicitoParametros::class.java
                )

                intentExplicito.putExtra("nombre", "Adrian")
                intentExplicito.putExtra("apellido", "Eguez")
                intentExplicito.putExtra("edad", 34)
                intentExplicito.putExtra(
                    "entrenador",
                    BEntrenador(1, "Adrian", "Ejemplo")
                )

                callbackContenidoIntentExplicito.launch(intentExplicito)
            }

        val botonIrSQLite = findViewById<Button>(R.id.btn_sqlite)
        botonIrSQLite
            .setOnClickListener {
                irActividad(ECrudEntrenador::class.java)
            }

        val botonIrRecyclerView = findViewById<Button>(R.id.btn_recycler_view)
        botonIrRecyclerView
            .setOnClickListener {
                irActividad(FRecyclerView::class.java)
            }

        val botonGMaps = findViewById<Button>(R.id.btn_google_maps)
        botonIrRecyclerView
            .setOnClickListener {
                irActividad(FRecyclerView::class.java)
            }

        val botonAuth = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonIrRecyclerView
            .setOnClickListener {
                irActividad(HFirebaseUIAuth::class.java)
            }
    }

    fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    val callbackContenidoIntentExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val data = result.data?.getStringExtra("nombreModificado")
                mostrarSnackbar("$data")
            }
        }
    }

    val callbackContenidoIntentImplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    // validación de contacto
                    if (result.data!!.data != null) {
                        var uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Teléfono $telefono")
                    }
                }
            }
        }
}