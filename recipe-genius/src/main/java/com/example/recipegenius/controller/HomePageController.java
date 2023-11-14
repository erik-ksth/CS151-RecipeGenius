package com.example.recipegenius.controller;
import com.example.recipegenius.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController {
    @FXML
    private Label welcomeText;

    // Reference to the Main application
    private Main mainApp;

    // Method to set the reference to the main application
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    protected void getStartedButton() {
        if (mainApp != null) {
            mainApp.switchToIngredientsPage();
        }
    }
}