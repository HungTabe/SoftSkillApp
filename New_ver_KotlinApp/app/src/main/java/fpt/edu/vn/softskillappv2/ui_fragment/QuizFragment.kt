package fpt.edu.vn.softskillappv2.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.Quiz
import fpt.edu.vn.softskillappv2.data.model.QuizResult
import fpt.edu.vn.softskillappv2.data.model.Video
import fpt.edu.vn.softskillappv2.data.repository.QuizRepository
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {
    
    private lateinit var quizRepository: QuizRepository
    private lateinit var currentVideo: Video
    private var currentQuizIndex = 0
    private var quizzes: List<Quiz> = emptyList()
    private var userAnswers: MutableMap<Int, Int> = mutableMapOf()
    private var totalScore = 0
    
    // UI Components
    private lateinit var tvQuestion: TextView
    private lateinit var tvProgress: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnNext: MaterialButton
    private lateinit var btnPrevious: MaterialButton
    private lateinit var btnSubmit: MaterialButton
    private lateinit var btnBack: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvVideoTitle: TextView
    
    companion object {
        private const val ARG_VIDEO = "video"
        
        fun newInstance(video: Video): QuizFragment {
            return QuizFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_VIDEO, video)
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizRepository = QuizRepository()
        
        // Get video from arguments or create default
        currentVideo = arguments?.getParcelable(ARG_VIDEO) ?: Video(
            id = 0,
            title = "Unknown Video",
            youtube_url = "",
            category = "Unknown"
        )
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initializeViews(view)
        loadQuizzes()
    }
    
    override fun onResume() {
        super.onResume()
        // Handle hardware back button
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBackToHome()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    
    private fun initializeViews(view: View) {
        tvQuestion = view.findViewById(R.id.tvQuestion)
        tvProgress = view.findViewById(R.id.tvProgress)
        radioGroup = view.findViewById(R.id.radioGroup)
        btnNext = view.findViewById(R.id.btnNext)
        btnPrevious = view.findViewById(R.id.btnPrevious)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        btnBack = view.findViewById(R.id.btnBack)
        progressBar = view.findViewById(R.id.progressBar)
        tvVideoTitle = view.findViewById(R.id.tvVideoTitle)
        
        tvVideoTitle.text = "Quiz: ${currentVideo.title}"
        
        btnNext.setOnClickListener { nextQuestion() }
        btnPrevious.setOnClickListener { previousQuestion() }
        btnSubmit.setOnClickListener { submitQuiz() }
        btnBack.setOnClickListener { goBackToHome() }
        
        // Initially hide submit button
        btnSubmit.visibility = View.GONE
    }
    
    private fun loadQuizzes() {
        lifecycleScope.launch {
            try {
                // Check if video ID is valid
                if (currentVideo.id <= 0) {
                    showError("Invalid video ID")
                    return@launch
                }
                
                val result = quizRepository.getQuizzesByVideoId(currentVideo.id)
                if (result.isSuccess) {
                    quizzes = result.getOrNull() ?: emptyList()
                    if (quizzes.isNotEmpty()) {
                        displayCurrentQuestion()
                    } else {
                        showNoQuizzesMessage()
                    }
                } else {
                    showError("Failed to load quizzes: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                showError("Error loading quizzes: ${e.message}")
            }
        }
    }
    
    private fun displayCurrentQuestion() {
        if (currentQuizIndex >= quizzes.size) return
        
        val quiz = quizzes[currentQuizIndex]
        
        // Update progress
        tvProgress.text = "Question ${currentQuizIndex + 1} of ${quizzes.size}"
        progressBar.progress = ((currentQuizIndex + 1) * 100 / quizzes.size)
        
        // Set question
        tvQuestion.text = quiz.question
        
        // Clear previous options
        radioGroup.removeAllViews()
        
        // Add options
        quiz.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(requireContext()).apply {
                id = index
                text = option
                textSize = 16f
                setPadding(32, 16, 32, 16)
            }
            radioGroup.addView(radioButton)
        }
        
        // Restore user's previous answer if exists
        userAnswers[currentQuizIndex]?.let { answer ->
            radioGroup.check(answer)
        }
        
        // Update button states
        updateButtonStates()
    }
    
    private fun updateButtonStates() {
        btnPrevious.isEnabled = currentQuizIndex > 0
        btnNext.visibility = if (currentQuizIndex < quizzes.size - 1) View.VISIBLE else View.GONE
        btnSubmit.visibility = if (currentQuizIndex == quizzes.size - 1) View.VISIBLE else View.GONE
    }
    
    private fun nextQuestion() {
        saveCurrentAnswer()
        if (currentQuizIndex < quizzes.size - 1) {
            currentQuizIndex++
            displayCurrentQuestion()
        }
    }
    
    private fun previousQuestion() {
        saveCurrentAnswer()
        if (currentQuizIndex > 0) {
            currentQuizIndex--
            displayCurrentQuestion()
        }
    }
    
    private fun saveCurrentAnswer() {
        val selectedId = radioGroup.checkedRadioButtonId
        if (selectedId != -1) {
            userAnswers[currentQuizIndex] = selectedId
        }
    }
    
    private fun submitQuiz() {
        saveCurrentAnswer()
        
        // Check if all questions are answered
        if (userAnswers.size < quizzes.size) {
            Toast.makeText(requireContext(), "Please answer all questions", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Calculate score
        calculateScore()
        
        // Save results to Supabase
        saveQuizResults()
    }
    
    private fun calculateScore() {
        totalScore = 0
        quizzes.forEachIndexed { index, quiz ->
            val userAnswer = userAnswers[index] ?: -1
            val isCorrect = userAnswer == quiz.correct_answer
            if (isCorrect) {
                totalScore += 100 / quizzes.size
            }
        }
    }
    
    private fun saveQuizResults() {
        lifecycleScope.launch {
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userId = currentUser?.email?.hashCode() ?: 0
                
                // Save individual quiz results
                val results = mutableListOf<QuizResult>()
                quizzes.forEachIndexed { index, quiz ->
                    val userAnswer = userAnswers[index] ?: -1
                    val isCorrect = userAnswer == quiz.correct_answer
                    
                    val quizResult = QuizResult(
                        quiz_id = quiz.id,
                        user_id = userId,
                        user_answer = userAnswer,
                        is_correct = isCorrect,
                        score = if (isCorrect) 100 / quizzes.size else 0,
                        completed_at = System.currentTimeMillis()
                    )
                    results.add(quizResult)
                }
                
                // Save each result
                var allSaved = true
                results.forEach { result ->
                    val saveResult = quizRepository.saveQuizResult(result)
                    if (saveResult.isFailure) {
                        allSaved = false
                    }
                }
                
                if (allSaved) {
                    showQuizResults()
                } else {
                    showError("Save quiz results successfully")
                }
                
            } catch (e: Exception) {
                showError("Error saving quiz results: ${e.message}")
            }
        }
    }
    
    private fun showQuizResults() {
        val scorePercentage = totalScore
        val message = when {
            scorePercentage >= 80 -> "Excellent! You scored $scorePercentage%"
            scorePercentage >= 60 -> "Good job! You scored $scorePercentage%"
            scorePercentage >= 40 -> "Not bad! You scored $scorePercentage%"
            else -> "Keep practicing! You scored $scorePercentage%"
        }
        
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        
        // Navigate back to home fragment
        requireActivity().supportFragmentManager.popBackStack()
    }
    
    private fun showNoQuizzesMessage() {
        tvQuestion.text = "No quizzes available for this video yet."
        btnNext.visibility = View.GONE
        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        radioGroup.visibility = View.GONE
        // Keep back button visible so user can return to home
    }
    
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
    
    private fun goBackToHome() {
        // Check if user has started the quiz and show confirmation dialog
        if (userAnswers.isNotEmpty()) {
            showExitConfirmationDialog()
        } else {
            // If no answers given, go back directly
            navigateBackToHome()
        }
    }
    
    private fun showExitConfirmationDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Exit Quiz")
            .setMessage("Are you sure you want to exit? Your progress will be lost.")
            .setPositiveButton("Exit") { _, _ ->
                navigateBackToHome()
            }
            .setNegativeButton("Continue Quiz") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }
    
    private fun navigateBackToHome() {
        // Pop back to home fragment
        requireActivity().supportFragmentManager.popBackStack()
    }
} 