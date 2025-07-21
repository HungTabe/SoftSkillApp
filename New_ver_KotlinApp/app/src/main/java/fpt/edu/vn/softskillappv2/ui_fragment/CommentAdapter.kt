package fpt.edu.vn.softskillappv2.ui_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Comment
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(
    private val comments: List<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivProfilePicture: ImageView = itemView.findViewById(R.id.ivProfilePicture)
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        private val tvCommentText: TextView = itemView.findViewById(R.id.tvCommentText)

        fun bind(comment: Comment) {
            // Set username based on userId
            val username = when (comment.userId) {
                101 -> "Sarah Johnson"
                102 -> "Michael Chen"
                103 -> "Emily Davis"
                1 -> "You"
                else -> "User ${comment.userId}"
            }
            tvUsername.text = username
            
            // Set comment text
            tvCommentText.text = comment.text
            
            // Set timestamp
            comment.timestamp?.let { timestamp ->
                tvTimestamp.text = formatTimestamp(timestamp)
            } ?: run {
                tvTimestamp.text = "Just now"
            }
            
            // Set profile picture (you can load from URL if needed)
            ivProfilePicture.setImageResource(R.drawable.baseline_person_24)
        }

        private fun formatTimestamp(timestamp: Long): String {
            val currentTime = System.currentTimeMillis()
            val diff = currentTime - timestamp
            
            return when {
                diff < 60000 -> "Just now"
                diff < 3600000 -> "${diff / 60000} minutes ago"
                diff < 86400000 -> "${diff / 3600000} hours ago"
                diff < 2592000000 -> "${diff / 86400000} days ago"
                else -> {
                    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    dateFormat.format(Date(timestamp))
                }
            }
        }
    }
} 