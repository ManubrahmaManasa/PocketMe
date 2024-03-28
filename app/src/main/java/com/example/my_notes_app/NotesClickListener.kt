package com.example.my_notes_app

import androidx.cardview.widget.CardView
import com.example.my_notes_app.Models.Notes

interface NotesClickListener {
    fun onClick(notes: Notes?)
    fun onLongClick(notes: Notes?, cardView: CardView?)
}
