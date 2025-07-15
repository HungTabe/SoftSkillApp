package fpt.edu.vn.softskillappv2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int = 0,
    val title: String,
    val youtube_url: String,
    val category: String,
    val uploader_id: Int? = null
) 