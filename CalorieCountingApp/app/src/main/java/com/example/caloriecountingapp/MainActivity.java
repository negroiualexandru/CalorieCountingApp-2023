package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    Button toSignUp;
    Button login;
    Database db;
    TextView username, password;

    public String usernameUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toSignUp = (Button) findViewById(R.id.toSignUP);
        login = (Button) findViewById(R.id.login);
        db = Database.getInstance(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.usernameAttempt);
                password = findViewById(R.id.passwordAttempt);

                Cursor result = db.getPassword(username.getText().toString());
                if (result.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Incorrect Username",
                            Toast.LENGTH_SHORT).show();
                            return;
                }

                //Need to move cursor to the first row in the results
                result.moveToNext();
                String corrPass = result.getString(0);
                if(!(corrPass.equals(password.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                usernameUsed = username.getText().toString();
                openHomePage();
            }
        });

        //on the click of button send user to sign up page
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();
            }
        });
    }

    //opens sign up page
    public void openSignUpPage() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    //opens home page after successful login
    public void openHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        Bundle passUsername = new Bundle();
        passUsername.putString("usernameUsed", usernameUsed);
        intent.putExtras(passUsername);
        startActivity(intent);
    }
}