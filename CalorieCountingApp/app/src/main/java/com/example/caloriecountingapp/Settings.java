package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    User user;
    TextView updateWeightField, updateHeightField, updateALField, updateGoalField;
    Button updateWeightButton, updateHeightButton, updateALButton, backToHome, darkTheme, lightTheme,
            updateGoalButton;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = Database.getInstance(getApplicationContext());

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        updateWeightField = findViewById(R.id.updateWeightField);
        updateHeightField = findViewById(R.id.updateHeightField);
        updateALField = findViewById(R.id.updateALField);

        updateWeightButton = findViewById(R.id.updateWeightButton);
        updateHeightButton = findViewById(R.id.updateHeightButton);
        updateALButton = findViewById(R.id.updateALButton);

        backToHome = findViewById(R.id.backToHome);

        darkTheme = findViewById(R.id.dark);
        lightTheme = findViewById(R.id.light);

        updateGoalField = findViewById(R.id.updateGoal);
        updateGoalButton = findViewById(R.id.updateGoalButton);

        updateWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateWeightField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter data",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    db.updateWeight(Integer.parseInt(updateWeightField.getText().toString()),
                            user.username);
                    Toast.makeText(getApplicationContext(), "Updated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateHeightField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter data",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    db.updateHeight(Integer.parseInt(updateHeightField.getText().toString()),
                            user.username);
                    Toast.makeText(getApplicationContext(), "Updated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateALButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateALField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter data",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(updateALField.getText().toString()) < 1 ||
                        Integer.parseInt(updateALField.getText().toString()) > 5){
                    Toast.makeText(getApplicationContext(), "Must be in between 1 and 5",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.updateActivityLevel(Integer.parseInt(updateALField.getText().toString()),
                            user.username);
                    Toast.makeText(getApplicationContext(), "Updated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateGoalField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter data",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(updateGoalField.getText().toString()) < 1 ||
                        Integer.parseInt(updateGoalField.getText().toString()) > 3){
                    Toast.makeText(getApplicationContext(), "Must be in between 1 and 3",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.updateGoal(Integer.parseInt(updateGoalField.getText().toString()),
                            user.username);
                    Toast.makeText(getApplicationContext(), "Updated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        lightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });


        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToHome();
            }
        });
    }

    public void BackToHome () {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user", user);
        intent.putExtra("usernameUsed", user.username);
        startActivity(intent);
    }
}