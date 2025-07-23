package fpt.edu.vn.softskillappv2.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fpt.edu.vn.softskillappv2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class AskAIActivity : AppCompatActivity() {
    private lateinit var etSituation: EditText
    private lateinit var btnAskAI: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvAIAnswer: TextView

    private val apiKey = "AIzaSyCf4j6O8b4drNGSkjwR2IUWN3RWOd00fsM"
    private val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_ai)

        etSituation = findViewById(R.id.etSituation)
        btnAskAI = findViewById(R.id.btnAskAI)
        progressBar = findViewById(R.id.progressBar)
        tvAIAnswer = findViewById(R.id.tvAIAnswer)

        btnAskAI.setOnClickListener {
            val situation = etSituation.text.toString().trim()
            if (situation.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tình huống giao tiếp!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            askGemini(situation)
        }
    }

    private fun askGemini(situation: String) {
        progressBar.visibility = View.VISIBLE
        tvAIAnswer.text = ""
        btnAskAI.isEnabled = false
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(apiUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true

                val jsonBody = JSONObject()
                val contents = JSONArray()
                val contentObj = JSONObject()
                val parts = JSONArray()
                val partObj = JSONObject()
                partObj.put("text", situation)
                parts.put(partObj)
                contentObj.put("parts", parts)
                contents.put(contentObj)
                jsonBody.put("contents", contents)

                val writer = OutputStreamWriter(conn.outputStream)
                writer.write(jsonBody.toString())
                writer.flush()
                writer.close()

                val responseCode = conn.responseCode
                val response = if (responseCode == 200) {
                    conn.inputStream.bufferedReader().use(BufferedReader::readText)
                } else {
                    conn.errorStream?.bufferedReader()?.use(BufferedReader::readText) ?: ""
                }
                conn.disconnect()

                val answer = if (responseCode == 200) {
                    // Parse Gemini response
                    val json = JSONObject(response)
                    val candidates = json.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val content = candidates.getJSONObject(0).optJSONObject("content")
                        val partsArr = content?.optJSONArray("parts")
                        if (partsArr != null && partsArr.length() > 0) {
                            partsArr.getJSONObject(0).optString("text", "")
                        } else "Không nhận được câu trả lời từ AI."
                    } else "Không nhận được câu trả lời từ AI."
                } else {
                    "Lỗi: $response"
                }

                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    btnAskAI.isEnabled = true
                    tvAIAnswer.text = answer
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    btnAskAI.isEnabled = true
                    tvAIAnswer.text = "Đã xảy ra lỗi: ${e.message}"
                }
            }
        }
    }
} 