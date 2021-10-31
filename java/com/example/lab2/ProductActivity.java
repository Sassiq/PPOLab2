package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ProductActivity extends AppCompatActivity {
    ArrayList<Product> products = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Bundle listProducts = getIntent().getExtras();

        if (listProducts != null)
        {
            products = (ArrayList<Product>) listProducts.get("products");
        }
    }

    public void onButtonClick(View view) {
        EditText editName = findViewById(R.id.editName);
        EditText editPrice = findViewById(R.id.editPrice);
        EditText editAmount = findViewById(R.id.editAmount);
        CalendarView editDate = findViewById(R.id.editTextDate);

        Product product = new Product(
                editName.getText().toString(),
                Integer.parseInt(String.valueOf(editAmount.getText())),
                Float.parseFloat(String.valueOf(editPrice.getText())),
                new Date(editDate.getDate()));

        products.add(product);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("products", products);
        startActivity(intent);
    }
}