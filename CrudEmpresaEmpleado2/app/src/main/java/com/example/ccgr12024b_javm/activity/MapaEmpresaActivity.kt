package com.example.ccgr12024b_javm.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ccgr12024b_javm.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapaEmpresaActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar osmdroid
        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        setContentView(R.layout.activity_mapa_empresa)

        // Inicializar mapView
        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Obtener coordenadas
        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)
        val nombre = intent.getStringExtra("nombre") ?: ""

        // Configurar el punto en el mapa
        val punto = GeoPoint(latitud, longitud)
        mapView.controller.setZoom(18.0)
        mapView.controller.setCenter(punto)

        // Crear marcador
        val marcador = Marker(mapView)
        marcador.position = punto
        marcador.title = nombre
        marcador.snippet = "Lat: ${"%.4f".format(latitud)}\nLong: ${"%.4f".format(longitud)}"

        // Configurar icono
        val icono = ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_map)
        icono?.setTint(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        marcador.icon = icono

        // Configurar ancla
        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Agregar al mapa
        mapView.overlays.add(marcador)

        // Mostrar información
        marcador.showInfoWindow()

        // Refrescar mapa
        mapView.invalidate()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDetach()
    }
}