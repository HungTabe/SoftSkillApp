package fpt.edu.vn.softskillappv2.data.model

object AssessmentConstants {
    // Score ranges
    const val MIN_SCORE = 1
    const val MAX_SCORE = 4
    
    // Default values
    const val DEFAULT_SCORE = 1
    const val DEFAULT_USER_ANSWER = -1
    
    // Category values
    const val CATEGORY_SOCIAL = "social"
    const val CATEGORY_NEGOTIATION = "negotiation"
    const val CATEGORY_OFFICE_COMMUNICATION = "office_communication"
    
    // Helper methods
    fun isValidScore(score: Int): Boolean {
        return score in MIN_SCORE..MAX_SCORE
    }
    
    fun isValidUserAnswer(answer: Int, optionsCount: Int): Boolean {
        return answer in 0 until optionsCount
    }
    
    fun calculateScore(userAnswer: Int, correctAnswer: Int?, optionsCount: Int): Int {
        if (userAnswer < 0 || userAnswer >= optionsCount) return MIN_SCORE
        if (correctAnswer == null) return MIN_SCORE
        
        return when {
            userAnswer == correctAnswer -> MAX_SCORE
            userAnswer == (correctAnswer + 1) % optionsCount || 
            userAnswer == (correctAnswer - 1 + optionsCount) % optionsCount -> 3
            userAnswer == (correctAnswer + 2) % optionsCount || 
            userAnswer == (correctAnswer - 2 + optionsCount) % optionsCount -> 2
            else -> MIN_SCORE
        }
    }
} 