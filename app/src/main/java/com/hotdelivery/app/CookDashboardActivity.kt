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
        val name = prefs.getString("userName", "Chef") ?: "Chef"
        binding.tvWelcome.text = "Benvenuto, Chef $name! 👨‍🍳"

        binding.btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
