package fpt.edu.vn.softskillappv2.ui_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.data.model.LocalNote
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private var notes: List<LocalNote>,
    private val onNoteClick: (LocalNote) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val tvNoteContent: TextView = itemView.findViewById(R.id.tvNoteContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        
        // Format timestamp
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val timestampText = if (note.timestamp > 0) {
            "Timestamp: ${dateFormat.format(Date(note.timestamp))}"
        } else {
            "Created: ${dateFormat.format(Date(note.createdAt))}"
        }
        
        holder.tvTimestamp.text = timestampText
        holder.tvNoteContent.text = note.content
        
        holder.itemView.setOnClickListener {
            onNoteClick(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    fun updateNotes(newNotes: List<LocalNote>) {
        notes = newNotes
        notifyDataSetChanged()
    }
} 