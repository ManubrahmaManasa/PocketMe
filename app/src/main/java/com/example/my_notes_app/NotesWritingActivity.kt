package com.example.my_notes_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.my_notes_app.Models.Notes
import com.example.my_notes_app.databinding.ActivityNotesWritingBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotesWritingActivity:AppCompatActivity() {
    private lateinit var binding: ActivityNotesWritingBinding

    private lateinit var note: Notes
    private var isOldNote = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        note = Notes()
        try{
            note = intent.getSerializableExtra("old_data")as Notes
            note?.let {
                binding.title.setText(it.title)
                binding.notes.setText(it.notes)
                isOldNote = true
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        binding.save.setOnClickListener {
            val title_name = binding.title.text.toString()
            val description = binding.notes.text.toString()

            if (description.isEmpty()) {
                Toast.makeText(this@NotesWritingActivity, "Please write something!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a", Locale.getDefault())
            val date = Date()

            if (!isOldNote) {
                note = Notes()
            }

            note.title = title_name
            note.notes = description
            note.date = formatter.format(date)

            val intent = Intent()
            intent.putExtra("note", note)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}