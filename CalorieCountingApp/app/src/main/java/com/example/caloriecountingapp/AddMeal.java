package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AddMeal extends AppCompatActivity {

    Database db;
    User user;
    Button cancel, addMeal;
    EditText fat, carbs, protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        cancel = findViewById(R.id.cancel);
        addMeal = findViewById(R.id.addMeal);
        fat = findViewById(R.id.fat);
        carbs = findViewById(R.id.carbs);
        protein = findViewById(R.id.protein);
        db = Database.getInstance(getApplicationContext());


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHomePage();
            }
        });

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int proteinData = 0;
                int carbsData = 0;
                int fatData = 0;

                if (protein.getText().toString().equals("") ||
                        carbs.getText().toString().equals("")||
                        fat.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter data for all fields",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                proteinData += Integer.parseInt(protein.getText().toString());
                carbsData += Integer.parseInt(carbs.getText().toString());
                fatData += Integer.parseInt(fat.getText().toString());

                db.addMeal(user.username, proteinData, fatData, carbsData);

                toHomePage();
            }
        });
    }

    public void toHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user", user);
        intent.putExtra("usernameUsed", user.username);
        startActivity(intent);
    }
}