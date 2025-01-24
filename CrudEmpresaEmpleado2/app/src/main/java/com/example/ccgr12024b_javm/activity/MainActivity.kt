package com.example.ccgr12024b_javm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ccgr12024b_javm.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnVerEmpresas: Button
    private lateinit var btnAgregarEmpresa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnVerEmpresas = findViewById(R.id.btnVerEmpresas)
        btnAgregarEmpresa = findViewById(R.id.btnAgregarEmpresa)

        btnVerEmpresas.setOnClickListener {
            val intent = Intent(this, EmpresaListActivity::class.java)
            startActivity(intent)
        }

        btnAgregarEmpresa.setOnClickListener {
            val intent = Intent(this, EmpresaFormActivity::class.java)
            startActivity(intent)
        }
    }
}
