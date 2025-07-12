package fpt.edu.vn.softskillappv2.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import fpt.edu.vn.softskillappv2.MainActivity
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.SoftSkillApplication

class SplashActivity : AppCompatActivity() {
    
    private lateinit var logoImageView: ImageView
    private val SPLASH_DELAY = 2000L // 2 giây
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        logoImageView = findViewById(R.id.ivLogo)
        
        // Tạo animation cho logo
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        logoImageView.startAnimation(logoAnimation)
        
        // Chuyển hướng sau delay với logic kiểm tra đăng nhập
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, SPLASH_DELAY)
    }
    
    private fun navigateToNextScreen() {
        val sharedPrefsManager = (application as SoftSkillApplication).sharedPrefsManager
        
        val intent = if (sharedPrefsManager.isLoggedIn()) {
            // Nếu đã đăng nhập, chuyển đến MainActivity
            Intent(this, MainActivity::class.java)
        } else {
            // Nếu chưa đăng nhập, có thể chuyển đến LoginActivity (nếu có)
            // Hoặc vẫn chuyển đến MainActivity để test
            Intent(this, MainActivity::class.java)
        }
        
        startActivity(intent)
        finish()
    }
} 