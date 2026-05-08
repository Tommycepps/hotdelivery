package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.util.PrefsManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se l'utente ha già una sessione attiva, vai direttamente alla dashboard
        if (PrefsManager.isLoggedIn(this)) {
            val role = PrefsManager.getUserRole(this)
            val intent = if (role == "cook") {
                Intent(this, CookDashboardActivity::class.java)
            } else {
                Intent(this, ClientDashboardActivity::class.java)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            return
        }

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
