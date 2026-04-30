package com.hotdelivery.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hotdelivery.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

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

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    db.collection("users").document(uid).get()
                        .addOnSuccessListener { doc ->
                            showLoading(false)
                            val name = doc.getString("name") ?: ""
                            val role = doc.getString("role") ?: "client"
                            saveUserLocally(name, email, role)
                            navigateToDashboard(role)
                        }
                        .addOnFailureListener {
                            showLoading(false)
                            Toast.makeText(this, "Errore nel recupero dati utente", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    showLoading(false)
                    Toast.makeText(this, "Login fallito: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUserLocally(name: String, email: String, role: String) {
        val prefs = getSharedPreferences("HotDeliveryPrefs", MODE_PRIVATE)
        prefs.edit().apply {
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
            else -> Intent(this, ClientDashboardActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !show
    }
}
