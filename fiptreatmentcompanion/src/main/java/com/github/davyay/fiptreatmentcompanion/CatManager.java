package com.github.davyay.fiptreatmentcompanion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;

public class CatManager {
    private ObjectMapper objectMapper;
    private String basePath;

    public CatManager() {
        // Relative path from the project's root directory
        this.basePath = "./CatProfiles";

        // Ensure the directory exists
        new File(basePath).mkdirs();

        // Initialize ObjectMapper and register the JavaTimeModule to handle LocalDate
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void saveCatProfile(Cat cat) throws IOException {
        String filename = generateFilename(cat.getName());
        // Save the cat profile to a file
        objectMapper.writeValue(new File(basePath, filename), cat);
    }

    public Cat loadCatProfile(String catName) throws IOException {
        String filename = generateFilename(catName);
        // Load a cat profile from a file
        return objectMapper.readValue(new File(basePath, filename), Cat.class);
    }

    private String generateFilename(String catName) {
        return catName + "Profile.json"; // Append 'Profile.json' to the cat's name
    }
}
