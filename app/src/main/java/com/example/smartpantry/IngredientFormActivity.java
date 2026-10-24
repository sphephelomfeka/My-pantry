package com.example.smartpantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IngredientFormActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText name, quantity, unit, expiry;
    private long id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_ingredient_form);
        db = new DatabaseHelper(this);
        name=findViewById(R.id.nameInput);
        quantity=findViewById(R.id.quantityInput);
        unit=findViewById(R.id.unitInput);
        expiry=findViewById(R.id.expiryInput);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getLongExtra("id", -1); ((TextView)findViewById(R.id.formTitle)).setText("Edit ingredient");
            name.setText(getIntent().getStringExtra("name"));
            quantity.setText(String.valueOf(getIntent().getDoubleExtra("quantity", 1)));
            unit.setText(getIntent().getStringExtra("unit"));
            expiry.setText(getIntent().getStringExtra("expiry"));
        }
        ((Button)findViewById(R.id.saveIngredientButton)).setOnClickListener(v -> save());
        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
    }
    private void save() {
        String n=name.getText().toString().trim(), q=quantity.getText().toString().trim(), u=unit.getText().toString().trim(), e=expiry.getText().toString().trim();
        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(q) || TextUtils.isEmpty(u)) {
            Toast.makeText(this, "Enter a name, quantity and unit.", Toast.LENGTH_LONG).show();
            return;
        }
        double amount;
        try {
            amount=Double.parseDouble(q);
            if (amount <= 0)
                throw new NumberFormatException();
        }
        catch (NumberFormatException ex) {
            quantity.setError("Enter a quantity greater than zero");
            quantity.requestFocus();
            return;
        }
        if (!e.isEmpty() && !e.matches("\\d{2}-\\d{2}-\\d{4}")) {
            expiry.setError("Use DD-MM-YYYY");
            expiry.requestFocus();
            return;
        }
        long result = db.saveIngredient(new Ingredient(id,n,amount,u,e));
        if (result < 0) {
            Toast.makeText(this, "Could not save ingredient.", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
