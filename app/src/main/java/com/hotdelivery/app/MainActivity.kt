package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if user is already logged in
        val prefs = getSharedPreferences("HotDeliveryPrefs", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            val role = prefs.getString("userRole", "")
            navigateToDashboard(role)
            return
        }

        // Animate logo
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_up)
        binding.logoImage.startAnimation(fadeIn)

        val fadeInDelayed = AnimationUtils.loadAnimation(this, R.anim.fade_in_up_delayed)
        binding.appName.startAnimation(fadeInDelayed)
        binding.tagline.startAnimation(fadeInDelayed)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }, 2500)
    }

    private fun navigateToDashboard(role: String?) {
        val intent = when (role) {
            "cook" -> Intent(this, CookDashboardActivity::class.java)
            else -> Intent(this, ClientDashboardActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
