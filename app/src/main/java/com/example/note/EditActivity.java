package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    String title, body, favourite, visibility, user, date, newtitle, newbody, newfavourite, newvisibility;

    EditText edittitle, editbody;
    TextView createdon;
    CheckBox editfavourite;
    RadioButton editradioprivate, editradiopublic;
    Button updatenote,deletenote,cancel;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        DB = new DBHelper(this);

        // Get Note object that got clicked as well as the activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Note note = extras.getParcelable("note");
        final String activity = extras.getString("from");

        // Old values ---------
        title = note.getTitle();
        body = note.getBody();
        favourite = note.getFavourite();
        visibility = note.getVisibility();
        user = note.getUser();
        date = note.getDate();
        updatenote = findViewById(R.id.updatenote);
        deletenote = findViewById(R.id.deletenote);
        cancel = findViewById(R.id.cancel);

        // Initialize new variables in case user doesn't change them
        newfavourite = favourite;
        newvisibility = visibility;

        // Get the views from activity layout ---
        edittitle = findViewById(R.id.edittitle);
        editbody = findViewById(R.id.editbody);
        editfavourite = findViewById(R.id.editcheckfavourite);
        editradioprivate = findViewById(R.id.editradioprivate);
        editradiopublic = findViewById(R.id.editradiopublic);
        createdon = findViewById(R.id.createdon);

        fillData(edittitle, editbody, editfavourite, editradioprivate, editradiopublic,createdon);

        updatenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // New values ----------------------------
                newtitle = edittitle.getText().toString();
                newbody = editbody.getText().toString();
                if (editfavourite.isChecked()) {
                    newfavourite = "yes";
                } else {
                    newfavourite = "no";
                }
                if (editradioprivate.isChecked()) {
                    newvisibility = "private";
                } else {
                    newvisibility = "public";
                }

                // Update database values --------------------------------------------------------------------------------------------------------
                Boolean update = DB.editNoteData(title, newtitle, body, newbody, favourite, newfavourite, visibility, newvisibility, user, date );
                if (update) {
                    Toast.makeText(EditActivity.this, "Note updated successfully!", Toast.LENGTH_SHORT).show();

                    if (activity.equals("home")) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(EditActivity.this, "Note could not be updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deletenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Delete database row ---------------------------------------------------------
                Boolean delete = DB.deleteNote(title, body, favourite, visibility, user, date );

                if (delete) {
                    Toast.makeText(EditActivity.this, "Note deleted successfully!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditActivity.this, "Could not delete note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void fillData (EditText ftitle, EditText fbody, CheckBox fav, RadioButton rprivate, RadioButton rpublic, TextView created) {
        ftitle.setText(title);
        fbody.setText(body);
        created.setText(date);
        if (favourite.equals("yes")) {
            fav.setChecked(true);
        }
        if (visibility.equals("private")) {
            rprivate.setChecked(true);
        } else {
            rpublic.setChecked(true);
        }
    }
}