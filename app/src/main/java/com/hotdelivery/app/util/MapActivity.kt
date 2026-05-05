package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityMapBinding
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.OnMapReadyCallback
import org.maplibre.android.maps.Style
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapLibreMap: MapLibreMap

    // Posizione iniziale: Bologna
    private val defaultLat = 44.4949
    private val defaultLng = 11.3426

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapLibre.getInstance(this)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.btnShare.setOnClickListener { shareLocation() }
        binding.btnBack.setOnClickListener { finish() }
    }

    override fun onMapReady(map: MapLibreMap) {
        mapLibreMap = map
        map.setStyle(
            Style.Builder().fromUri("https://demotiles.maplibre.org/style.json")
        ) {
            map.cameraPosition = CameraPosition.Builder()
                .target(LatLng(defaultLat, defaultLng))
                .zoom(12.0)
                .build()
        }
    }

    private fun shareLocation() {
        val lat = mapLibreMap.cameraPosition.target?.latitude  ?: defaultLat
        val lng = mapLibreMap.cameraPosition.target?.longitude ?: defaultLng
        val text = "📍 Posizione HotDelivery:\nhttps://www.openstreetmap.org/?mlat=$lat&mlon=$lng&zoom=15"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Condividi posizione"))
    }

    // Lifecycle del MapView
    override fun onStart()   { super.onStart();   binding.mapView.onStart() }
    override fun onResume()  { super.onResume();  binding.mapView.onResume() }
    override fun onPause()   { super.onPause();   binding.mapView.onPause() }
    override fun onStop()    { super.onStop();    binding.mapView.onStop() }
    override fun onDestroy() { super.onDestroy(); binding.mapView.onDestroy() }
    override fun onSaveInstanceState(out: Bundle) { super.onSaveInstanceState(out); binding.mapView.onSaveInstanceState(out) }
    override fun onLowMemory() { super.onLowMemory(); binding.mapView.onLowMemory() }
}