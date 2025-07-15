package fpt.edu.vn.softskillappv2.ui_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Video

class VideoAdapter(
    private val videos: List<Video>,
    private val onLikeClick: (Video) -> Unit,
    private val onShareClick: (Video) -> Unit,
    private val onQuizzClick: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val webView: WebView = itemView.findViewById(R.id.webView)
        private val btnLike: ImageButton = itemView.findViewById(R.id.btnLike)
        private val btnShare: ImageButton = itemView.findViewById(R.id.btnShare)
        private val tvQuizz: TextView = itemView.findViewById(R.id.tvQuizz)
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvHashtags: TextView = itemView.findViewById(R.id.tvHashtags)
        // Có thể bổ sung thêm các view khác nếu cần

        @SuppressLint("SetJavaScriptEnabled")
        fun bind(video: Video) {
            // Embed YouTube video
            val youtubeId = extractYoutubeId(video.youtube_url)
            val html = """
                <html><body style='margin:0;'>
                <iframe width='100%' height='100%' src='https://www.youtube.com/embed/$youtubeId?autoplay=0&controls=1' frameborder='0' allowfullscreen></iframe>
                </body></html>
            """.trimIndent()
            webView.settings.javaScriptEnabled = true
            webView.settings.pluginState = WebSettings.PluginState.ON
            webView.webViewClient = WebViewClient()
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)

            // Bind info
            tvUsername.text = video.uploader_id?.toString() ?: "Unknown"
            tvDescription.text = video.title
            tvHashtags.text = "#${video.category}"

            // Nút Quizz, Like, Share
            tvQuizz.setOnClickListener { onQuizzClick(video) }
            btnLike.setOnClickListener { onLikeClick(video) }
            btnShare.setOnClickListener { onShareClick(video) }
        }

        // Helper để lấy YouTube video ID từ URL
        private fun extractYoutubeId(url: String): String? {
            val regex = Regex("(?:v=|be/|embed/)([a-zA-Z0-9_-]{11})")
            val match = regex.find(url)
            return match?.groups?.get(1)?.value ?: url.takeLast(11)
        }
    }
}