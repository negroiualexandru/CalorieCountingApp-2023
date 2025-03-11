package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
public class SignUp extends AppCompatActivity {

    EditText username, password, weight, height, level;
    RadioGroup genderSelector;
    RadioButton gender;
    Database db;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = Database.getInstance(getApplicationContext());

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        level = findViewById(R.id.activityLevel);

        signUpBtn = findViewById(R.id.signUp);
        genderSelector = (RadioGroup) findViewById(R.id.genderSelect);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Read and check username
                String un = username.getText().toString();
                if (un.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Username must be more than 8 chars",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //read and check password
                String pw = password.getText().toString();
                if (pw.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password must be more than 8 chars",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //read and check weight - UI forced it to be a number
                String weightData = weight.getText().toString();
                if(weightData.length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please fill in weight information",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Read and check height - the UI restricts it to a number already
                String heightData = height.getText().toString();
                if(heightData.length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please fill in height information",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Read and check level - UI restricts it to a number
                String levelData = level.getText().toString();
                if(levelData.length() <= 0 ||
                        Integer.parseInt(levelData) < 1 ||
                        Integer.parseInt(levelData) > 5){
                    Toast.makeText(getApplicationContext(), "Level must be between 1 and 5",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //get Gender selection
                int selection = genderSelector.getCheckedRadioButtonId();
                if(selection == -1)
                {
                    Toast.makeText(getApplicationContext(), "Please select a gender",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                gender = (RadioButton) findViewById(selection);
                String genderData = gender.getText().toString();

                //attempts to create an account but otherwise, catch exception and prompt new username
                try {
                    db.insertUserData(un, pw, Integer.parseInt(weightData),
                            Integer.parseInt(heightData), Integer.parseInt(levelData), genderData);
                    openLoginPage();
                }catch(android.database.sqlite.SQLiteConstraintException ce)
                {
                    Toast.makeText(getApplicationContext(), "UserName already exists.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //method that goes to login page
    public void openLoginPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}