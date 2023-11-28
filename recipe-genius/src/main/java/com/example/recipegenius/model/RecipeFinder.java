package com.example.recipegenius.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RecipeFinder {

    // Constructors
    public RecipeFinder() {
    }

    String apiKey = "bf457e24348349a49fbd2894f67de249";

    // Method to find recipes
    public RecipesList findRecipes(List<String> ingredientList) {

        // Create a list to store recipes
        RecipesList recipesList = new RecipesList();

        int numberOfRecipes = 6;
        try {
            
            // Load properties from the .env file
            // Properties properties = new Properties();
            // FileInputStream input = new FileInputStream("com/example/recipegenius/.env");
            // properties.load(input);
            // input.close();

            // String apiKey = properties.getProperty("SPOONACULAR_API_KEY");

            String url = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=" + apiKey + "&ingredients="
                    + String.join(",+", ingredientList) + "&number=" + numberOfRecipes;

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

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                // Display Recipe on the page
                for (JsonNode recipe : jsonNode) {
                    RecipeInfo recipeInfo = new RecipeInfo();
                    int recipeId = recipe.get("id").asInt();
                    recipeInfo.setRecipeId(recipeId);
                    fetchRecipeInfo(recipeInfo, recipeId);
                    recipeInfo.setMissedIngredientCount(recipe.get("missedIngredientCount").asInt());
                    recipesList.addRecipe(recipeInfo);
                }

            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipesList;
    }

    // Method to find recipe detail
    public void fetchRecipeInfo(RecipeInfo recipeInfo, int recipeId) {
        try {
            String url = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + apiKey;

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
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response for recipe information
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode recipe = objectMapper.readTree(response.toString());

                // Set recipe information
                recipeInfo.setRecipeName(recipe.get("title").asText());
                recipeInfo.setImageUrl(recipe.get("image").asText());
                recipeInfo.setInstructionUrl(recipe.get("sourceUrl").asText());

            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
