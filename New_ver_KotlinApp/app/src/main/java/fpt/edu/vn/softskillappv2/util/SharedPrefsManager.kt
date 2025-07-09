package fpt.edu.vn.softskillappv2.util

import android.content.Context
import android.content.SharedPreferences
import java.util.UUID

class SharedPrefsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREF_NAME, 
        Context.MODE_PRIVATE
    )

    fun saveUserId(userId: UUID) {
        prefs.edit().putString(Constants.PREF_USER_ID, userId.toString()).apply()
    }

    fun getUserId(): UUID? {
        val userIdString = prefs.getString(Constants.PREF_USER_ID, null)
        return userIdString?.let { UUID.fromString(it) }
    }

    fun saveUserEmail(email: String) {
        prefs.edit().putString(Constants.PREF_USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return prefs.getString(Constants.PREF_USER_EMAIL, null)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(Constants.PREF_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false)
    }

    fun setAssessmentCompleted(completed: Boolean) {
        prefs.edit().putBoolean(Constants.PREF_ASSESSMENT_COMPLETED, completed).apply()
    }

    fun isAssessmentCompleted(): Boolean {
        return prefs.getBoolean(Constants.PREF_ASSESSMENT_COMPLETED, false)
    }

    fun clearUserData() {
        prefs.edit().clear().apply()
    }
} 