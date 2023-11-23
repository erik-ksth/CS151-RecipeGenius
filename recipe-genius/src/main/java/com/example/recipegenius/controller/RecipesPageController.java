package com.example.recipegenius.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class RecipesPageController extends BaseController {

     @FXML
     private ListView<String> recipeLinkContainer;

     @FXML
     private Label recipeName;

     @FXML
     private Hyperlink recipeLink;

     public void setRecipeInfo(String name, String url) {
          recipeName.setText(name);
          recipeLink.setText(url);
      }
  
}
