package com.example.recipegenius.model;

public class RecipeInfo {

    private int recipeId;

    private String recipeName;

    private String imageUrl;

    private String instructionUrl;

    private int missedIngredientCount;

    // constructors
    public RecipeInfo() {};

    public RecipeInfo(int recipeId, String recipeName, String imageUrl, String instructionUrl, int missedIngredientCount) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
        this.instructionUrl = instructionUrl;
        this.missedIngredientCount = missedIngredientCount;
    }

    // Getters
    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInstructionUrl() {
        return instructionUrl;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    // Setters
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setInstructionUrl(String instructionUrl) {
        this.instructionUrl = instructionUrl;
    }

    public void setMissedIngredientCount(int missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }
}
