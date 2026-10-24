package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, minutes INTEGER NOT NULL, method TEXT NOT NULL, ingredients TEXT NOT NULL)");
        seedRecipes(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Tomato Egg Scramble", 12, "1. Dice the tomatoes.\n2. Warm a pan with a little oil.\n3. Add tomatoes, then beaten eggs. Stir gently until cooked.", "tomato,egg,salt");
        addRecipe(db, "Garlic Tomato Pasta", 20, "1. Boil pasta until tender.\n2. Gently cook garlic and tomatoes in oil.\n3. Toss together and season.", "pasta,tomato,garlic,salt");
        addRecipe(db, "Cheesy Potato Bake", 40, "1. Slice potatoes thinly.\n2. Layer with cheese and a splash of milk.\n3. Bake until golden and tender.", "potato,cheese,milk,salt");
        addRecipe(db, "Banana Oat Pancakes", 15, "1. Mash the banana.\n2. Mix with oats and egg.\n3. Cook small pancakes in a lightly oiled pan.", "banana,oats,egg");
        addRecipe(db, "Simple Fried Rice", 15, "1. Scramble the egg in a hot pan.\n2. Add cooked rice and peas.\n3. Season with soy sauce and stir-fry.", "rice,egg,peas,soy sauce");
        addRecipe(db, "Cheese Omelette", 10, "1. Beat eggs with a pinch of salt.\n2. Pour into a warm non-stick pan.\n3. Add cheese, fold and cook through.", "egg,cheese,salt");
        addRecipe(db, "Apple Cinnamon Oats", 8, "1. Dice the apple.\n2. Simmer oats in milk or water.\n3. Add apple and cinnamon and cook until soft.", "apple,oats,cinnamon,milk");
        addRecipe(db, "Chickpea Tomato Bowl", 10, "1. Drain and rinse chickpeas.\n2. Chop tomatoes and cucumber.\n3. Toss with lemon juice and salt.", "chickpeas,tomato,cucumber,lemon,salt");
        addRecipe(db, "Creamy Mushroom Pasta", 22, "1. Cook pasta.\n2. Sauté mushrooms and garlic.\n3. Stir in cream, combine with pasta and season.", "pasta,mushroom,garlic,cream,salt");
        addRecipe(db, "Spinach Cheese Toast", 10, "1. Wilt spinach in a pan.\n2. Place spinach and cheese on bread.\n3. Toast until crisp and melted.", "spinach,cheese,bread");
        addRecipe(db, "Peanut Banana Toast", 5, "1. Toast the bread.\n2. Spread peanut butter.\n3. Top with sliced banana.", "bread,peanut butter,banana");
        addRecipe(db, "Lemon Garlic Potatoes", 30, "1. Chop potatoes into wedges.\n2. Toss with garlic, lemon and oil.\n3. Roast until crisp outside and soft inside.", "potato,garlic,lemon,salt");
        addRecipe(db, "Carrot Fritters", 18, "1. Grate the carrots.\n2. Mix with egg and flour.\n3. Pan-fry spoonfuls until golden.", "carrot,egg,flour,salt");
        addRecipe(db, "Yoghurt Berry Bowl", 5, "1. Spoon yoghurt into a bowl.\n2. Add berries and oats.\n3. Finish with honey.", "yoghurt,berries,oats,honey");
        addRecipe(db, "Tuna Sweetcorn Salad", 8, "1. Drain tuna and sweetcorn.\n2. Mix with cucumber and lemon juice.\n3. Season and serve chilled.", "tuna,sweetcorn,cucumber,lemon");
        addRecipe(db, "Quick Bean Quesadilla", 12, "1. Spoon beans and cheese onto a tortilla.\n2. Fold in half.\n3. Toast in a pan on both sides until crisp.", "beans,cheese,tortilla");
        addRecipe(db, "Avocado Egg Toast", 8, "1. Toast bread.\n2. Mash avocado with salt.\n3. Top toast with avocado and a cooked egg.", "bread,avocado,egg,salt");
        addRecipe(db, "Cucumber Yoghurt Dip", 7, "1. Grate cucumber and squeeze out excess water.\n2. Mix with yoghurt and garlic.\n3. Season and chill briefly.", "cucumber,yoghurt,garlic,salt");
        addRecipe(db, "Cinnamon Apple Toast", 8, "1. Dice apple and soften with cinnamon.\n2. Toast the bread.\n3. Spoon warm apple over toast.", "apple,bread,cinnamon");
        addRecipe(db, "Simple Lentil Soup", 35, "1. Sauté onion and carrot.\n2. Add lentils and water or stock.\n3. Simmer until soft and season.", "lentils,onion,carrot,salt");
    }
    private void addRecipe(SQLiteDatabase db, String name, int minutes, String method, String ingredients) {
        ContentValues cv = new ContentValues(); cv.put("name", name); cv.put("minutes", minutes); cv.put("method", method); cv.put("ingredients", ingredients);
        db.insert("recipes", null, cv);
    }
    public List<Ingredient> getIngredients() {
        List<Ingredient> list = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM ingredients ORDER BY name COLLATE NOCASE", null)) {
            while (c.moveToNext()) list.add(new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4)));
        }
        return list;
    }
    public long saveIngredient(Ingredient item) {
        ContentValues cv = new ContentValues(); cv.put("name", item.name); cv.put("quantity", item.quantity); cv.put("unit", item.unit); cv.put("expiry", item.expiry);
        if (item.id > 0) return getWritableDatabase().update("ingredients", cv, "id=?", new String[]{String.valueOf(item.id)}) > 0 ? item.id : -1;
        return getWritableDatabase().insert("ingredients", null, cv);
    }
    public void deleteIngredient(long id) { getWritableDatabase().delete("ingredients", "id=?", new String[]{String.valueOf(id)}); }
    public List<Recipe> getRecipes() {
        List<Recipe> list = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery("SELECT name,minutes,method,ingredients FROM recipes ORDER BY name COLLATE NOCASE", null)) {
            while (c.moveToNext()) list.add(new Recipe(c.getString(0), c.getInt(1), c.getString(2), c.getString(3).split(",")));
        }
        return list;
    }
    public List<Recipe> getStrictMatches() {
        List<Recipe> matches = new ArrayList<>();
        List<Ingredient> pantry = getIngredients();

        for (Recipe recipe : getRecipes()) {
            boolean allPresent = true;
            for (String required : recipe.ingredients) {
                boolean found = false;
                for (Ingredient item : pantry) {
                    if (normalise(item.name).equals(normalise(required)) && item.quantity >= 1) {
                        found = true;
                        break;
                    }
                }
                if (!found) { allPresent = false;
                    break;
                }
            }
            if (allPresent) matches.add(recipe);
        }
        return matches;
    }
    private String normalise(String s) {
        String value = s == null ? "" : s.trim().toLowerCase().replaceAll("[^a-z0-9 ]", "");
        if (value.endsWith("ies") && value.length() > 4) value = value.substring(0, value.length()-3) + "y";
        else if (value.endsWith("s") && !value.endsWith("ss") && value.length() > 3) value = value.substring(0, value.length()-1);
        return value;
    }
}
