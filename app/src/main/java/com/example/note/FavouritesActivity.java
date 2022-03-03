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

public class FavouritesActivity extends AppCompatActivity {

    Button addnote,logout,yournotes,globalnotesfavourites;
    RecyclerView recyclerView;
    ArrayList<Note> dataholder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        addnote = findViewById(R.id.addnotefavourites);
        logout = findViewById(R.id.logoutfavourites);
        globalnotesfavourites = findViewById(R.id.globalnotesfavourites);
        yournotes = findViewById(R.id.yournotes);
        final SessionManagement sessionManagement = new SessionManagement(FavouritesActivity.this);

        recyclerView = findViewById(R.id.recyclerfavourites);

        // Store all private notes in cursor -------------------------------------------------------
        Cursor cursor = new DBHelper(this).readFavouriteNotes(sessionManagement.getSession());

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
                intent.putExtra("camefrom", "favourites");
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

        yournotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        globalnotesfavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GlobalActivity.class);
                startActivity(intent);
            }
        });
    }
}