package fpt.edu.vn.softskillappv2.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Comment
import fpt.edu.vn.softskillappv2.data.model.Video
import kotlinx.coroutines.launch
import java.util.*

class CommentFragment : Fragment() {
    private lateinit var rvComments: RecyclerView
    private lateinit var etComment: EditText
    private lateinit var btnSendComment: ImageButton
    private lateinit var btnBack: ImageButton
    private var commentAdapter: CommentAdapter? = null
    private var video: Video? = null
    private val comments = mutableListOf<Comment>()

    companion object {
        private const val ARG_VIDEO = "video"
        
        fun newInstance(video: Video): CommentFragment {
            val fragment = CommentFragment()
            val args = Bundle()
            args.putParcelable(ARG_VIDEO, video)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            video = it.getParcelable(ARG_VIDEO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupRecyclerView()
        loadComments()
        setupClickListeners()
    }

    private fun initViews(view: View) {
        rvComments = view.findViewById(R.id.rvComments)
        etComment = view.findViewById(R.id.etComment)
        btnSendComment = view.findViewById(R.id.btnSendComment)
        btnBack = view.findViewById(R.id.btnBack)
    }

    private fun setupRecyclerView() {
        rvComments.layoutManager = LinearLayoutManager(requireContext())
        commentAdapter = CommentAdapter(comments)
        rvComments.adapter = commentAdapter
    }

    private fun loadComments() {
        // Load sample comments for demonstration
        // In real app, you would load from database/API
        comments.clear()
        comments.addAll(getSampleComments())
        commentAdapter?.notifyDataSetChanged()
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnSendComment.setOnClickListener {
            val commentText = etComment.text.toString().trim()
            if (commentText.isNotEmpty()) {
                addComment(commentText)
                etComment.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter a comment", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addComment(commentText: String) {
        val newComment = Comment(
            id = comments.size + 1,
            videoId = video?.id ?: 0,
            userId = 1, // Current user ID
            text = commentText,
            timestamp = System.currentTimeMillis()
        )
        
        comments.add(0, newComment) // Add to top
        commentAdapter?.notifyItemInserted(0)
        rvComments.scrollToPosition(0)
        
        // In real app, you would save to database/API
        lifecycleScope.launch {
            // TODO: Save comment to database
            Toast.makeText(requireContext(), "Comment added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSampleComments(): List<Comment> {
        return listOf(
            Comment(
                id = 1,
                videoId = video?.id ?: 0,
                userId = 101,
                text = "This was really helpful! I learned a lot about active listening.",
                timestamp = System.currentTimeMillis() - (2 * 60 * 60 * 1000) // 2 hours ago
            ),
            Comment(
                id = 2,
                videoId = video?.id ?: 0,
                userId = 102,
                text = "Great explanation of the concepts. Looking forward to more content!",
                timestamp = System.currentTimeMillis() - (3 * 60 * 60 * 1000) // 3 hours ago
            ),
            Comment(
                id = 3,
                videoId = video?.id ?: 0,
                userId = 103,
                text = "The examples were very practical. I can use these tips in my daily work.",
                timestamp = System.currentTimeMillis() - (5 * 60 * 60 * 1000) // 5 hours ago
            )
        )
    }
} 