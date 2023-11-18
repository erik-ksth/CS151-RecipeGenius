package com.example.recipegenius.controller;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IngredientsPageController extends BaseController {

    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> suggestionList;

    // Get the user input and call autocomplete method
    @FXML
    private void handleInput() {
        String userInput = inputField.getText();
        autoComplete(userInput);
    }

    // Auto-complete method
    private void autoComplete(String userInput) {
        try {
            String apiKey = "5dc3339e14f64eecb9f8d41188bbf0f9";
            String url = "https://api.spoonacular.com/food/ingredients/autocomplete?apiKey=" + apiKey + "&query=" + userInput + "&number=5";
            
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
                    }
                }

            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Go to Next page
    @FXML
    protected void goToRecipesPage() {
        mainApp.switchToRecipesPage();
    }
}