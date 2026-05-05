package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hotdelivery.app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { registerUser() }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser() {
        val name            = binding.etName.text.toString().trim()
        val email           = binding.etEmail.text.toString().trim()
        val password        = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val selectedRole    = when (binding.roleGroup.checkedRadioButtonId) {
            R.id.rbCook   -> "cook"
            R.id.rbClient -> "client"
            else          -> ""
        }

        if (TextUtils.isEmpty(name))     { binding.etName.error = "Inserisci il tuo nome"; return }
        if (TextUtils.isEmpty(email))    { binding.etEmail.error = "Inserisci la tua email"; return }
        if (TextUtils.isEmpty(password)) { binding.etPassword.error = "Inserisci una password"; return }
        if (password.length < 6)         { binding.etPassword.error = "La password deve avere almeno 6 caratteri"; return }
        if (password != confirmPassword) { binding.etConfirmPassword.error = "Le password non corrispondono"; return }
        if (selectedRole.isEmpty())      { Toast.makeText(this, "Seleziona il tuo ruolo", Toast.LENGTH_SHORT).show(); return }

        showLoading(true)

        val usersPrefs = getSharedPreferences("HotDeliveryUsers", MODE_PRIVATE)
        if (usersPrefs.contains("pwd_$email")) {
            showLoading(false)
            Toast.makeText(this, "Email già registrata", Toast.LENGTH_SHORT).show()
            return
        }

        usersPrefs.edit().apply {
            putString("pwd_$email",  password)
            putString("name_$email", name)
            putString("role_$email", selectedRole)
            apply()
        }

        showLoading(false)
        saveUserLocally(name, email, selectedRole)
        navigateToDashboard(selectedRole)
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
        binding.btnRegister.isEnabled = !show
    }
}
