package com.example.recipegenius.model;

import java.util.List;

public class DataHolder {
    private static List<String> ingredients;

    public static List<String> getIngredients() {
        return ingredients;
    }

    public static void setIngredients(List<String> ingredients) {
        DataHolder.ingredients = ingredients;
    }
}

