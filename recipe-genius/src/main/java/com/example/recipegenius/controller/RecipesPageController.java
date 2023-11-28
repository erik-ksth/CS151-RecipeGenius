package com.example.recipegenius.controller;

import com.example.recipegenius.model.IngredientList;
import com.example.recipegenius.model.RecipeFinder;
import com.example.recipegenius.model.RecipeInfo;
import com.example.recipegenius.model.RecipesList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RecipesPageController extends BaseController {

     @FXML
     private VBox recipesContainer;

     // Generate Recipes
     public void generateRecipes(IngredientList ingredientList) {
          RecipeFinder recipeFinder = new RecipeFinder();
          RecipesList recipesList = new RecipesList();
          recipesList = recipeFinder.findRecipes(ingredientList);

          // Iterate through the RecipeInfo objects in the RecipesList
          try {
               int i = 0;
               for (RecipeInfo recipeInfo : recipesList.getRecipesList()) {
                    System.out.println(recipeInfo.getRecipeName());
                    Label recipeName = new Label(recipeInfo.getRecipeName());
                    recipesContainer.getChildren().addAll(recipeName);
               }
          } catch (NullPointerException e) {
               System.out.println("NullPointerException: " + e);
          }
     }

}
