package fpt.edu.vn.softskillappv2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import fpt.edu.vn.softskillappv2.data.repository.UserRepository
import fpt.edu.vn.softskillappv2.data.repository.VideoRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private val userRepository = UserRepository()
    private val videoRepository = VideoRepository()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Test Supabase connection
        testSupabaseConnection()

    }
    
    private fun testSupabaseConnection() {
        lifecycleScope.launch {
            try {
                // Test video repository
                val videosResult = videoRepository.getAllVideos()
                if (videosResult.isSuccess) {
                    val videos = videosResult.getOrNull()
                    Log.d("MainActivity", "✅ Supabase connection successful! Found ${videos?.size ?: 0} videos")
                    Toast.makeText(this@MainActivity, "✅ Supabase connected successfully! Found ${videos?.size ?: 0} videos", Toast.LENGTH_LONG).show()
                } else {
                    val error = videosResult.exceptionOrNull()
                    Log.e("MainActivity", "❌ Supabase connection failed: ${error?.message}")
                    Toast.makeText(this@MainActivity, "❌ Supabase connection failed: ${error?.message}", Toast.LENGTH_LONG).show()
                }
                
                // Test user repository
                val usersResult = userRepository.getTopUsers(5)
                if (usersResult.isSuccess) {
                    val users = usersResult.getOrNull()
                    Log.d("MainActivity", "✅ Users query successful! Found ${users?.size ?: 0} users")
                } else {
                    val error = usersResult.exceptionOrNull()
                    Log.e("MainActivity", "❌ Users query failed: ${error?.message}")
                }
                
            } catch (e: Exception) {
                Log.e("MainActivity", "❌ Error testing Supabase connection", e)
                Toast.makeText(this@MainActivity, "❌ Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}