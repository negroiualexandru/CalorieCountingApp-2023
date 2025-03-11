package com.example.caloriecountingapp;

public class Meal {
    Double proteinAmount;
    Double fatAmount;
    Double carbAmount;

    public Meal (double protein, double fat, double carb) {
        proteinAmount = protein;
        fatAmount = fat;
        carbAmount = carb;
    }

    public double calculateCalories() {
        return (proteinAmount * 4) + (carbAmount * 4) + (fatAmount * 9);
    }
}
