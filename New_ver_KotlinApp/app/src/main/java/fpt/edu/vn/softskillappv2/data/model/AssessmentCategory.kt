package fpt.edu.vn.softskillappv2.data.model

enum class AssessmentCategory(val displayName: String, val description: String) {
    SOCIAL("Kỹ năng xã hội", "Các kỹ năng tương tác và xây dựng mối quan hệ"),
    NEGOTIATION("Kỹ năng đàm phán", "Các kỹ năng thương lượng và đạt được thỏa thuận"),
    OFFICE_COMMUNICATION("Giao tiếp văn phòng", "Các kỹ năng giao tiếp trong môi trường công sở");
    
    companion object {
        fun fromString(category: String): AssessmentCategory? {
            return when (category.lowercase()) {
                "social" -> SOCIAL
                "negotiation" -> NEGOTIATION
                "office_communication" -> OFFICE_COMMUNICATION
                else -> null
            }
        }
    }
} 