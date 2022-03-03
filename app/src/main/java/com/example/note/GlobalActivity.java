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

public class GlobalActivity extends AppCompatActivity {

    Button logout,yournotesglobal,favoritesglobal;
    RecyclerView recyclerView;
    ArrayList<Note> dataholder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        logout = findViewById(R.id.logoutglobal);
        yournotesglobal = findViewById(R.id.yournotesglobal);
        favoritesglobal = findViewById(R.id.favoritenotesglobal);
        final SessionManagement sessionManagement = new SessionManagement(GlobalActivity.this);

        recyclerView = findViewById(R.id.recyclerglobal);

        // Store all private notes in cursor ------------------------------------------------------
        Cursor cursor = new DBHelper(this).readGlobalNotes(sessionManagement.getSession());

        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5 ));
            dataholder.add(note);
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataholder);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManagement.removeSession();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        yournotesglobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        favoritesglobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                startActivity(intent);
            }
        });
    }

}
