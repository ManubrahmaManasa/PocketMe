package com.example.my_notes_app;

import androidx.cardview.widget.CardView;

import com.example.my_notes_app.Models.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);

}
