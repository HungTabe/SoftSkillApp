package fpt.edu.vn.softskillappv2.data.repository

import fpt.edu.vn.softskillappv2.data.model.Quiz
import fpt.edu.vn.softskillappv2.data.model.QuizResult
import fpt.edu.vn.softskillappv2.data.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class QuizRepository {
    private val postgrest = SupabaseClient.postgrest

    suspend fun getQuizzesByVideoId(videoId: UUID): Result<List<Quiz>> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("quizzes").select {
                filter { eq("video_id", videoId.toString()) }
            }
            val quizzes = response.decodeList<Quiz>()
            Result.success(quizzes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createQuiz(quiz: Quiz): Result<Quiz> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("quizzes").insert(quiz)
            Result.success(quiz)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveQuizResult(quizResult: QuizResult): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("quiz_results").insert(quizResult)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getQuizById(quizId: UUID): Result<Quiz?> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("quizzes").select {
                filter { eq("id", quizId.toString()) }
            }
            val quizzes = response.decodeList<Quiz>()
            Result.success(quizzes.firstOrNull())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 