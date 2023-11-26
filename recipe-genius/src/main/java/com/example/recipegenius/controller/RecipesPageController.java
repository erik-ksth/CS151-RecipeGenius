package com.example.recipegenius.controller;

import com.example.recipegenius.model.IngredientList;
import com.example.recipegenius.model.RecipeFinder;
import com.example.recipegenius.model.RecipeInfo;
import com.example.recipegenius.model.RecipesList;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecipesPageController extends BaseController {

     @FXML
     private VBox recipesContainer;

     @FXML
     private Label recipeName;

     @FXML
     private Text missedIngredientCount;

     // Generate Recipes
     public void generateRecipes(IngredientList ingredientList) {
          RecipeFinder recipeFinder = new RecipeFinder();
          RecipesList recipesList = recipeFinder.findRecipes(ingredientList);

          // Iterate through the RecipeInfo objects in the RecipesList
          for (RecipeInfo recipeInfo : recipesList.getRecipesList()) {
               recipeName.setText("Hi");
//               missedIngredientCount.setText("Missed Ingredients: " + recipeInfo.getMissedIngredientCount());
          }
     }

}
