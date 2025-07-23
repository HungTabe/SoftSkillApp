package fpt.edu.vn.softskillappv2.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.databinding.ActivityMainBinding
import fpt.edu.vn.softskillappv2.ui_fragment.create
import fpt.edu.vn.softskillappv2.ui_fragment.discover
import fpt.edu.vn.softskillappv2.ui_fragment.home
import fpt.edu.vn.softskillappv2.ui_fragment.profile

class NavBarTestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nav_bar_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(home())
        bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(home())
                R.id.profile -> replaceFragment(profile())
                R.id.discover -> replaceFragment(discover())
                R.id.create -> replaceFragment(create())
                R.id.ask_ai -> {
                    val intent = Intent(this, AskAIActivity::class.java)
                    startActivity(intent)
                }
                else ->{}
            }

            true

        }



    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }

}