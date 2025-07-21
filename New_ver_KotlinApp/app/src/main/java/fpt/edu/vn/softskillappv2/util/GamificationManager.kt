package fpt.edu.vn.softskillappv2.util

import fpt.edu.vn.softskillappv2.data.model.User

class GamificationManager {
    
    companion object {
        // Level thresholds
        private val LEVEL_THRESHOLDS = mapOf(
            1 to 0,
            2 to 500,
            3 to 1000,
            4 to 1500,
            5 to 2000,
            6 to 2500,
            7 to 3000,
            8 to 3500,
            9 to 4000,
            10 to 5000
        )
        
        // Badge definitions
        private val BADGES = mapOf(
            "BEGINNER" to "Complete your first assessment",
            "INTERMEDIATE" to "Reach level 3",
            "EXPERT" to "Reach level 8",
            "STREAK_MASTER" to "Maintain a 7-day study streak",
            "VIDEO_CREATOR" to "Upload 10 videos",
            "SOCIAL_BUTTERFLY" to "Gain 100 followers",
            "QUIZ_MASTER" to "Complete 50 quizzes",
            "POINT_COLLECTOR" to "Earn 5000 points"
        )
    }
    
    fun calculateLevel(points: Int): Int {
        return LEVEL_THRESHOLDS.entries
            .filter { it.value <= points }
            .maxOfOrNull { it.key } ?: 1
    }
    
    fun getLevelProgress(points: Int): Pair<Int, Int> {
        val currentLevel = calculateLevel(points)
        val currentLevelPoints = LEVEL_THRESHOLDS[currentLevel] ?: 0
        val nextLevel = currentLevel + 1
        val nextLevelPoints = LEVEL_THRESHOLDS[nextLevel] ?: currentLevelPoints
        
        val progress = if (nextLevelPoints > currentLevelPoints) {
            points - currentLevelPoints
        } else {
            0
        }
        
        val total = if (nextLevelPoints > currentLevelPoints) {
            nextLevelPoints - currentLevelPoints
        } else {
            1
        }
        
        return Pair(progress, total)
    }
    
    fun getLevelTitle(level: Int): String {
        return when {
            level >= 8 -> "Expert"
            level >= 5 -> "Advanced"
            level >= 3 -> "Intermediate"
            else -> "Beginner"
        }
    }
    
    fun getLevelColor(level: Long): Long {
        return when {
            level >= 8 -> 0xFFFFD700 // Gold
            level >= 5 -> 0xFFC0C0C0 // Silver
            level >= 3 -> 0xFFCD7F32 // Bronze
            else -> 0xFF808080 // Gray
        }
    }
    
    fun checkForNewBadges(user: User): List<String> {
        val newBadges = mutableListOf<String>()
        val currentBadges = user.badges.toSet()
        
        // Check level-based badges
        if (user.level >= 3 && !currentBadges.contains("INTERMEDIATE")) {
            newBadges.add("INTERMEDIATE")
        }
        
        if (user.level >= 8 && !currentBadges.contains("EXPERT")) {
            newBadges.add("EXPERT")
        }
        
        // Check video count badge
        if (user.videosCount >= 10 && !currentBadges.contains("VIDEO_CREATOR")) {
            newBadges.add("VIDEO_CREATOR")
        }
        
        // Check follower count badge
        if (user.followersCount >= 100 && !currentBadges.contains("SOCIAL_BUTTERFLY")) {
            newBadges.add("SOCIAL_BUTTERFLY")
        }
        
        // Check points badge
        if (user.points >= 5000 && !currentBadges.contains("POINT_COLLECTOR")) {
            newBadges.add("POINT_COLLECTOR")
        }
        
        return newBadges
    }
    
    fun getBadgeDescription(badgeName: String): String {
        return BADGES[badgeName] ?: "Unknown badge"
    }
    
    fun formatPoints(points: Int): String {
        return when {
            points >= 1000000 -> "${points / 1000000}M"
            points >= 1000 -> "${points / 1000}K"
            else -> points.toString()
        }
    }
    
    fun getNextMilestone(points: Int): Pair<String, Int> {
        val nextLevel = calculateLevel(points) + 1
        val nextLevelPoints = LEVEL_THRESHOLDS[nextLevel] ?: points
        
        return if (nextLevelPoints > points) {
            Pair("Level $nextLevel", nextLevelPoints - points)
        } else {
            Pair("Max Level", 0)
        }
    }
} 