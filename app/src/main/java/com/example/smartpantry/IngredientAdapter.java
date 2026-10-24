package com.example.smartpantry;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder> {
    public interface Listener { void onEdit(Ingredient item); void onDelete(Ingredient item); }
    private final List<Ingredient> items;
    private final Listener listener;
    public IngredientAdapter(List<Ingredient> items, Listener listener) { this.items = items; this.listener = listener; }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ingredient, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        Ingredient item = items.get(position);
        h.name.setText(item.name);
        h.quantity.setText(format(item.quantity) + " " + item.unit + (item.expiry.isEmpty() ? "" : "  ·  Expires " + item.expiry));
        h.edit.setOnClickListener(v -> listener.onEdit(item));
        h.delete.setOnClickListener(v -> new AlertDialog.Builder(v.getContext()).setTitle("Delete ingredient?")
            .setMessage("Remove " + item.name + " from your pantry?")
            .setNegativeButton("Cancel", (d,w) -> d.dismiss())
            .setPositiveButton("Delete", (d,w) -> listener.onDelete(item)).show());
        h.itemView.setOnClickListener(v -> listener.onEdit(item));
    }
    private String format(double n) {
        return n == (long)n ? String.valueOf((long)n) : String.valueOf(n);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    static class Holder extends RecyclerView.ViewHolder {
        TextView name, quantity, edit, delete;
        Holder(View v) { super(v); name=v.findViewById(R.id.ingredientName);
            quantity=v.findViewById(R.id.ingredientQuantity);
            edit=v.findViewById(R.id.editIngredient);
            delete=v.findViewById(R.id.deleteIngredient);
        }
    }
}
