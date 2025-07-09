package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int = 0,
    val userId: Int,
    val videoId: Int,
    val text: String,
    val timestamp: Long? = null
) 