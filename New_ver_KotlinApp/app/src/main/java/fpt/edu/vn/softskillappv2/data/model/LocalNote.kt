package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocalNote(
    val id: String,
    val content: String,
    val timestamp: Long,
    val createdAt: Long = System.currentTimeMillis()
) 