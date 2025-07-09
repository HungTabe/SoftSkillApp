package fpt.edu.vn.softskillappv2.data.repository

import fpt.edu.vn.softskillappv2.data.model.User
import fpt.edu.vn.softskillappv2.data.model.AssessmentResult
import fpt.edu.vn.softskillappv2.data.model.StudyFrequency
import fpt.edu.vn.softskillappv2.data.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UserRepository {
    private val postgrest = SupabaseClient.postgrest

    suspend fun createUser(user: User): Result<User> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("users").insert(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserByEmail(email: String): Result<User?> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("users").select {
                filter { eq("email", email) }
            }
            val users = response.decodeList<User>()
            Result.success(users.firstOrNull())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserPoints(userId: UUID, points: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("users").update({ set("points", points) }) {
                filter { eq("id", userId.toString()) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserBadges(userId: UUID, badges: List<String>): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("users").update({ set("badges", badges) }) {
                filter { eq("id", userId.toString()) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAssessmentResult(userId: UUID, assessment: AssessmentResult): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("users").update({ set("assessment", assessment) }) {
                filter { eq("id", userId.toString()) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateStudyFrequency(userId: UUID, studyFrequency: StudyFrequency): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("users").update({ set("study_frequency", studyFrequency) }) {
                filter { eq("id", userId.toString()) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTopUsers(limit: Int = 100): Result<List<User>> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("users").select {
                limit(limit.toLong())
            }
            val users = response.decodeList<User>()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 