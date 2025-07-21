package fpt.edu.vn.softskillappv2.ui_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Video

class ProfileVideoAdapter(
    private var videos: List<Video>,
    private val onVideoClick: (Video) -> Unit
) : RecyclerView.Adapter<ProfileVideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImage: ImageView = itemView.findViewById(R.id.videoThumbnail)
        val categoryText: TextView = itemView.findViewById(R.id.videoCategory)
        val likesText: TextView = itemView.findViewById(R.id.videoLikes)
        val heartIcon: ImageView = itemView.findViewById(R.id.heartIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        
        // Set category text
        holder.categoryText.text = video.category
        
        // Set likes count with formatting
        val likesText = when {
            video.likesCount >= 1000 -> "${video.likesCount / 1000}.${(video.likesCount % 1000) / 100}k"
            else -> video.likesCount.toString()
        }
        holder.likesText.text = likesText
        
        // Set heart icon color based on like status
        if (video.isLiked) {
            holder.heartIcon.setColorFilter(
                holder.itemView.context.getColor(R.color.red)
            )
        } else {
            holder.heartIcon.setColorFilter(
                holder.itemView.context.getColor(R.color.white)
            )
        }
        
        // Set click listener
        holder.itemView.setOnClickListener {
            onVideoClick(video)
        }
        
        // For now, we'll use a placeholder image
        // In a real app, you would load the thumbnail using Glide or Picasso
        holder.thumbnailImage.setImageResource(R.drawable.baseline_assignment_48)
    }

    override fun getItemCount(): Int = videos.size

    fun updateVideos(newVideos: List<Video>) {
        videos = newVideos
        notifyDataSetChanged()
    }
} 