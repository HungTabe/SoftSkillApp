package fpt.edu.vn.softskillappv2.data.repository

import fpt.edu.vn.softskillappv2.data.model.AssessmentQuestion
import fpt.edu.vn.softskillappv2.data.model.AssessmentResult
import fpt.edu.vn.softskillappv2.data.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssessmentRepository {
    private val postgrest = SupabaseClient.postgrest

    suspend fun getAssessmentQuestions(): Result<List<AssessmentQuestion>> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("assessment_questions").select()
            val questions = response.decodeList<AssessmentQuestion>()
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAssessmentQuestionsByCategory(category: String): Result<List<AssessmentQuestion>> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("assessment_questions").select {
                filter { eq("category", category) }
            }
            val questions = response.decodeList<AssessmentQuestion>()
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveAssessmentResult(assessmentResult: AssessmentResult): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("assessment_results").insert(assessmentResult)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAssessmentResultsByUserId(userId: Int): Result<List<AssessmentResult>> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("assessment_results").select {
                filter { eq("user_id", userId) }
            }
            val results = response.decodeList<AssessmentResult>()
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAssessmentResultById(resultId: Int): Result<AssessmentResult?> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("assessment_results").select {
                filter { eq("id", resultId) }
            }
            val results = response.decodeList<AssessmentResult>()
            Result.success(results.firstOrNull())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 