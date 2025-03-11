package com.example.caloriecountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Recs extends AppCompatActivity {

    User user;
    Database db;

    TextView title, recTypeMessage, s1, s2, s3, s4, s5, s6, s7;

    String recType;

    Button toHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recs);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        db = Database.getInstance(getApplicationContext());

        title = findViewById(R.id.title);
        recTypeMessage = findViewById(R.id.recType);

        s1 = findViewById(R.id.s1);
        s2 = findViewById(R.id.s2);
        s3 = findViewById(R.id.s3);
        s4 = findViewById(R.id.s4);
        s5 = findViewById(R.id.s5);
        s6 = findViewById(R.id.s6);
        s7 = findViewById(R.id.s7);

        toHome = findViewById(R.id.backToHome);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHomePage();
            }
        });
    }

    //in onStart do the actual functionality since it might change from the
    // last time the user accessed this
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

        recType = user.determineRec();

        String recMessage = "You should eat more " + recType;
        recTypeMessage.setText(recMessage);

        TextView[] views = {s1, s2, s3, s4, s5, s6, s7};
        Cursor searchResults = db.getRecs(recType);
        ArrayList<Suggestion> recs = new ArrayList<Suggestion>(7);
        searchResults.moveToNext();
        while(!searchResults.isAfterLast()) {
            String food = searchResults.getString(0);
            int cost = searchResults.getInt(1);
            int amount = searchResults.getInt(2);
            recs.add(new Suggestion(food, cost, amount));
            searchResults.moveToNext();
        }

        for(int i = 0; i < views.length; i++) {
            String foodData = recs.get(i).food;
            int costData = recs.get(i).cost;
            int amountData = recs.get(i).amount;

            String message = foodData + ": $" + costData + " " +
                    recType + ": " + amountData + "g";
            views[i].setText(message);
        }

    }

    public void toHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("user", user);
        intent.putExtra("usernameUsed", user.username);
        startActivity(intent);
    }
}