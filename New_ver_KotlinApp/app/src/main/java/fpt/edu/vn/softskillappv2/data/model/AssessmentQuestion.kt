package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import fpt.edu.vn.softskillappv2.data.model.AssessmentCategory

@Serializable
data class AssessmentQuestion(
    val id: Int = 0,
    val question: String,
    val options: List<String> = emptyList(),
    @SerialName("option_scores")
    val optionScores: List<Int> = emptyList(), // Mảng điểm số tương ứng với từng lựa chọn
    val explanation: String? = null,
    val category: String // 'social', 'negotiation', 'office_communication'
) {
    fun getCategoryEnum(): AssessmentCategory? {
        return AssessmentCategory.fromString(category)
    }
    
    // Helper method to get score for a specific option index
    fun getScoreForOption(optionIndex: Int): Int {
        return if (optionIndex >= 0 && optionIndex < optionScores.size) {
            optionScores[optionIndex]
        } else {
            0
        }
    }
    
    // Helper method to get maximum possible score for this question
    fun getMaxScore(): Int {
        return optionScores.maxOrNull() ?: 0
    }
}