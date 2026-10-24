package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.navPantry).setOnClickListener(v -> finish());
        findViewById(R.id.navRecipes).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, RecipesActivity.class));
        });

        findViewById(R.id.navSettings).setOnClickListener(v -> {});
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        Switch expiry = findViewById(R.id.expirySwitch);
        expiry.setChecked(prefs.getBoolean("expiry_alerts", true));
        expiry.setOnCheckedChangeListener((button, checked) -> prefs.edit().putBoolean("expiry_alerts", checked).apply());
    }
}
