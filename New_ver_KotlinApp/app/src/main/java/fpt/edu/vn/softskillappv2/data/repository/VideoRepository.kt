package fpt.edu.vn.softskillappv2.data.repository

import fpt.edu.vn.softskillappv2.data.model.Video
import fpt.edu.vn.softskillappv2.data.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository {
    private val postgrest = SupabaseClient.postgrest

    suspend fun getAllVideos(): Result<List<Video>> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("videos").select { }
            val videos = response.decodeList<Video>()
            Result.success(videos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getVideoById(videoId: Int): Result<Video?> = withContext(Dispatchers.IO) {
        try {
            val response = postgrest.from("videos").select {
                filter { eq("id", videoId) }
            }
            val videos = response.decodeList<Video>()
            Result.success(videos.firstOrNull())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createVideo(video: Video): Result<Video> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("videos").insert(video)
            Result.success(video)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateVideoViews(videoId: Int, newViews: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("videos").update({ set("views", newViews) }) {
                filter { eq("id", videoId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateVideoLikes(videoId: Int, newLikes: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            postgrest.from("videos").update({ set("likes", newLikes) }) {
                filter { eq("id", videoId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 