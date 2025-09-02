package com.example.caloriecountingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class Database extends SQLiteOpenHelper {


    //static instance of Database
    private static Database dbInstance = null;

    //creates it in correct version
    private Database(Context context) {
        super(context, "users.db", null, 1);
    }

    //makes sure that there is only one instance of Database class
    public static Database getInstance(Context context){
        if(dbInstance == null){
            dbInstance = new Database(context);
        }

        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    //inserts new user into user table
    public void insertUserData(String username, String pw, int weight, int height,
                              int level, String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP table users");
        db.execSQL("create Table IF NOT EXISTS users(username TEXT UNIQUE primary key, password TEXT, " +
                "weight INTEGER, height INTEGER, level INTEGER, gender TEXT, goal INTEGER)");

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", pw);
        values.put("weight", weight);
        values.put("height", height);
        values.put("level", level);
        values.put("gender", gender);
        values.put("goal", 1);

        try {
            db.insertOrThrow("users", null, values);
        }catch(android.database.sqlite.SQLiteConstraintException ce)
        {
            Log.i("Database", "ConstraintException: " + ce.getMessage());
            throw ce;
        }
    }

    //accesses a row from the user table based on the username
    public Cursor getPassword(String un) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create Table IF NOT EXISTS users(username TEXT UNIQUE primary key, password TEXT, " +
                "weight INTEGER, height INTEGER, level INTEGER, gender TEXT, goal INTEGER)");
        String[] selection = {un};
        return db.rawQuery("SELECT password FROM users WHERE username = ?", selection);
    }

    public Cursor getAllUserData(String un) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selection = {un};
        return db.rawQuery("SELECT username, password, weight, height, level, gender, " +
                "goal FROM users WHERE username = ?", selection);
    }

    public void addMeal(String username, int protein, int fat, int carbs) {
        SQLiteDatabase db = this.getWritableDatabase();
        //do not create the table is the table already exists
        //db.execSQL("DROP Table meals");
        db.execSQL("create Table IF NOT EXISTS meals(username TEXT, protein INTEGER, " +
                "fat INTEGER, carbs INTEGER)");

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("protein", protein);
        values.put("fat", fat);
        values.put("carbs", carbs);

        try {
            db.insertOrThrow("meals", null, values);
        }catch(android.database.sqlite.SQLiteConstraintException ce)
        {
            Log.i("Database", "ConstraintException: " + ce.getMessage());
            throw ce;
        }
    }

    public Cursor getMeals(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create Table IF NOT EXISTS meals(username TEXT, protein INTEGER, " +
                "fat INTEGER, carbs INTEGER)");
        String[] selection = {username};
        return db.rawQuery("SELECT protein, fat, carbs FROM meals WHERE username = ?", selection);
    }

    public Cursor getRecs(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor exists = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='recs'", null);
        String[] selection = {type};
        if( (exists.getCount() == 1) || exists.getCount() == 0) {
            db.execSQL("create Table IF NOT EXISTS recs(food TEXT, cost INTEGER, type TEXT, amount INTEGER)");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Avocado', 2, 'fat', 22);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Almond', 10, 'fat', 45);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Walnut', 5, 'fat', 52);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Salmon', 6, 'fat', 27);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Peanuts', 1, 'fat', 72);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Tofu', 3, 'fat', 6);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Sardine', 2, 'fat', 8);");

            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Chicken Breast', 3, 'protein', 30);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Turkey', 4, 'protein', 29);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Eggs', 1, 'protein', 6);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Cottage Cheese', 2, 'protein', 14);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Greek Yogurt', 3, 'protein', 10);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Lentils', 1, 'protein', 18);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Quinoa', 5, 'protein', 8);");

            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Rice', 1, 'carb', 45);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Sweet Potato', 2, 'carb', 37);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Banana', 1, 'carb', 27);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Oats', 3, 'carb', 66);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Bread', 2, 'carb', 25);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Pasta', 4, 'carb', 40);");
            db.execSQL("INSERT INTO recs(food, cost, type, amount) VALUES('Potatoes', 1, 'carb', 30);");
        }
        return db.rawQuery("SELECT food, cost, amount FROM recs WHERE type = ?", selection);
    }

    public void updateHeight (int h, String u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("height", h);
        db.update("users", values, "username = ?", new String[]{u});
    }

    public void updateWeight (int w, String u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("weight", w);
        db.update("users", values, "username = ?", new String[]{u});
    }

    public void updateActivityLevel (int al, String u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("level", al);
        db.update("users", values, "username = ?", new String[]{u});
    }

    public void updateGoal (int g, String u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("goal", g);
        db.update("users", values, "username = ?", new String[]{u});
    }

    public void updateDate (String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE date");
        db.execSQL("create Table IF NOT EXISTS date(date TEXT)");
        ContentValues values = new ContentValues();
        values.put("date", date);

        try {
            db.insertOrThrow("date", null, values);
        }catch(android.database.sqlite.SQLiteConstraintException ce)
        {
            Log.i("Database", "ConstraintException: " + ce.getMessage());
            throw ce;
        }
    }

    public Cursor getDate (String currentDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create Table IF NOT EXISTS date(date TEXT)");
        String[] selection = {currentDate};
        return db.rawQuery("SELECT date FROM date WHERE date = ?", selection);
    }

    public void destroyMeals () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create Table IF NOT EXISTS meals(username TEXT, protein INTEGER, " +
                "fat INTEGER, carbs INTEGER)");
        db.execSQL("DROP TABLE meals");
    }
}


