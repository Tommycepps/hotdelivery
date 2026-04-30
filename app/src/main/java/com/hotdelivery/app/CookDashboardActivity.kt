package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityCookDashboardBinding

class CookDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCookDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCookDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("HotDeliveryPrefs", MODE_PRIVATE)
        binding.tvWelcome.text = "Benvenuto, Chef ${prefs.getString("userName", "Chef")}! 👨‍🍳"

        binding.btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
