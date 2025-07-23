package fpt.edu.vn.softskillappv2.util

import android.content.Context
import android.content.SharedPreferences
import fpt.edu.vn.softskillappv2.data.model.LocalNote
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

    // Note storage methods
    fun saveNote(note: LocalNote) {
        val notes = getNotes().toMutableList()
        notes.add(note)
        saveNotes(notes)
    }

    fun getNotes(): List<LocalNote> {
        val notesJson = prefs.getString(Constants.PREF_NOTES, "[]")
        return try {
            Json.decodeFromString(notesJson ?: "[]")
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun deleteNote(noteId: String) {
        val notes = getNotes().toMutableList()
        notes.removeAll { it.id == noteId }
        saveNotes(notes)
    }

    private fun saveNotes(notes: List<LocalNote>) {
        val notesJson = Json.encodeToString(notes)
        prefs.edit().putString(Constants.PREF_NOTES, notesJson).apply()
    }

    fun saveUserAvatar(avatarUrl: String) {
        prefs.edit().putString("user_avatar", avatarUrl).apply()
    }

    fun getUserAvatar(): String? {
        return prefs.getString("user_avatar", null)
    }

    companion object {
        fun saveUserEmail(context: Context, email: String) {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(Constants.PREF_USER_EMAIL, email).apply()
        }

        fun saveUserId(context: Context, userId: String) {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(Constants.PREF_USER_ID, userId).apply()
        }

        fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(Constants.PREF_IS_LOGGED_IN, isLoggedIn).apply()
        }

        fun clearUserData(context: Context) {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
        }

        fun saveUserAvatar(context: Context, avatarUrl: String) {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString("user_avatar", avatarUrl).apply()
        }
        fun getUserAvatar(context: Context): String? {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            return prefs.getString("user_avatar", null)
        }
    }
} 