package com.example.recipegenius.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController extends BaseController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void goToIngredientsPage() {
        mainApp.switchToIngredientsPage();
    }
}