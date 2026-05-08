package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.util.PrefsManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            if (PrefsManager.isLoggedIn(this)) {
                val role = PrefsManager.getUserRole(this)
                val intent = if (role == "cook") {
                    Intent(this, CookDashboardActivity::class.java)
                } else {
                    Intent(this, ClientDashboardActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            startActivity(Intent(this, LoginActivity::class.java))
        } finally {
            finish()
        }
    }
}
