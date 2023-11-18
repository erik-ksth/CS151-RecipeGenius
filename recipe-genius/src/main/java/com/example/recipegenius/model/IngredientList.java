package com.example.recipegenius.model;

import java.util.ArrayList;
import java.util.List;

public class IngredientList {
     
     private List<String> ingredients = new ArrayList<>();

     // Constructors
     public IngredientList() {}

     public IngredientList(String ingredientName) {
          this.ingredients.add(ingredientName);
     }

     public void addIngredient(String ingredientName) {
          this.ingredients.add(ingredientName);
     }

     public void removeIngredient(String ingredientName) {
          this.ingredients.remove(ingredientName);
     }

     public List<String> getIngredients() {
          // Return a copy to avoid direct manipulation of the internal list
          return new ArrayList<>(ingredients);
     }

}
