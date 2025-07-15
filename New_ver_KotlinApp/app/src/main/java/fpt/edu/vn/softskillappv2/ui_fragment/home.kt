package fpt.edu.vn.softskillappv2.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Video
import fpt.edu.vn.softskillappv2.data.repository.VideoRepository
import kotlinx.coroutines.launch

class home : Fragment() {
    private lateinit var viewPager: ViewPager2
    private val videoRepository = VideoRepository()
    private var videoAdapter: VideoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.videoViewPager)
        loadVideos()
    }

    private fun loadVideos() {
        lifecycleScope.launch {
            val result = videoRepository.getAllVideos()
            if (result.isSuccess) {
                val videos = result.getOrNull() ?: emptyList()
                videoAdapter = VideoAdapter(
                    videos,
                    onLikeClick = { video ->
                        Toast.makeText(requireContext(), "Liked: ${'$'}{video.title}", Toast.LENGTH_SHORT).show()
                        // TODO: Gọi update likes nếu muốn
                    },
                    onShareClick = { video ->
                        Toast.makeText(requireContext(), "Share: ${'$'}{video.title}", Toast.LENGTH_SHORT).show()
                        // TODO: Thêm logic share
                    },
                    onQuizzClick = { video ->
                        Toast.makeText(requireContext(), "Quizz for: ${'$'}{video.title}", Toast.LENGTH_SHORT).show()
                        // TODO: Mở quiz liên quan video
                    }
                )
                viewPager.adapter = videoAdapter
            } else {
                Toast.makeText(requireContext(), "Không thể tải video", Toast.LENGTH_SHORT).show()
            }
        }
    }
}