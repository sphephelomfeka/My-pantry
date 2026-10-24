package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recycler;
    private TextView count, empty;
    private List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        recycler = findViewById(R.id.pantryRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.itemCount);
        empty = findViewById(R.id.emptyPantry);
        findViewById(R.id.addIngredientButton).setOnClickListener(v -> startActivity(new Intent(this, IngredientFormActivity.class)));
        findViewById(R.id.viewRecipesButton).setOnClickListener(v -> startActivity(new Intent(this, RecipesActivity.class)));
        bindNav();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (db != null) refresh();
    }
    private void refresh() {
        ingredients = db.getIngredients(); count.setText(String.valueOf(ingredients.size()));
        empty.setVisibility(ingredients.isEmpty() ? View.VISIBLE : View.GONE);
        recycler.setVisibility(ingredients.isEmpty() ? View.GONE : View.VISIBLE);
        recycler.setAdapter(new IngredientAdapter(ingredients, new IngredientAdapter.Listener() {
            @Override
            public void onEdit(Ingredient item) {
                Intent i = new Intent(MainActivity.this, IngredientFormActivity.class);
                i.putExtra("id", item.id);
                i.putExtra("name", item.name);
                i.putExtra("quantity", item.quantity);
                i.putExtra("unit", item.unit);
                i.putExtra("expiry", item.expiry);
                startActivity(i);
            }
            @Override
            public void onDelete(Ingredient item) {
                db.deleteIngredient(item.id);
                refresh();
            }
        }));
        findViewById(R.id.recipeCount).setContentDescription(db.getStrictMatches().size() + " matching recipes");
    }
    private void bindNav() {
        findViewById(R.id.navPantry).setOnClickListener(v -> {});
        findViewById(R.id.navRecipes).setOnClickListener(v -> startActivity(new Intent(this, RecipesActivity.class)));
        findViewById(R.id.navSettings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}
