package com.example.smartpantry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.Holder> {
    private final List<Recipe> recipes;
    public RecipeAdapter(List<Recipe> recipes) { this.recipes = recipes; }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        Recipe recipe = recipes.get(position);
        h.name.setText(recipe.name);
        h.meta.setText(recipe.ingredients.size() + " ingredients  ·  " + recipe.minutes + " minutes");
        h.open.setOnClickListener(v -> {
            Context c = v.getContext(); Intent i = new Intent(c, RecipeDetailActivity.class);
            i.putExtra("name", recipe.name); i.putExtra("minutes", recipe.minutes);
            i.putExtra("method", recipe.method);
            i.putExtra("ingredients", String.join(",", recipe.ingredients));
            c.startActivity(i);
        });
        h.itemView.setOnClickListener(v -> h.open.performClick());
    }
    @Override public int getItemCount() {
        return recipes.size();
    }
    static class Holder extends RecyclerView.ViewHolder {
        TextView name, meta, open;
        Holder(View v) {
            super(v);
            name=v.findViewById(R.id.recipeName);
            meta=v.findViewById(R.id.recipeMeta);
            open=v.findViewById(R.id.recipeOpen);
        }
    }
}
