//package com.example.recipegenius.model;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class RecipeInfoFinder {
//    private final String apiKey;
//
//    public RecipeInfoFinder(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    public void fetchRecipeInfo(int recipeId) {
//        try {
//            String url = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + apiKey;
//
//            // Create a URL object
//            URL apiUrl = new URL(url);
//
//            // Open a connection to the URL
//            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
//
//            // Set the request method to GET
//            connection.setRequestMethod("GET");
//
//            // Get the response code
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // Read the response
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//                StringBuilder response = new StringBuilder();
//
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//
//                reader.close();
//
//                // Parse the JSON response for recipe information
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode recipeInfo = objectMapper.readTree(response.toString());
//
//                // Print out recipe information
//                System.out.println("ID " + recipeId);
//                System.out.println("Name: " + recipeInfo.get("title").asText());
//                System.out.println("Image: " + recipeInfo.get("image").asText());
//                System.out.println("Instructions: " + recipeInfo.get("sourceUrl").asText());
//
//            } else {
//                System.out.println("Error: " + responseCode);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
