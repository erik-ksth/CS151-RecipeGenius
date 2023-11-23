package com.example.recipegenius.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecipeFinder {

    private IngredientList ingredientList;

    // Constructors
    public RecipeFinder() {
    }

    public RecipeFinder(IngredientList ingredientList) {
        this.ingredientList = ingredientList;
    }

    // Getter
    public IngredientList getIngredientList() {
        return ingredientList;
    }

    // Setter
    public void setIngredientList(IngredientList ingredientList) {
        this.ingredientList = ingredientList;
    }

    // Method to find recipes
    public void findRecipes(IngredientList ingredientList) {
        int numberOfRecipes = 6;
        try {
            
            // Load properties from the .env file
            // Properties properties = new Properties();
            // FileInputStream input = new FileInputStream("com/example/recipegenius/.env");
            // properties.load(input);
            // input.close();

            // String apiKey = properties.getProperty("SPOONACULAR_API_KEY");
            String apiKey = "5dc3339e14f64eecb9f8d41188bbf0f9";

            String url = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=" + apiKey + "&ingredients="
                    + String.join(",+", ingredientList.getIngredients()) + "&number=" + numberOfRecipes;

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

                // Parse the JSON response
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
