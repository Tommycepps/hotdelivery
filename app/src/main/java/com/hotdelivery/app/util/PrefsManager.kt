package com.hotdelivery.app.util

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

object PrefsManager {

    private const val PREF_NAME     = "hotdelivery_prefs"
    private const val KEY_LOGGED_IN = "logged_in"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_ROLE = "user_role"
    private const val KEY_USERS     = "registered_users"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ── Sessione ──────────────────────────────────────────────────────────

    fun isLoggedIn(context: Context): Boolean =
        prefs(context).getBoolean(KEY_LOGGED_IN, false)

    fun getUserName(context: Context): String =
        prefs(context).getString(KEY_USER_NAME, "Utente") ?: "Utente"

    fun getUserEmail(context: Context): String =
        prefs(context).getString(KEY_USER_EMAIL, "") ?: ""

    fun getUserRole(context: Context): String =
        prefs(context).getString(KEY_USER_ROLE, "client") ?: "client"

    fun saveSession(context: Context, name: String, email: String, role: String) {
        prefs(context).edit().apply {
            putBoolean(KEY_LOGGED_IN, true)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_ROLE, role)
            apply()
        }
    }

    fun clearSession(context: Context) {
        prefs(context).edit().apply {
            putBoolean(KEY_LOGGED_IN, false)
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_ROLE)
            apply()
        }
    }

    // ── Registrazione utenti ──────────────────────────────────────────────

    /**
     * Registra un nuovo utente. Restituisce false se l'email è già in uso.
     */
    fun registerUser(context: Context, name: String, email: String,
                     password: String, role: String): Boolean {
        val users = getUsers(context)
        for (i in 0 until users.length()) {
            val user = users.getJSONObject(i)
            if (user.getString("email").equals(email, ignoreCase = true)) return false
        }
        val newUser = JSONObject().apply {
            put("name", name)
            put("email", email)
            put("password", password)
            put("role", role)
        }
        users.put(newUser)
        prefs(context).edit().putString(KEY_USERS, users.toString()).apply()
        return true
    }

    /**
     * Verifica credenziali. Restituisce il JSONObject dell'utente oppure null.
     */
    fun loginUser(context: Context, email: String, password: String): JSONObject? {
        val users = getUsers(context)
        for (i in 0 until users.length()) {
            val user = users.getJSONObject(i)
            if (user.getString("email").equals(email, ignoreCase = true) &&
                user.getString("password") == password) {
                return user
            }
        }
        return null
    }

    private fun getUsers(context: Context): JSONArray {
        val raw = prefs(context).getString(KEY_USERS, "[]") ?: "[]"
        return try { JSONArray(raw) } catch (e: Exception) { JSONArray() }
    }
}
