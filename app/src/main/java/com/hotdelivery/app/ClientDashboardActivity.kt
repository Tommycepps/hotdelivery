package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityClientDashboardBinding
import com.hotdelivery.app.util.PrefsManager

class ClientDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvWelcome.text = "Benvenuto, ${PrefsManager.getUserName(this)}! 🍽️"

        binding.btnOpenMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            PrefsManager.clearSession(this)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
