package com.example.recipegenius.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeFinder {
    public static void main(String[] args) {

        // Create an ArrayList for ingredients
        List<String> ingredients = new ArrayList<>();
        int numberOfRecipes = 6;
        try {
            String apiKey = "5dc3339e14f64eecb9f8d41188bbf0f9";

            // Add elements to the list
            ingredients.add("Pork");
            ingredients.add("Bell Pepper");
            ingredients.add("honey");

            String url = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=" + apiKey + "&ingredients=" + String.join(",", ingredients) + "&number=" + numberOfRecipes;

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
                // System.out.println(response);

//              // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                int i = 0;
                // Print out dish names
                RecipeInfo recipeInfoFetcher = new RecipeInfo(apiKey);
                for (JsonNode recipe : jsonNode) {
                    int recipeId = recipe.get("id").asInt();
                    System.out.println("No." + (i + 1));
                    recipeInfoFetcher.fetchRecipeInfo(recipeId);
                    System.out.println("Missed Ingredient Count: " + recipe.get("missedIngredientCount").asInt());
                    System.out.println(" ");
                    i++;
                }

            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
