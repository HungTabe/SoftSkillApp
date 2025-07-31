package fpt.edu.vn.softskillappv2.ui_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.LocalNote
import fpt.edu.vn.softskillappv2.util.SharedPrefsManager
import java.text.SimpleDateFormat
import java.util.*

class discover : Fragment() {
    private lateinit var etNoteInput: EditText
    private lateinit var btnGetTime: Button
    private lateinit var btnSaveNote: Button
    private lateinit var rvNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var sharedPrefsManager: SharedPrefsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_discover, container, false)
        
        // Initialize views
        etNoteInput = view.findViewById(R.id.etNoteInput)
        btnGetTime = view.findViewById(R.id.btnGetTime)
        btnSaveNote = view.findViewById(R.id.btnSaveNote)
        rvNotes = view.findViewById(R.id.rvNotes)
        
        // Initialize SharedPrefsManager
        sharedPrefsManager = SharedPrefsManager(requireContext())
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Setup click listeners
        setupClickListeners()
        
        // Load existing notes
        loadNotes()
        
        return view
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(emptyList()) { note ->
            // Handle note click - could be used for editing or viewing details
            Toast.makeText(context, "Note: ${note.content}", Toast.LENGTH_SHORT).show()
        }
        
        rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
    }

    private fun setupClickListeners() {
        btnGetTime.setOnClickListener {
            insertCurrentTime()
        }
        
        btnSaveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun insertCurrentTime() {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val timeString = dateFormat.format(Date(currentTime))
        
        val currentText = etNoteInput.text.toString()
        val cursorPosition = etNoteInput.selectionStart
        
        val newText = if (currentText.isEmpty()) {
            "[$timeString] "
        } else {
            val beforeCursor = currentText.substring(0, cursorPosition)
            val afterCursor = currentText.substring(cursorPosition)
            "$beforeCursor[$timeString] $afterCursor"
        }
        
        etNoteInput.setText(newText)
        etNoteInput.setSelection(cursorPosition + timeString.length + 3) // +3 for "[ ] "
    }

    private fun saveNote() {
        val noteContent = etNoteInput.text.toString().trim()
        
        if (noteContent.isEmpty()) {
            Toast.makeText(context, "Please enter some content for your note", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Extract timestamp from note content if it exists
        val timestamp = extractTimestampFromContent(noteContent)
        
        val note = LocalNote(
            id = UUID.randomUUID().toString(),
            content = noteContent,
            timestamp = timestamp
        )
        
        sharedPrefsManager.saveNote(note)
        
        // Clear input
        etNoteInput.text.clear()
        
        // Reload notes
        loadNotes()
        
        Toast.makeText(context, "Note saved successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun extractTimestampFromContent(content: String): Long {
        // Look for timestamp pattern [dd/MM/yyyy HH:mm:ss]
        val timestampPattern = Regex("\\[(\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2})\\]")
        val matchResult = timestampPattern.find(content)
        
        return if (matchResult != null) {
            try {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val date = dateFormat.parse(matchResult.groupValues[1])
                date?.time ?: 0L
            } catch (e: Exception) {
                0L
            }
        } else {
            0L
        }
    }

    private fun loadNotes() {
        val notes = sharedPrefsManager.getNotes().sortedByDescending { it.createdAt }
        noteAdapter.updateNotes(notes)
    }
}