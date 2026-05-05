package com.hotdelivery.app.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Classe centralizzata per la gestione delle SharedPreferences.
 * Elimina la duplicazione di logica presente in LoginActivity e RegisterActivity.
 */
object PrefsManager {

    private const val PREFS_SESSION = "HotDeliveryPrefs"
    private const val PREFS_USERS   = "HotDeliveryUsers"

    // --- Session ---

    fun saveSession(context: Context, name: String, email: String, role: String) {
        prefs(context, PREFS_SESSION).edit().apply {
            putBoolean("isLoggedIn", true)
            putString("userName",  name)
            putString("userEmail", email)
            putString("userRole",  role)
            apply()
        }
    }

    fun clearSession(context: Context) =
        prefs(context, PREFS_SESSION).edit().clear().apply()

    fun isLoggedIn(context: Context): Boolean =
        prefs(context, PREFS_SESSION).getBoolean("isLoggedIn", false)

    fun getUserName(context: Context): String =
        prefs(context, PREFS_SESSION).getString("userName", "Utente") ?: "Utente"

    fun getUserRole(context: Context): String =
        prefs(context, PREFS_SESSION).getString("userRole", "client") ?: "client"

    // --- Users store ---

    fun registerUser(context: Context, name: String, email: String, password: String, role: String): Boolean {
        val p = prefs(context, PREFS_USERS)
        if (p.contains("pwd_$email")) return false
        p.edit().apply {
            putString("pwd_$email",  password)
            putString("name_$email", name)
            putString("role_$email", role)
            apply()
        }
        return true
    }

    /** Restituisce la coppia (name, role) se le credenziali sono corrette, altrimenti null. */
    fun validateLogin(context: Context, email: String, password: String): Pair<String, String>? {
        val p = prefs(context, PREFS_USERS)
        val storedPwd  = p.getString("pwd_$email", null)  ?: return null
        if (storedPwd != password) return null
        val name = p.getString("name_$email", "") ?: ""
        val role = p.getString("role_$email", "client") ?: "client"
        return Pair(name, role)
    }

    // --- Private helper ---

    private fun prefs(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
}
