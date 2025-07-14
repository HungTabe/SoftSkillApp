package fpt.edu.vn.softskillappv2.ui.assessment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.MainActivity

class AssessmentResultActivity : AppCompatActivity() {
    
    private lateinit var tvScore: TextView
    private lateinit var tvLevel: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var tvRecommendation: TextView
    private lateinit var cardSocial: CardView
    private lateinit var cardNegotiation: CardView
    private lateinit var cardCommunication: CardView
    private lateinit var btnBackToHome: Button
    
    private var totalScore = 0
    private var totalQuestions = 0
    private var userId = 0
    
    companion object {
        const val EXTRA_USER_ID = "user_id"
        const val EXTRA_TOTAL_SCORE = "total_score"
        const val EXTRA_TOTAL_QUESTIONS = "total_questions"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment_result)
        
        initViews()
        loadIntentData()
        displayResults()
        setupRecommendations()
    }
    
    private fun initViews() {
        tvScore = findViewById(R.id.tvScore)
        tvLevel = findViewById(R.id.tvLevel)
        tvFeedback = findViewById(R.id.tvFeedback)
        tvRecommendation = findViewById(R.id.tvRecommendation)
        cardSocial = findViewById(R.id.cardSocial)
        cardNegotiation = findViewById(R.id.cardNegotiation)
        cardCommunication = findViewById(R.id.cardCommunication)
        btnBackToHome = findViewById(R.id.btnBackToHome)
        
        btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
    
    private fun loadIntentData() {
        totalScore = intent.getIntExtra(EXTRA_TOTAL_SCORE, 0)
        totalQuestions = intent.getIntExtra(EXTRA_TOTAL_QUESTIONS, 0)
        userId = intent.getIntExtra(EXTRA_USER_ID, 0)
    }
    
    private fun displayResults() {
        val percentage = if (totalQuestions > 0) {
            (totalScore.toFloat() / (totalQuestions * 5) * 100).toInt() // Assuming max score per question is 5
        } else {
            0
        }
        
        tvScore.text = "$totalScore điểm"
        
        val (level, feedback) = getLevelAndFeedback(percentage)
        tvLevel.text = level
        tvFeedback.text = feedback
    }
    
    private fun getLevelAndFeedback(percentage: Int): Pair<String, String> {
        return when {
            percentage >= 80 -> {
                Pair(
                    "Chuyên gia",
                    "Xuất sắc! Bạn có kỹ năng mềm rất tốt. Hãy tiếp tục phát triển và chia sẻ kiến thức với người khác."
                )
            }
            percentage >= 60 -> {
                Pair(
                    "Trung cấp",
                    "Tốt! Bạn có nền tảng kỹ năng mềm vững chắc. Hãy tập trung vào các lĩnh vực cần cải thiện."
                )
            }
            percentage >= 40 -> {
                Pair(
                    "Sơ cấp",
                    "Khá! Bạn đã có một số kỹ năng cơ bản. Hãy tiếp tục học hỏi và thực hành thường xuyên."
                )
            }
            else -> {
                Pair(
                    "Người mới bắt đầu",
                    "Đừng lo lắng! Mọi người đều bắt đầu từ đây. Hãy kiên nhẫn và thực hành từng bước một."
                )
            }
        }
    }
    
    private fun setupRecommendations() {
        val percentage = if (totalQuestions > 0) {
            (totalScore.toFloat() / (totalQuestions * 5) * 100).toInt()
        } else {
            0
        }
        
        val recommendations = getRecommendations(percentage)
        tvRecommendation.text = recommendations
        
        // Setup category cards with click listeners
        setupCategoryCard(cardSocial, "Kỹ năng xã hội", "social")
        setupCategoryCard(cardNegotiation, "Kỹ năng đàm phán", "negotiation")
        setupCategoryCard(cardCommunication, "Giao tiếp văn phòng", "office_communication")
    }
    
    private fun getRecommendations(percentage: Int): String {
        return when {
            percentage >= 80 -> {
                "Khuyến nghị: Tập trung vào việc phát triển kỹ năng lãnh đạo và mentoring."
            }
            percentage >= 60 -> {
                "Khuyến nghị: Cải thiện kỹ năng giao tiếp và xây dựng mối quan hệ."
            }
            percentage >= 40 -> {
                "Khuyến nghị: Thực hành các kỹ năng cơ bản và tăng cường tự tin."
            }
            else -> {
                "Khuyến nghị: Bắt đầu với các khóa học cơ bản và thực hành thường xuyên."
            }
        }
    }
    
    private fun setupCategoryCard(card: CardView, title: String, category: String) {
        card.setOnClickListener {
            // Navigate to category-specific content
            // You can implement this based on your app's navigation structure
            // For now, we'll just show a toast
            android.widget.Toast.makeText(this, "Chuyển đến $title", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
} 