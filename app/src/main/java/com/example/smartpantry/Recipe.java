package com.example.smartpantry;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    public String name, method;
    public List<String> ingredients;
    public int minutes;
    public Recipe(String name, int minutes, String method, String... ingredients) {
        this.name = name;
        this.minutes = minutes;
        this.method = method;
        this.ingredients = Arrays.asList(ingredients);
    }
}
