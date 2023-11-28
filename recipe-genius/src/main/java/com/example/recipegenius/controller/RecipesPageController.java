package com.example.recipegenius.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.recipegenius.model.DataHolder;
import com.example.recipegenius.model.IngredientList;
import com.example.recipegenius.model.RecipeFinder;
import com.example.recipegenius.model.RecipeInfo;
import com.example.recipegenius.model.RecipesList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecipesPageController extends BaseController {

     @FXML
     private TilePane recipesContainer;

     private List<String> ingredients = new ArrayList<>();

     public RecipesPageController() {
     }

     public RecipesPageController(IngredientList ingredientList) {
          this.ingredients = ingredientList.getIngredients();
     }

     public void passIngredients(IngredientList ingredientList) {
          this.ingredients = ingredientList.getIngredients();
          System.out.println(ingredients);
     }

     @FXML
     public void initialize() {

          // Call the generateRecipes method with the obtained ingredientList
          // generateRecipes(ingredients);
          List<String> ingredients = DataHolder.getIngredients();
          generateRecipes(ingredients);
     }

     // Generate Recipes
     public void generateRecipes(List<String> ingredients) {
          RecipeFinder recipeFinder = new RecipeFinder();
          RecipesList recipesList = recipeFinder.findRecipes(ingredients);

          // Iterate through the RecipeInfo objects in the RecipesList
          try {
               for (RecipeInfo recipeInfo : recipesList.getRecipesList()) {
                    System.out.println(recipeInfo.getRecipeName());
                    
                    Label recipeName = new Label(recipeInfo.getRecipeName());
                    recipeName.getStyleClass().add("ShowMenu");
                    Text missedIngredientCount = new Text("Missed Ingredients Count: " + recipeInfo.getMissedIngredientCount());
                    missedIngredientCount.getStyleClass().add("MissedIngredients");
                    VBox recipeContainer = new VBox(recipeName, missedIngredientCount);
                    recipeContainer.getStyleClass().add("recipe-container");
                    recipesContainer.getChildren().addAll(recipeContainer);
               }
          } catch (NullPointerException e) {
               System.out.println("NullPointerException: " + e);
          }
     }

}
