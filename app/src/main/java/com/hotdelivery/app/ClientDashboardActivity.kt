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
        binding.tvWelcome.text = "Benvenuto, ${prefs.getString("userName", "Cliente")}! 🍽️"

        binding.btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
