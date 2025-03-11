package com.example.caloriecountingapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User implements Parcelable {
    String username;
    String password;
    String gender;
    int weight;
    int height;
    int activityLevel;
    int goal;
    ArrayList<Meal> meals = new ArrayList<Meal>();

    public User (String username, String password, String gender,
                 int weight, int height, int activityLevel, int goal) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activityLevel = activityLevel;
        this.goal = goal;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        gender = in.readString();
        weight = in.readInt();
        height = in.readInt();
        activityLevel = in.readInt();
        goal = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String determineRec() {
        Meal[] historyMeals = new Meal[meals.size()];
        for(int i = 0; i < historyMeals.length; i++) {
            historyMeals[i] = meals.get(i);
        }

        int protein = 0;
        int carbs = 0;
        int fat = 0;

        for(int i = 0; i < historyMeals.length; i++) {
            protein += historyMeals[i].proteinAmount * 4;
            carbs += historyMeals[i].carbAmount * 4;
            fat += historyMeals[i].fatAmount * 9;
        }

        if (carbs <= fat && carbs <= protein) {
            return "carb";
        } else if (fat <= carbs && fat <= protein) {
            return "fat";
        } else {
            return "protein";
        }
    }

    public double calcCaloriesNeeded (int goal) {
        int baseCalories = 0;
        if (gender.equals("Female")) {
            baseCalories += 655.1 + (4.35 * weight) + (4.7 * height);
        } else {
            baseCalories += 66 + (6.2 * weight) + (12.7 * height);
        }

        if (activityLevel == 1) {
            if (goal == 1) {
                return baseCalories * 1.2;
            } else if (goal == 2) {
                return baseCalories * 1.2 * 1.15;
            } else if (goal == 3) {
                return baseCalories * 1.2 * 0.85;
            }
        } else if ( activityLevel == 2) {
            if (goal == 1) {
                return baseCalories * 1.375;
            } else if (goal == 2) {
                return baseCalories * 1.375 * 1.15;
            } else if (goal == 3) {
                return baseCalories * 1.375 * 0.85;
            }
        }else if ( activityLevel == 3) {
            if (goal == 1) {
                return baseCalories * 1.55;
            } else if (goal == 2) {
                return baseCalories * 1.55 * 1.15;
            } else if (goal == 3) {
                return baseCalories * 1.55 * 0.85;
            }
        }else if ( activityLevel == 4) {
            if (goal == 1) {
                return baseCalories * 1.725;
            } else if (goal == 2) {
                return baseCalories * 1.725 * 1.15;
            } else if (goal == 3) {
                return baseCalories * 1.725 * 0.85;
            }
        }else if ( activityLevel == 5) {
            if (goal == 1) {
                return baseCalories * 1.9;
            } else if (goal == 2) {
                return baseCalories * 1.9 * 1.15;
            } else if (goal == 3) {
                return baseCalories * 1.9 * 0.85;
            }
        }
        return baseCalories;
    }

    public double calculateEatenCalories () {
        return caloriesFromFat() + caloriesFromProtein() + caloriesFromCarbs();
    }

    public double caloriesFromProtein () {
        double fromProtein = 0;

        Meal[] eatenMeals = new Meal[meals.size()];
        for(int i = 0; i < eatenMeals.length; i++) {
            eatenMeals[i] = meals.get(i);
        }

        for (int i = 0; i < eatenMeals.length; i++) {
            fromProtein += eatenMeals[i].proteinAmount * 4;
        }
        return fromProtein;
    }

    public double caloriesFromCarbs () {
        double fromCarbs = 0;

        Meal[] eatenMeals = new Meal[meals.size()];
        for(int i = 0; i < eatenMeals.length; i++) {
            eatenMeals[i] = meals.get(i);
        }

        for (int i = 0; i < eatenMeals.length; i++) {
            fromCarbs += eatenMeals[i].carbAmount * 4;
        }
        return fromCarbs;
    }

    public double caloriesFromFat () {
        double fromFat = 0;

        Meal[] eatenMeals = new Meal[meals.size()];
        for(int i = 0; i < eatenMeals.length; i++) {
            eatenMeals[i] = meals.get(i);
        }

        for (int i = 0; i < eatenMeals.length; i++) {
            fromFat += eatenMeals[i].fatAmount * 9;
        }
        return fromFat;
    }

    public double calculateRemainingCalories() {
        return calcCaloriesNeeded(goal) - calculateEatenCalories();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(gender);
        parcel.writeInt(weight);
        parcel.writeInt(height);
        parcel.writeInt(activityLevel);
        parcel.writeInt(goal);
    }
}
