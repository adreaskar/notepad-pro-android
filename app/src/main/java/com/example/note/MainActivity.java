package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password,email;
    Button register;
    TextView loginhere;
    DBHelper DB;

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.registerusername);
        password = findViewById(R.id.registerpassword);
        email = findViewById(R.id.registeremail);
        register = findViewById(R.id.register);
        loginhere = findViewById(R.id.loginhere);
        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();

                // Check if all fields are filled --------------------------------------------------
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(mail)) {
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuser = DB.checkUsername(user);

                    // If username doesn't exist
                    if (!checkuser) {
                        Boolean insert = DB.insertUserData(user,pass,mail);

                        // Success database insertion
                        if (insert) {
                            Toast.makeText(MainActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void loginHere(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        String sessionUser = sessionManagement.getSession();

        if (sessionUser != "no") {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else {
            //do nothing
        }
    }
}