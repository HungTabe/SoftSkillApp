package fpt.edu.vn.softskillappv2

import android.app.Application
// import com.google.android.gms.ads.MobileAds
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager

class SoftSkillApplication : Application() {
    
    lateinit var sharedPrefsManager: SharedPrefsManager
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize SharedPrefsManager
        sharedPrefsManager = SharedPrefsManager(this)
        
        // TODO: Initialize AdMob later when needed
        // MobileAds.initialize(this) { initializationStatus ->
        //     // AdMob initialization completed
        // }
    }
} 