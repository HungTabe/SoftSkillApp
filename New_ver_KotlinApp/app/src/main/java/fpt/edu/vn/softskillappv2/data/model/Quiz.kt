package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val id: Int = 0,
    val video_id: Int,
    val question: String,
    val options: List<String> = emptyList(),
    val correct_answer: Int? = null,
    val explanation: String? = null
)

@Serializable
data class QuizResult(
    val id: Int = 0,
    val quiz_id: Int,
    val user_id: Int,
    val user_answer: Int,
    val is_correct: Boolean,
    val score: Int,
    val completed_at: Long? = null
) 