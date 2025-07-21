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
        val intent = Intent(this, fpt.edu.vn.softskillappv2.ui.auth.LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
} 