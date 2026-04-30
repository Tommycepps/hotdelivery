package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { loginUser() }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (TextUtils.isEmpty(email)) { binding.etEmail.error = "Inserisci l'email"; return }
        if (TextUtils.isEmpty(password)) { binding.etPassword.error = "Inserisci la password"; return }

        showLoading(true)

        val usersPrefs = getSharedPreferences("HotDeliveryUsers", MODE_PRIVATE)
        val storedPassword = usersPrefs.getString("pwd_$email", null)
        val storedName = usersPrefs.getString("name_$email", null)
        val storedRole = usersPrefs.getString("role_$email", null)

        if (storedPassword == null) {
            showLoading(false)
            Toast.makeText(this, "Account non trovato", Toast.LENGTH_SHORT).show()
            return
        }
        if (storedPassword != password) {
            showLoading(false)
            Toast.makeText(this, "Password errata", Toast.LENGTH_SHORT).show()
            return
        }

        showLoading(false)
        saveUserLocally(storedName ?: "", email, storedRole ?: "client")
        navigateToDashboard(storedRole ?: "client")
    }

    private fun saveUserLocally(name: String, email: String, role: String) {
        getSharedPreferences("HotDeliveryPrefs", MODE_PRIVATE).edit().apply {
            putBoolean("isLoggedIn", true)
            putString("userName", name)
            putString("userEmail", email)
            putString("userRole", role)
            apply()
        }
    }

    private fun navigateToDashboard(role: String) {
        val intent = when (role) {
            "cook" -> Intent(this, CookDashboardActivity::class.java)
            else   -> Intent(this, ClientDashboardActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !show
    }
}
