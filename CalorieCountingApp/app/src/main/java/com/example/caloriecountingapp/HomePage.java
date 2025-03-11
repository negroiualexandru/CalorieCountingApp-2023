package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;
public class HomePage extends AppCompatActivity {

    Database db;
    User user;
    TextView title, eaten, fat, carbs, protein, remaining;
    Button addMeal, settings, history, suggestions;
    public Bundle getBundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //check if it is the same day: update the day in DB and clear Meals if not
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format.format(c.getTime());

        db = Database.getInstance(getApplicationContext());

        Cursor dateSearch = db.getDate(todayDate);
        if (!dateSearch.moveToNext() || !dateSearch.getString(0).equals(todayDate)) {
            db.destroyMeals();
            db.updateDate(todayDate);
        }

        addMeal = findViewById(R.id.addMeal);
        settings = findViewById(R.id.settings);
        history = findViewById(R.id.history);
        suggestions = findViewById(R.id.suggestions);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecPage();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryPage();
            }
        });

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddMealPage();
            }
        });
    }

    @Override
    protected void onStart () {
        super.onStart();

        db = Database.getInstance(getApplicationContext());

        getBundle = this.getIntent().getExtras();
        String usernameUsed = getBundle.getString("usernameUsed");

        Cursor userData = db.getAllUserData(usernameUsed);
        userData.moveToNext();
        user = new User(usernameUsed, userData.getString(1), userData.getString(5),
                userData.getInt(2), userData.getInt(3), userData.getInt(4), userData.getInt(6));

        //recalculate information based on any newly entered meals
        Cursor mealSearch = db.getMeals(user.username);
        mealSearch.moveToNext();
        while(!mealSearch.isAfterLast()) {
            double protein = mealSearch.getDouble(0);
            double fat = mealSearch.getDouble(1);
            double carbs = mealSearch.getDouble(2);
            user.meals.add(0, new Meal(protein, fat, carbs));
            mealSearch.moveToNext();
        }

        title = (TextView) findViewById(R.id.title);
        String welcomeMessage = "Welcome " + user.username;
        title.setText(welcomeMessage);

        eaten = (TextView) findViewById(R.id.caloriesEaten);
        String caloriesEatenMessage = "Calories Eaten: " + user.calculateEatenCalories();
        eaten.setText(caloriesEatenMessage);

        fat = (TextView) findViewById(R.id.fromFat);
        String fromFatMessage = "Fat: " + user.caloriesFromFat();
        fat.setText(fromFatMessage);

        carbs = (TextView) findViewById(R.id.fromCarbs);
        String fromCarbsMessage = "Carbohydrates: " + user.caloriesFromCarbs();
        carbs.setText(fromCarbsMessage);

        protein = (TextView) findViewById(R.id.fromProtein);
        String fromProteinMessage = "Protein: " + user.caloriesFromProtein();
        protein.setText(fromProteinMessage);

        remaining = (TextView) findViewById(R.id.caloriesRemaining);
        String caloriesRemainingMessage = "Calories Remaining (rounded): " +
                Math.round((user.calcCaloriesNeeded(user.goal) - user.calculateEatenCalories()));
        remaining.setText(caloriesRemainingMessage);
    }

    public void openAddMealPage () {
        //move all user to new Activity
        Intent intent = new Intent(this, AddMeal.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void openHistoryPage () {
        //move all user to new Activity
        Intent intent = new Intent(this, History.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void openRecPage () {
        //move all user to new Activity
        Intent intent = new Intent(this, Recs.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void openSettings() {
        //move all user to new Activity
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void dropMeals() {

    }
}
