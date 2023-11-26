package com.example.recipegenius.model;

import java.util.ArrayList;
import java.util.List;

public class RecipesList {

    private List<RecipeInfo> recipeslist = new ArrayList<>();

    // Constructors
    public RecipesList() {}

    public RecipesList(RecipeInfo recipe) { this.recipeslist.add(recipe); }

    public void addRecipe(RecipeInfo recipe) { this.recipeslist.add(recipe); }

    public void remove(RecipeInfo recipe) { this.recipeslist.remove(recipe); }

    public List<RecipeInfo> getRecipesList() {
        return new ArrayList<>(recipeslist);
    }
}
