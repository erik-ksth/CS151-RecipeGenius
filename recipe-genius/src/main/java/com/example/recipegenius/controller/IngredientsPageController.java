package com.example.recipegenius.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.recipegenius.model.DataHolder;
import com.example.recipegenius.model.IngredientList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.text.Text;

public class IngredientsPageController extends BaseController {

    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> suggestionList;

    @FXML
    private VBox ingredientListContainer;

    @FXML
    private HBox ingredientContainer;

    @FXML
    private Label ingredientLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Text errorMessage;

    IngredientList ingredientList = new IngredientList();

    // Get the user input and call autocomplete method
    @FXML
    private void handleInput() {
        autoComplete(inputField.getText());
    }

    // Add ingredients
    @FXML
    private void addIngredient() {
        String newIngredient = inputField.getText();
        if (suggestionList.getItems().contains(newIngredient)) {
            ingredientList.addIngredient(newIngredient);

            // Display new ingredient
            Label ingredientLabel = new Label(newIngredient);
            ingredientLabel.getStyleClass().add("IngredientLabel");

            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().add("DeleteButton");

            HBox ingredientContainer = new HBox(ingredientLabel, deleteButton);
            ingredientContainer.setAlignment(Pos.CENTER);
            ingredientContainer.setSpacing(10); // Set spacing between label and button

            // Set up the delete action for the new button
            deleteButton.setOnAction(event -> deleteIngredient(ingredientContainer, ingredientLabel));
            
            // Append new ingredient
            ingredientListContainer.getChildren().add(ingredientContainer);

            // Delete Error Message
            errorMessage.setText("");

            // Clear the inputfield and autocomplete list data
            inputField.clear();
            suggestionList.getItems().clear();
            System.out.println("Ingredients: " + ingredientList.getIngredients());
        } else {
            System.out.println("Invalid input");
            errorMessage.setText("Invalid input");
        }
    }
    // Go to Next page
    @FXML
    protected void goToRecipesPage() {
        // Check if at least an ingredient has been added.
        if (ingredientList.getLength() > 0) {
            // Pass ingredientList data to recipe page
            DataHolder.setIngredients(ingredientList.getIngredients());
            mainApp.switchToRecipesPage();
            RecipesPageController recipesPageController = new RecipesPageController();
            recipesPageController.passIngredients(ingredientList);
        } else {
            System.out.println("Please add some ingredients.");
            errorMessage.setText("Please add some ingredients.");
        }
    }

    // Auto-complete method
    private void autoComplete(String userInput) {

        try {
            // Properties properties = new Properties();
            // InputStream input = getClass().getResourceAsStream("application.properties");
            // properties.load(input);
            // input.close();

            // String apiKey = properties.getProperty("SPOONACULAR_API_KEY");
            // System.out.println("API KEY ==== "+apiKey);
            String apiKey = "930bab48c9044e6db5df0510743ee973";

            String url = "https://api.spoonacular.com/food/ingredients/autocomplete?apiKey=" + apiKey + "&query="
                    + userInput + "&number=5";

            // Create a URL object
            URL apiUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Prase the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                // Clear previous items
                suggestionList.getItems().clear();

                if (jsonNode.isArray()) {
                    for (JsonNode suggestion : jsonNode) {
                        String ingredientName = suggestion.get("name").asText();
                        System.out.println("Name: " + ingredientName);
                        suggestionList.getItems().add(ingredientName);
                        errorMessage.setText("");
                    }

                    // Set the visibility of the suggestionList
                    suggestionList.setVisible(!suggestionList.getItems().isEmpty());

                    // Add an event handler to the suggestionList
                    suggestionList.setOnMouseClicked(e -> {
                        String selectedItem = suggestionList.getSelectionModel().getSelectedItem();
                        // Set the selected item into the inputField
                        inputField.setText(selectedItem);
                        // Close the auto-complete box
                        suggestionList.setVisible(false);
                    });
                }

            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteIngredient(HBox ingredientContainer, Label ingredientLabel) {
        ingredientList.removeIngredient(ingredientLabel.getText());
        ingredientListContainer.getChildren().remove(ingredientContainer);
        System.out.println("Ingredients: " + ingredientList.getIngredients());
    }

}