package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class History extends AppCompatActivity {

    User user;
    Button back;
    TextView meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        back = findViewById(R.id.backToHome);

        db = Database.getInstance(getApplicationContext());

        meal1 = findViewById(R.id.meal1);
        meal2 = findViewById(R.id.meal2);
        meal3 = findViewById(R.id.meal3);
        meal4 = findViewById(R.id.meal4);
        meal5 = findViewById(R.id.meal5);
        meal6 = findViewById(R.id.meal6);
        meal7 = findViewById(R.id.meal7);
        meal8 = findViewById(R.id.meal8);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHomePage();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        //add the meals of the user into the ArrayList
        Cursor mealSearch = db.getMeals(user.username);
        mealSearch.moveToNext();
        while(!mealSearch.isAfterLast()) {
            double protein = mealSearch.getDouble(0);
            double fat = mealSearch.getDouble(1);
            double carbs = mealSearch.getDouble(2);
            user.meals.add(0, new Meal(protein, fat, carbs));
            mealSearch.moveToNext();
        }

        TextView[] displays = {meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8};

        if (user.meals.size() == 0) {
            meal1.setText("No Meals to Display");
        } else if (user.meals.size() < 8) {
            for(int i = 0; i < user.meals.size(); i++) {
                double protein = user.meals.get(i).proteinAmount;
                double fat = user.meals.get(i).fatAmount;
                double carbs = user.meals.get(i).carbAmount;

                String message = "Protein: " + protein + " Fat: " + fat + " Carbohydrates: " + carbs;
                displays[i].setText(message);
            }
        } else {
            for(int i = 0; i < displays.length; i++) {
                double protein = user.meals.get(i).proteinAmount;
                double fat = user.meals.get(i).fatAmount;
                double carbs = user.meals.get(i).carbAmount;

                String message = "Protein: " + protein + " Fat: " + fat + "Carbohydrates: " + carbs;
                displays[i].setText(message);
            }
        }
    }

    public void toHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user", user);
        intent.putExtra("usernameUsed", user.username);
        startActivity(intent);
    }
}