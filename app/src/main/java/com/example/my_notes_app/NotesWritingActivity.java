package com.example.my_notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.my_notes_app.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesWritingActivity extends AppCompatActivity {

    EditText title,notes;
    ImageView save;

    Notes note;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_writing);

        save = findViewById(R.id.save);
        title = findViewById(R.id.title);
        notes = findViewById(R.id.notes);


        note = new Notes();
        try {
            note = (Notes) getIntent().getSerializableExtra("old_data");
            title.setText(note.getTitle());
            notes.setText(note.getNotes());
            isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_name = title.getText().toString();
                String decription = notes.getText().toString();

                if(decription.isEmpty()){
                    Toast.makeText(NotesWritingActivity.this, "Please write something!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if(!isOldNote) {
                    note = new Notes();
                }

                note.setTitle(title_name);
                note.setNotes(decription);
                note.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",note);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}