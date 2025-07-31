package fpt.edu.vn.softskillappv2.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import fpt.edu.vn.softskillappv2.MainActivity
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.User
import fpt.edu.vn.softskillappv2.data.repository.UserRepository
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userRepository: UserRepository
    
    // Activity result launcher for Google Sign-In
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.w("LoginActivity", "Google sign in failed", e)
                handleSignInError(e)
            }
        } else {
            Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        
        // Initialize UserRepository
        userRepository = UserRepository()
        
        // Configure Google Sign-In
        setupGoogleSignIn()
        
        // Check if user is already signed in
        checkCurrentUser()
        
        // Set up click listeners
        setupClickListeners()
    }
    
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    
    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToMain()
        }
    }
    
    private fun setupClickListeners() {
        findViewById<com.google.android.gms.common.SignInButton>(R.id.btnGoogleSignIn).setOnClickListener {
            signIn()
        }
    }
    
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }
    
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let { firebaseUser ->
                        Log.d("LoginActivity", "signInWithCredential:success")
                        saveUserToSupabase(firebaseUser)
                    }
                } else {
                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        this, 
                        "Authentication failed: ${task.exception?.message}", 
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
    
    private fun handleSignInError(e: ApiException) {
        Log.e("LoginActivity", "Google Sign-In Error: Status Code = ${e.statusCode}, Message = ${e.message}")
        
        val errorMessage = when (e.statusCode) {
            10 -> "Developer error: Check SHA-1 fingerprint and package name in Firebase Console"
            12501 -> "Sign in cancelled by user"
            12500 -> "Sign in failed: Check your internet connection"
            12502 -> "Sign in failed: Network error"
            12506 -> "Sign in failed: Invalid account"
            12507 -> "Sign in failed: Invalid client ID"
            12508 -> "Sign in failed: Invalid client secret"
            else -> "Google Sign-In failed (Code: ${e.statusCode}): ${e.message}"
        }
        
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        
        // Log additional debug info
        Log.d("LoginActivity", "Package name: ${packageName}")
        Log.d("LoginActivity", "Client ID: ${getString(R.string.default_web_client_id)}")
    }
    
    private fun saveUserToSupabase(firebaseUser: com.google.firebase.auth.FirebaseUser) {
        lifecycleScope.launch {
            try {
                val user = User(
                    email = firebaseUser.email ?: "",
                    points = 0,
                    badges = emptyList()
                )

                val savedUser = userRepository.createOrUpdateUser(user)

                // Save user info to SharedPreferences
                SharedPrefsManager.saveUserEmail(this@LoginActivity, firebaseUser.email ?: "")
                SharedPrefsManager.saveUserId(this@LoginActivity, savedUser.id.toString())

                // Lưu URL ảnh đại diện Google (nếu có)
                val photoUrl = firebaseUser.photoUrl?.toString()
                if (photoUrl != null) {
                    SharedPrefsManager.saveUserAvatar(this@LoginActivity, photoUrl)
                }

                Toast.makeText(
                    this@LoginActivity, 
                    "Welcome ${firebaseUser.displayName ?: "User"}!", 
                    Toast.LENGTH_SHORT
                ).show()
                navigateToMain()

            } catch (e: Exception) {
                Log.e("LoginActivity", "Error saving user to Supabase", e)
                Toast.makeText(
                    this@LoginActivity, 
                    "Error saving user data: ${e.message}", 
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, fpt.edu.vn.softskillappv2.ui.NavBarTestActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToMain()
        }
    }
} 
