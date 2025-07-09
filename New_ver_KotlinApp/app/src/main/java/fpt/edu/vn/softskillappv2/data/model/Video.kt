package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int = 0,
    val title: String,
    val youtubeUrl: String,
    val category: String,
    val uploaderId: Int? = null
) 