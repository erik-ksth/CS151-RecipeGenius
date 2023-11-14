package com.example.recipegenius.controller;

import com.example.recipegenius.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IngredientsPageController extends BaseController {
    @FXML
    protected void goToRecipesPage() {
        mainApp.switchToRecipesPage();
    }
}