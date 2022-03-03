package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    EditText title,body;
    Button addnote;
    String user, visibility = "private", favourite = "no";
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        SessionManagement sessionManagement = new SessionManagement(AddNoteActivity.this);
        DB = new DBHelper(this);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        addnote = findViewById(R.id.addnote);
        user = sessionManagement.getSession();

        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = title.getText().toString();
                String noteBody = body.getText().toString();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String strDate = sdfDate.format(now);

                Boolean insert = DB.insertNoteData(noteTitle, noteBody, favourite, visibility, user, strDate );
                if (insert) {
                    Toast.makeText(AddNoteActivity.this, "Note saved successfully!", Toast.LENGTH_SHORT).show();

                    Intent fromIntent = getIntent();
                    String cameFrom = fromIntent.getStringExtra("camefrom");

                    if (cameFrom.equals("home")) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                        startActivity(intent);
                    }


                } else {
                    Toast.makeText(AddNoteActivity.this, "Note could not be saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void checkCheckbox(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            favourite = "yes";
        } else {
            favourite = "no";
        }
    }

    public void checkRadio(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioprivate:
                if (checked)
                    visibility = "private";
                    break;
            case R.id.radiopublic:
                if (checked)
                    visibility = "public";
                    break;
        }
    }

}