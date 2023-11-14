package com.example.recipe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Add this import statement
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Label welcomeText;




    @FXML
    private void initialize() {
        welcomeText.setText("Welcome to Recipe Genius!");

    }


    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        // Handle the button click event
        openIngredientPage();
    }
    @FXML
    private Label welcomeText2;
    private void openIngredientPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingredient-view.fxml"));
            VBox ingredientPageRoot = fxmlLoader.load();
            Scene ingredientPageScene = new Scene(ingredientPageRoot, 1200, 800);

            // Create a new stage for the ingredient input page
            welcomeText2.setText("Add your ingredients");
            Stage ingredientPageStage = new Stage();
            ingredientPageStage.setTitle("Ingredient Input");
            ingredientPageStage.setScene(ingredientPageScene);

            // Show the ingredient input page
            ingredientPageStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
