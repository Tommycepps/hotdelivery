package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityLoginBinding
import com.hotdelivery.app.util.PrefsManager

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
        val email    = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (TextUtils.isEmpty(email))    { binding.etEmail.error = "Inserisci l'email"; return }
        if (TextUtils.isEmpty(password)) { binding.etPassword.error = "Inserisci la password"; return }

        showLoading(true)

        val result = PrefsManager.validateLogin(this, email, password)
        showLoading(false)

        if (result == null) {
            Toast.makeText(this, "Credenziali non valide", Toast.LENGTH_SHORT).show()
            return
        }

        val (name, role) = result
        PrefsManager.saveSession(this, name, email, role)
        navigateToDashboard(role)
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
