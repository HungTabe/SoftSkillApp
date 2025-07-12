package fpt.edu.vn.softskillappv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import fpt.edu.vn.softskillappv2.data.repository.UserRepository
import fpt.edu.vn.softskillappv2.data.repository.VideoRepository
import fpt.edu.vn.softskillappv2.ui.auth.LoginActivity
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private val userRepository = UserRepository()
    private val videoRepository = VideoRepository()
    private val auth = FirebaseAuth.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Check if user is logged in
        if (auth.currentUser == null) {
            navigateToLogin()
            return
        }
        
        // Display user info
        displayUserInfo()
        
        // Set up logout button
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogout).setOnClickListener {
            logout()
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
    
    private fun displayUserInfo() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            Log.d("MainActivity", "Logged in user: ${user.displayName} (${user.email})")
            
            // Update UI with user info
            findViewById<android.widget.TextView>(R.id.tvUserInfo).text = 
                "Name: ${user.displayName ?: "Unknown"}\nEmail: ${user.email ?: "No email"}"
            
            Toast.makeText(this, "Welcome ${user.displayName}!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    fun logout() {
        // Sign out from Firebase
        auth.signOut()
        
        // Sign out from Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
        
        // Clear SharedPreferences
        SharedPrefsManager.clearUserData(this)
        
        // Navigate to login
        navigateToLogin()
    }
}