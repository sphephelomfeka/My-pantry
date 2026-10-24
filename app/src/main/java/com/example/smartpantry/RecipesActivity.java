package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    private DatabaseHelper db;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        db = new DatabaseHelper(this);

        findViewById(R.id.navPantry).setOnClickListener(v -> finish());
        findViewById(R.id.navRecipes).setOnClickListener(v -> {});
        findViewById(R.id.navSettings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (db != null) refresh();
    }
    private void refresh() {
        List<Recipe> matches = db.getStrictMatches();
        ((TextView)findViewById(R.id.matchSummary)).setText("STRICT MATCHES  ·  " + matches.size());

        RecyclerView recycler = findViewById(R.id.recipesRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new RecipeAdapter(matches));

        findViewById(R.id.noRecipes).setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
        recycler.setVisibility(matches.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
