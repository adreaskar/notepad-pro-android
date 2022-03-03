package com.example.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Button addnote,logout,favouritenotes,globalnotes;
    RecyclerView recyclerView;
    ArrayList<Note> dataholder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addnote = findViewById(R.id.addnotehome);
        logout = findViewById(R.id.logout);
        globalnotes = findViewById(R.id.globalnotes);
        favouritenotes = findViewById(R.id.favouritenotes);
        final SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);

        recyclerView = findViewById(R.id.recyclerprivate);

        // Store all private notes in cursor -------------------------------------------------------
        Cursor cursor = new DBHelper(this).readPrivateNotes(sessionManagement.getSession());
        Cursor cursorFav = new DBHelper(this).readFavouriteNotes(sessionManagement.getSession());

        while (cursorFav.moveToNext()) {
            Note note = new Note(cursorFav.getString(0),cursorFav.getString(1),cursorFav.getString(2),cursorFav.getString(3),cursorFav.getString(4),cursorFav.getString(5 ));
            dataholder.add(note);
        }

        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5 ));
            dataholder.add(note);
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataholder);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                intent.putExtra("camefrom", "home");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManagement.removeSession();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        favouritenotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                startActivity(intent);
            }
        });

        globalnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GlobalActivity.class);
                startActivity(intent);
            }
        });
    }
}