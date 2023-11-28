package com.example.recipegenius.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;
import java.util.ArrayList;
import java.util.List;

import com.example.recipegenius.model.DataHolder;
import com.example.recipegenius.model.IngredientList;
import com.example.recipegenius.model.RecipeFinder;
import com.example.recipegenius.model.RecipeInfo;
import com.example.recipegenius.model.RecipesList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
public class RecipesPageController extends BaseController {

@FXML
private VBox recipesContainer;

     private List<String> ingredients;

     public RecipesPageController() {
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

                    Text missedIngredientCount = new Text(
                            "Missed Ingredients Count: " + recipeInfo.getMissedIngredientCount());
                    missedIngredientCount.getStyleClass().add("MissedIngredients");

                    Hyperlink seeRecipeBtn = new Hyperlink("See Recipe");
                    seeRecipeBtn.getStyleClass().add("recipeLinkBtn");

                    // Load recipe image
                    ImageView imageView = new ImageView(new Image(recipeInfo.getImageUrl()));
                    imageView.setFitWidth(100); // Set the width as per your requirement
                    imageView.setFitHeight(100); // Set the height as per your requirement

                    VBox recipeLabelContainer = new VBox(recipeName, missedIngredientCount);
                    BorderPane recipeContainer = new BorderPane();
                    BorderPane.setAlignment(recipeLabelContainer, Pos.CENTER);
                    BorderPane.setAlignment(seeRecipeBtn, Pos.CENTER);

                    // Adding image and details to the container
                    recipeContainer.setLeft(imageView);
                    recipeContainer.setCenter(recipeLabelContainer);
                    recipeContainer.setRight(seeRecipeBtn);

                    recipeContainer.getStyleClass().add("recipe-container");
                    recipesContainer.getChildren().addAll(recipeContainer);

                    seeRecipeBtn.setOnAction(event -> displayRecipe(recipeInfo.getInstructionUrl()));
               }
          } catch (NullPointerException e) {
               System.out.println("NullPointerException: " + e);
          }
     }

     public static void displayRecipe(String url) {
          try {
               java.awt.Desktop.getDesktop().browse(new URI(url));
          } catch (IOException | URISyntaxException e) {
               e.printStackTrace();
          }
     }

     @FXML
     protected void goToHome() {
          mainApp.loadHomePage();
     }
}
