package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val id: Int = 0,
    val videoId: Int,
    val question: String,
    val options: List<String> = emptyList(),
    val correctAnswer: Int? = null,
    val explanation: String? = null
)

@Serializable
data class QuizResult(
    val id: Int = 0,
    val quizId: Int,
    val userId: Int,
    val userAnswer: Int,
    val isCorrect: Boolean,
    val score: Int,
    val completedAt: Long? = null
) 