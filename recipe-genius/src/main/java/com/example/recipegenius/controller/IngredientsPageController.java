package com.example.recipegenius.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.example.recipegenius.model.IngredientList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class IngredientsPageController extends BaseController {
    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> suggestionList;

    @FXML
    private VBox ingredientListContainer;

    private void displayIngredient(String newIngredient) {
        Label ingredientLabel = new Label(newIngredient);

        // Create buttons for edit and delete
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        // Set styles/classes if needed
        ingredientLabel.getStyleClass().add("ingredientLable");
        editButton.getStyleClass().add("editList");
        deleteButton.getStyleClass().add("deleteIcon");

        // Set up actions for the buttons
        editButton.setOnAction(event -> handleEditIngredient(newIngredient));
        deleteButton.setOnAction(event -> deleteIngredient(ingredientLabel));

        // Create an HBox to hold the ingredient information
        ingredientContainer = new HBox(ingredientLabel, editButton, deleteButton);
        ingredientContainer.setSpacing(5.0);
        // Add the HBox to the ingredientListContainer
        ingredientListContainer.getChildren().add(ingredientContainer);
    }
    private void handleEditIngredient(String ingredientName) {
        // Open a dialog to get the new name for the ingredient
        TextInputDialog dialog = new TextInputDialog(ingredientName);
        dialog.setTitle("Edit Ingredient");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the new name for the ingredient:");

        // Show the dialog and wait for the user's response
        dialog.showAndWait().ifPresent(newName -> {
            // Check if the user clicked OK and the new name is not empty
            if (!newName.isEmpty()) {
                // Update the ingredient name in the view
                updateIngredientName(ingredientName, newName);
                System.out.println("Editing ingredient: " + ingredientName + " to " + newName);
            } else {
                // Show an alert if the new name is empty
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid name for the ingredient.");
                alert.showAndWait();
            }
        });
    }
    // Helper method to update the ingredient name in the view
    private void updateIngredientName(String oldName, String newName) {
        // Iterate through the child nodes of ingredientListContainer
        ingredientListContainer.getChildren().forEach(node -> {
            HBox hbox = (HBox) node;
            Label label = (Label) hbox.getChildren().get(0);

            // Check if the label's text matches the old ingredient name
            if (label.getText().equals(oldName)) {
                // Update the label with the new ingredient name
                label.setText(newName);
            }
        });
    }
    @FXML
    private HBox ingredientContainer;

    @FXML
    private Label ingredientLabel;

    @FXML
    private Button deleteButton;

    IngredientList ingredientList = new IngredientList();

    // Get the user input and call autocomplete method
    @FXML
    private void handleInput() {
        autoComplete(inputField.getText());
    }

    // Add ingredients
    @FXML

    private void addIngredient() {
        String newIngredient = inputField.getText().trim();

        if (!newIngredient.isEmpty() && suggestionList.getItems().contains(newIngredient)) {
            // Add the ingredient to the list
            ingredientList.addIngredient(newIngredient);

            // Display new ingredient
            displayNewIngredient(newIngredient);

            // Clear the input field and autocomplete list data
            clearInputAndSuggestions();
            System.out.println("Ingredients: " + ingredientList.getIngredients());
        } else {
            System.out.println("Invalid input");
        }
    }

    private void displayNewIngredient(String newIngredient) {
        Label ingredientLabel = new Label(newIngredient);
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        // Set styles/classes if needed
        ingredientLabel.getStyleClass().add("ingredientLable");
        editButton.getStyleClass().add("editList");
        deleteButton.getStyleClass().add("deleteIcon");

        // Set up actions for the buttons
        editButton.setOnAction(event -> handleEditIngredient(newIngredient));
        deleteButton.setOnAction(event -> deleteIngredient(ingredientLabel));

        // Create an HBox to hold the ingredient information
        HBox ingredientContainer = new HBox(ingredientLabel, editButton, deleteButton);
        ingredientContainer.setSpacing(1.0);
        ingredientContainer.getStyleClass().add("listContainer");

        // Add the HBox to the ingredientListContainer
        ingredientListContainer.getChildren().add(ingredientContainer);
    }

    private void clearInputAndSuggestions() {
        inputField.clear();
        suggestionList.getItems().clear();
    }

    // Go to Next page
    @FXML
    protected void goToRecipesPage() {
        // Check if at least an ingredient has been added.
        if (ingredientList.getLength() > 0) {
            // Pass ingredientList data to recipe page
            RecipesPageController recipesPageController = new RecipesPageController();
            recipesPageController.generateRecipes(ingredientList);
            mainApp.switchToRecipesPage();
        } else {
            System.out.println("Please add some ingredients.");
        }
    }

    private void autoComplete(String userInput) {
        try {
            String apiKey = "dc10eafafe494c06ae0eeeeff70bb183";
            String encodedUserInput = URLEncoder.encode(userInput, StandardCharsets.UTF_8.toString());
            String url = "https://api.spoonacular.com/food/ingredients/autocomplete?apiKey=" + apiKey + "&query="
                    + encodedUserInput + "&number=5";

            // Create a URL object
            try {
                URL apiUrl = new URL(url);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                try {
                    // Set the request method to GET
                    connection.setRequestMethod("GET");

                    // Get the response code
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read the response
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                            StringBuilder response = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }

                            // Parse the JSON response
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode jsonNode = objectMapper.readTree(response.toString());

                            // Clear previous items
                            suggestionList.getItems().clear();

                            if (jsonNode.isArray()) {
                                for (JsonNode suggestion : jsonNode) {
                                    String ingredientName = suggestion.get("name").asText();
                                    System.out.println("Name: " + ingredientName);
                                    suggestionList.getItems().add(ingredientName);
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
                        }
                    } else {
                        System.out.println("Error: something is wrong " + responseCode);
                        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                            String errorLine;
                            StringBuilder errorResponse = new StringBuilder();
                            while ((errorLine = errorReader.readLine()) != null) {
                                errorResponse.append(errorLine);
                            }
                            System.out.println("Error response: " + errorResponse.toString());
                        }
                    }
                } finally {
                    connection.disconnect(); // Close the connection in the finally block
                }
            } catch (MalformedURLException e) {
                System.out.println("Malformed URL: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private void deleteIngredient(Label ingredientLabel) {
        ingredientList.removeIngredient(ingredientLabel.getText());
        HBox ingredientContainer = (HBox) ingredientLabel.getParent();
        ingredientListContainer.getChildren().remove(ingredientContainer);
        System.out.println("Ingredients: " + ingredientList.getIngredients());
    }

}