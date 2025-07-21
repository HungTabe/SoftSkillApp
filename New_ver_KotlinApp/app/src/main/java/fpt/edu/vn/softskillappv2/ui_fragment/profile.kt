package fpt.edu.vn.softskillappv2.ui_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Video
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager
import fpt.edu.vn.softskillappv2.viewmodel.ProfileViewModel

class profile : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var videoAdapter: ProfileVideoAdapter
    private lateinit var sharedPrefsManager: SharedPrefsManager
    
    // UI Components
    private lateinit var profilePicture: ImageView
    private lateinit var userName: TextView
    private lateinit var userTitle: TextView
    private lateinit var userPoints: TextView
    private lateinit var userLevel: TextView
    private lateinit var videosCount: TextView
    private lateinit var followingCount: TextView
    private lateinit var followersCount: TextView
    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var backButton: ImageView
    private lateinit var editProfileButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initializeViews(view)
        setupViewModel()
        setupRecyclerView()
        setupTabLayout()
        setupClickListeners()
        observeViewModel()
    }

    private fun initializeViews(view: View) {
        profilePicture = view.findViewById(R.id.profilePicture)
        userName = view.findViewById(R.id.userName)
        userTitle = view.findViewById(R.id.userTitle)
        userPoints = view.findViewById(R.id.userPoints)
        userLevel = view.findViewById(R.id.userLevel)
        videosCount = view.findViewById(R.id.videosCount)
        followingCount = view.findViewById(R.id.followingCount)
        followersCount = view.findViewById(R.id.followersCount)
        videoRecyclerView = view.findViewById(R.id.videoRecyclerView)
        tabLayout = view.findViewById(R.id.tabLayout)
        loadingIndicator = view.findViewById(R.id.loadingIndicator)
        backButton = view.findViewById(R.id.backButton)
        editProfileButton = view.findViewById(R.id.editProfileButton)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        sharedPrefsManager = SharedPrefsManager(requireContext())
        viewModel.setSharedPrefsManager(sharedPrefsManager)
    }

    private fun setupRecyclerView() {
        videoAdapter = ProfileVideoAdapter(emptyList()) { video ->
            // Handle video click
            Toast.makeText(context, "Playing: ${video.title}", Toast.LENGTH_SHORT).show()
        }
        
        videoRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = videoAdapter
        }
    }

    private fun setupTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> loadUserVideos()
                    1 -> loadLikedVideos()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            // Handle back button click
            requireActivity().onBackPressed()
        }

        editProfileButton.setOnClickListener {
            // Handle edit profile click
            Toast.makeText(context, "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            updateUserInfo(user)
        }

        viewModel.userVideos.observe(viewLifecycleOwner) { videos ->
            if (tabLayout.selectedTabPosition == 0) {
                videoAdapter.updateVideos(videos)
            }
        }

        viewModel.likedVideos.observe(viewLifecycleOwner) { videos ->
            if (tabLayout.selectedTabPosition == 1) {
                videoAdapter.updateVideos(videos)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUserInfo(user: fpt.edu.vn.softskillappv2.data.model.User) {
        userName.text = user.name
        userTitle.text = user.title
        userPoints.text = "${viewModel.formatPoints(user.points)} points"
        userLevel.text = "Level ${user.level}"
        videosCount.text = user.videosCount.toString()
        followingCount.text = user.followingCount.toString()
        followersCount.text = formatCount(user.followersCount)
        
        // Check for new badges
        val newBadges = viewModel.checkForNewBadges(user)
        if (newBadges.isNotEmpty()) {
            showBadgeNotification(newBadges)
        }
        
        // Update profile picture (in a real app, you'd load from URL)
        // profilePicture.load(user.profilePicture)
    }
    
    private fun showBadgeNotification(newBadges: List<String>) {
        val badgeNames = newBadges.joinToString(", ")
        Toast.makeText(context, "🎉 New badges earned: $badgeNames", Toast.LENGTH_LONG).show()
    }

    private fun loadUserVideos() {
        viewModel.userVideos.value?.let { videos ->
            videoAdapter.updateVideos(videos)
        }
    }

    private fun loadLikedVideos() {
        viewModel.likedVideos.value?.let { videos ->
            videoAdapter.updateVideos(videos)
        }
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1000 -> "${count / 1000}.${(count % 1000) / 100}k"
            else -> count.toString()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = profile()
    }
}