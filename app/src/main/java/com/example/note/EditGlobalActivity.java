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
import android.widget.TextView;

public class EditGlobalActivity extends AppCompatActivity {

    String title, body, user, date, currentuser;

    EditText edittitle, editbody;
    TextView byuser,ondate;
    Button cancelglobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_global);

        SessionManagement sessionManagement = new SessionManagement(EditGlobalActivity.this);

        Intent intent = getIntent();
        Note note = intent.getParcelableExtra("note");

        // Old values ----------
        title = note.getTitle();
        body = note.getBody();
        user = note.getUser();
        date = note.getDate();
        cancelglobal = findViewById(R.id.cancelglobal);
        currentuser = sessionManagement.getSession();

        // Get view elements from layout --------------
        edittitle = findViewById(R.id.edittitleglobal);
        editbody = findViewById(R.id.editbodyglobal);
        byuser = findViewById(R.id.byuser);
        ondate = findViewById(R.id.ondate);

        fillData(edittitle, editbody, byuser, ondate);

        cancelglobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void fillData (EditText ftitle, EditText fbody, TextView buser, TextView bdate) {
        ftitle.setText(title);
        ftitle.setEnabled(false);

        fbody.setText(body);
        fbody.setEnabled(false);

        if (currentuser.equals(user)) {
            buser.setText("by you");
        } else {
            buser.setText("by "+user);
        }

        bdate.setText("on "+date);
    }
}