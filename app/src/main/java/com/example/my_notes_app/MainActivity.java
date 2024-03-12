package com.example.my_notes_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.my_notes_app.Adapters.NotesListAdapter;
import com.example.my_notes_app.Database.RoomDB;
import com.example.my_notes_app.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;

    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton float_button;
    SearchView search;
    Notes selectedNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        float_button = findViewById(R.id.float_button);
        search = findViewById(R.id.searchview);

        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAll();

        updateRecycler(notes);


        float_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NotesWritingActivity.class);
                startActivityForResult(intent,100);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteration(newText);
                return true;
            }
        });
    }

    private void filteration(String newText) {
        List<Notes> filtered_list = new ArrayList<>();
        for(Notes singleNotes: notes){
            if(singleNotes.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singleNotes.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filtered_list.add(singleNotes);
            }
        }
        notesListAdapter.filterList(filtered_list);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            if(resultCode== Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
               Notes new_notes = (Notes) data.getSerializableExtra("note");
               database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
               notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter  = new NotesListAdapter(MainActivity.this,notes,notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this,NotesWritingActivity.class);
            intent.putExtra("old_data",notes);
            startActivityForResult(intent,101);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNotes = new Notes();
            selectedNotes = notes;
            showPopUp(cardView);

        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int id = item.getItemId();
        if(id== R.id.pin) {
            if (selectedNotes.isPinned()) {
                database.mainDAO().pin(selectedNotes.getID(), false);
                Toast.makeText(this, "Unpinned!", Toast.LENGTH_SHORT).show();
            } else {
                database.mainDAO().pin(selectedNotes.getID(), true);
                Toast.makeText(this, "Pinned!", Toast.LENGTH_SHORT).show();
            }
            notes.clear();
            notes.addAll(database.mainDAO().getAll());
            notesListAdapter.notifyDataSetChanged();
            return true;
        } else if (id== R.id.delete) {
            database.mainDAO().delete(selectedNotes);
            notes.remove(selectedNotes);
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Note Deleted!!!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}