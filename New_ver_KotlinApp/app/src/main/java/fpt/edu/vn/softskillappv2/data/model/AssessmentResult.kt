package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class AssessmentResult(
    val id: Int = 0,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("question_id")
    val questionId: Int,
    @SerialName("user_answer")
    val userAnswer: Int, // Chỉ số lựa chọn của người dùng (bắt đầu từ 0)
    @SerialName("is_correct")
    val isCorrect: Boolean, // Xác định xem câu trả lời có đúng không
    val score: Int, // Điểm số cho lựa chọn của người dùng (tra cứu từ option_scores)
    @SerialName("completed_at")
    val completedAt: Long? = null // Thời gian hoàn thành (Unix timestamp)
) 