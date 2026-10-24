package com.example.smartpantry;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        String name = getIntent().getStringExtra("name");
        ((TextView)findViewById(R.id.detailName)).setText(name == null ? "Recipe" : name);

        int minutes = getIntent().getIntExtra("minutes", 0);
        ((TextView)findViewById(R.id.detailDescription)).setText("A simple recipe · " + minutes + " minutes · Made from your pantry");
        String ingredients = getIntent().getStringExtra("ingredients");

        StringBuilder formatted = new StringBuilder();

        if (ingredients != null) for (String ingredient : ingredients.split(",")) formatted.append("•  ").append(capitalise(ingredient.trim())).append("\n");
        ((TextView)findViewById(R.id.detailIngredients)).setText(formatted.toString().trim());
        ((TextView)findViewById(R.id.detailMethod)).setText(getIntent().getStringExtra("method"));
        findViewById(R.id.detailBack).setOnClickListener(v -> finish());
    }
    private String capitalise(String s) {
        return s.isEmpty() ? s : s.substring(0,1).toUpperCase() + s.substring(1);
    }
}
