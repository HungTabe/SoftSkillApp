package fpt.edu.vn.softskillappv2.ui.assessment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.AssessmentQuestion
import fpt.edu.vn.softskillappv2.data.model.AssessmentResult
import fpt.edu.vn.softskillappv2.data.repository.AssessmentRepository
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager
import kotlinx.coroutines.launch
import java.util.UUID

class AssessmentActivity : AppCompatActivity() {
    
    private lateinit var tvQuestion: TextView
    private lateinit var tvProgress: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnNext: Button
    private lateinit var progressBar: ProgressBar
    
    private val assessmentRepository = AssessmentRepository()
    private lateinit var sharedPrefsManager: SharedPrefsManager
    
    private var questions: List<AssessmentQuestion> = emptyList()
    private var currentQuestionIndex = 0
    private val userAnswers = mutableMapOf<Int, Int>() // questionId to selectedOptionIndex
    private var userId: Int = 0
    
    companion object {
        const val EXTRA_USER_ID = "user_id"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
        
        initViews()
        setupSharedPrefs()
        loadUserId()
        loadAssessmentQuestions()
    }
    
    private fun initViews() {
        tvQuestion = findViewById(R.id.tvQuestion)
        tvProgress = findViewById(R.id.tvProgress)
        radioGroup = findViewById(R.id.radioGroup)
        btnNext = findViewById(R.id.btnNext)
        progressBar = findViewById(R.id.progressBar)
        
        btnNext.setOnClickListener {
            handleNextButton()
        }
    }
    
    private fun setupSharedPrefs() {
        sharedPrefsManager = SharedPrefsManager(this)
    }
    
    private fun loadUserId() {
        userId = intent.getIntExtra(EXTRA_USER_ID, 0)
        if (userId == 0) {
            // Try to get from SharedPrefs
            val userEmail = sharedPrefsManager.getUserEmail()
            if (userEmail != null) {
                // For now, use a simple hash of email as userId
                userId = userEmail.hashCode()
            }
        }
    }
    
    private fun loadAssessmentQuestions() {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                val result = assessmentRepository.getAssessmentQuestions()
                
                if (result.isSuccess) {
                    questions = result.getOrNull() ?: emptyList()
                    if (questions.isNotEmpty()) {
                        displayCurrentQuestion()
                    } else {
                        showError("Không có câu hỏi assessment nào")
                    }
                } else {
                    showError("Lỗi khi tải câu hỏi: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                showError("Lỗi: ${e.message}")
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
    
    private fun displayCurrentQuestion() {
        if (currentQuestionIndex >= questions.size) {
            finishAssessment()
            return
        }
        
        val question = questions[currentQuestionIndex]
        tvQuestion.text = question.question
        tvProgress.text = "${currentQuestionIndex + 1}/${questions.size}"
        
        // Clear previous radio buttons
        radioGroup.removeAllViews()
        
        // Add radio buttons for options
        question.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(this)
            radioButton.id = index
            radioButton.text = option
            radioGroup.addView(radioButton)
        }
        
        // Update button text
        btnNext.text = if (currentQuestionIndex == questions.size - 1) "Hoàn thành" else "Tiếp theo"
    }
    
    private fun handleNextButton() {
        val selectedId = radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Vui lòng chọn một đáp án", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Save user answer
        val currentQuestion = questions[currentQuestionIndex]
        userAnswers[currentQuestion.id] = selectedId
        
        currentQuestionIndex++
        
        if (currentQuestionIndex < questions.size) {
            displayCurrentQuestion()
        } else {
            finishAssessment()
        }
    }
    
    private fun finishAssessment() {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                
                // Calculate results and save to database
                val results = calculateResults()
                val saveResult = assessmentRepository.saveAssessmentResult(results)
                
                if (saveResult.isSuccess) {
                    // Navigate to result screen
                    val intent = Intent(this@AssessmentActivity, AssessmentResultActivity::class.java).apply {
                        putExtra(AssessmentResultActivity.EXTRA_USER_ID, userId)
                        putExtra(AssessmentResultActivity.EXTRA_TOTAL_SCORE, results.score)
                        putExtra(AssessmentResultActivity.EXTRA_TOTAL_QUESTIONS, questions.size)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    showError("Lỗi khi lưu kết quả: ${saveResult.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                showError("Lỗi: ${e.message}")
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
    
    private fun calculateResults(): AssessmentResult {
        var totalScore = 0
        var correctAnswers = 0
        
        questions.forEach { question ->
            val userAnswerIndex = userAnswers[question.id] ?: -1
            if (userAnswerIndex >= 0 && userAnswerIndex < question.optionScores.size) {
                val score = question.optionScores[userAnswerIndex]
                totalScore += score
                
                // Consider answer correct if score > 0 (you can adjust this logic)
                if (score > 0) {
                    correctAnswers++
                }
            }
        }
        
        return AssessmentResult(
            userId = userId,
            questionId = 0, // We'll use 0 to indicate this is a summary result
            userAnswer = correctAnswers,
            isCorrect = correctAnswers > questions.size / 2,
            score = totalScore,
            completedAt = System.currentTimeMillis()
        )
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.e("AssessmentActivity", message)
    }
} 