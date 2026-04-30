package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityClientDashboardBinding

class ClientDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("HotDeliveryPrefs", MODE_PRIVATE)
        val name = prefs.getString("userName", "Cliente") ?: "Cliente"
        binding.tvWelcome.text = "Benvenuto, $name! 🍽️"

        binding.btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
