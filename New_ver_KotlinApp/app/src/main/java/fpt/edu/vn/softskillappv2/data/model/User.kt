package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val email: String,
    val name: String = "",
    val title: String = "",
    val profilePicture: String = "",
    val points: Int = 0,
    val level: Int = 1,
    val videosCount: Int = 0,
    val followingCount: Int = 0,
    val followersCount: Int = 0,
    val badges: List<String> = emptyList(),
    val assessment: AssessmentResult_type? = null,
    val study_frequency: StudyFrequency? = null
)

@Serializable
data class AssessmentResult_type(
    val level: String? = null,
    val score: Int? = null
)

@Serializable
data class StudyFrequency(
    val daily: Int? = null,
    val weekly: Int? = null,
    val currentStreak: Int? = null,
    val longestStreak: Int? = null,
    val lastLoginDate: Long? = null,
    val totalStudyDays: Int? = null
) 