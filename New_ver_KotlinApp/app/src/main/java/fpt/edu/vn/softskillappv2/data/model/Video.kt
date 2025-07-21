package fpt.edu.vn.softskillappv2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Video(
    val id: Int = 0,
    val title: String,
    val youtube_url: String,
    val category: String,
    val uploader_id: Int? = null
) : Parcelable 