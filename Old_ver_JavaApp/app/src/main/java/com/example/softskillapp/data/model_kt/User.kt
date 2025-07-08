package com.example.softskillapp.data.model_kt

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val points: Int,
    val badges: List<String>,
    val assessment: Map<String, Int>,
    val study_frequency: Map<String, Long>
)