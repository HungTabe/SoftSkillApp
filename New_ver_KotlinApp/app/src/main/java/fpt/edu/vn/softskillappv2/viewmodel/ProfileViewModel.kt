package fpt.edu.vn.softskillappv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fpt.edu.vn.softskillappv2.data.model.User
import fpt.edu.vn.softskillappv2.data.model.Video
import fpt.edu.vn.softskillappv2.data.repository.UserRepository
import fpt.edu.vn.softskillappv2.data.repository.VideoRepository
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager
import fpt.edu.vn.softskillappv2.util.GamificationManager
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val videoRepository = VideoRepository()
    private val gamificationManager = GamificationManager()
    
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    
    private val _userVideos = MutableLiveData<List<Video>>()
    val userVideos: LiveData<List<Video>> = _userVideos
    
    private val _likedVideos = MutableLiveData<List<Video>>()
    val likedVideos: LiveData<List<Video>> = _likedVideos
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private var sharedPrefsManager: SharedPrefsManager? = null
    
    fun setSharedPrefsManager(manager: SharedPrefsManager) {
        sharedPrefsManager = manager
        loadUserProfile()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userEmail = sharedPrefsManager?.getUserEmail()
                if (userEmail != null) {
                    val userResult = userRepository.getUserByEmail(userEmail)
                    userResult.onSuccess { user ->
                        if (user != null) {
                            _user.value = user
                            loadUserVideos(user.id)
                            loadLikedVideos()
                        } else {
                            // Create default user profile
                            val defaultUser = User(
                                email = userEmail,
                                name = "Sarah Wilson",
                                title = "Soft Skills Expert",
                                points = 2450,
                                level = 5,
                                videosCount = 24,
                                followingCount = 128,
                                followersCount = 1200
                            )
                            _user.value = defaultUser
                        }
                    }.onFailure { exception ->
                        _error.value = "Failed to load user profile: ${exception.message}"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error loading profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun loadUserVideos(userId: Int) {
        viewModelScope.launch {
            try {
                val videosResult = videoRepository.getAllVideos()
                videosResult.onSuccess { videos ->
                    // Filter videos by uploader_id or create sample data
                    val userVideos = videos.filter { it.uploader_id == userId }
                    if (userVideos.isEmpty()) {
                        // Create sample videos for demonstration
                        val sampleVideos = createSampleVideos()
                        _userVideos.value = sampleVideos
                    } else {
                        _userVideos.value = userVideos
                    }
                }.onFailure { exception ->
                    _error.value = "Failed to load videos: ${exception.message}"
                }
            } catch (e: Exception) {
                _error.value = "Error loading videos: ${e.message}"
            }
        }
    }
    
    private fun loadLikedVideos() {
        viewModelScope.launch {
            try {
                val videosResult = videoRepository.getAllVideos()
                videosResult.onSuccess { videos ->
                    // Filter liked videos or create sample data
                    val likedVideos = videos.filter { it.isLiked }
                    if (likedVideos.isEmpty()) {
                        // Create sample liked videos for demonstration
                        val sampleLikedVideos = createSampleLikedVideos()
                        _likedVideos.value = sampleLikedVideos
                    } else {
                        _likedVideos.value = likedVideos
                    }
                }.onFailure { exception ->
                    _error.value = "Failed to load liked videos: ${exception.message}"
                }
            } catch (e: Exception) {
                _error.value = "Error loading liked videos: ${e.message}"
            }
        }
    }
    
    private fun createSampleVideos(): List<Video> {
        return listOf(
            Video(
                id = 1,
                title = "Communication Skills",
                youtube_url = "https://youtube.com/watch?v=sample1",
                category = "Communication Skills",
                thumbnail = "https://upload.wikimedia.org/wikipedia/commons/b/bb/Employee_Training_-_Communication_Skills_Illustration.jpg",
                likesCount = 1200,
                viewsCount = 5000
            ),
            Video(
                id = 2,
                title = "Leadership Development",
                youtube_url = "https://youtube.com/watch?v=sample2",
                category = "Leadership",
                thumbnail = "https://blog.kenjo.io/hubfs/Leadership.jpeg",
                likesCount = 850,
                viewsCount = 3200
            ),
            Video(
                id = 3,
                title = "Team Building",
                youtube_url = "https://youtube.com/watch?v=sample3",
                category = "Team Building",
                thumbnail = "https://static.vecteezy.com/system/resources/previews/013/976/430/non_2x/teamwork-concept-group-of-people-climbing-a-mountain-company-employees-working-together-mutual-achievement-of-the-goal-team-building-people-helping-and-supporting-each-other-at-work-vector.jpg",
                likesCount = 650,
                viewsCount = 2800
            ),
            Video(
                id = 4,
                title = "Problem Solving",
                youtube_url = "https://youtube.com/watch?v=sample4",
                category = "Problem Solving",
                thumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvdJTNeShc8E4X0hC2gZIPcLcvsIH_LYzEug&s",
                likesCount = 920,
                viewsCount = 4100
            )
        )
    }
    
    private fun createSampleLikedVideos(): List<Video> {
        return listOf(
            Video(
                id = 5,
                title = "Public Speaking Tips",
                youtube_url = "https://youtube.com/watch?v=sample5",
                category = "Public Speaking",
                thumbnail = "https://example.com/thumbnail5.jpg",
                likesCount = 1500,
                viewsCount = 7200,
                isLiked = true
            ),
            Video(
                id = 6,
                title = "Conflict Resolution",
                youtube_url = "https://youtube.com/watch?v=sample6",
                category = "Conflict Resolution",
                thumbnail = "https://example.com/thumbnail6.jpg",
                likesCount = 1100,
                viewsCount = 4800,
                isLiked = true
            )
        )
    }
    
    fun calculateLevel(points: Int): Int {
        return gamificationManager.calculateLevel(points)
    }
    
    fun getLevelTitle(level: Int): String {
        return gamificationManager.getLevelTitle(level)
    }
    
    fun getLevelProgress(points: Int): Pair<Int, Int> {
        return gamificationManager.getLevelProgress(points)
    }
    
    fun getNextMilestone(points: Int): Pair<String, Int> {
        return gamificationManager.getNextMilestone(points)
    }
    
    fun checkForNewBadges(user: User): List<String> {
        return gamificationManager.checkForNewBadges(user)
    }
    
    fun formatPoints(points: Int): String {
        return gamificationManager.formatPoints(points)
    }
} 