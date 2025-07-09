package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int = 0,
    val videoId: Int,
    val userId: Int,
    val text: String,
    val timestamp: Long? = null
) 