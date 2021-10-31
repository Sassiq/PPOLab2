package com.example.lab2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    int requestCode = 1;
    ArrayList<Product> products = new ArrayList();
    ListView productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle listProducts = getIntent().getExtras();

        if (listProducts != null)
        {
            products = (ArrayList<Product>) listProducts.get("products");
        }

        UpdateView();

        ListView listView = (ListView) findViewById(R.id.productsList);
        registerForContextMenu(listView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSortButtonClick(View view){
        ArrayList<Product> sortedProducts = new ArrayList<Product>();
        for (Product product: products) {
            if (product.getPrice() > 1_000_000 && new Date().getTime() - product.getDate().getTime() > 2_628_000_000l){
                sortedProducts.add(product);
            }
        }

        sortedProducts.sort(Product::compareTo);
        products = sortedProducts;
        UpdateView();
    }

    public void onAddButtonClick(View view){
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("products", products);
        startActivity(intent);
    }

    public void onInitializeButtonClick(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == Activity.RESULT_OK)
        {
            if (data == null)
            {
                return;
            }

            Uri uri = data.getData();
            setDataFromFile(uri);

            UpdateView();
        }
    }

    private void setDataFromFile(Uri uri){
        try {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(this.getContentResolver().openInputStream(uri)));
            String line = bufferedReader.readLine();
            while (line != null) {
                Product product = new Product(line);
                products.add(product);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                editProduct(info.position);
                return true;
            case R.id.delete:
                deleteItem(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteItem(int position) {
        products.remove(position);
        UpdateView();
    }

    private void editProduct(int position) {
        products.remove(position);
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("products", products);
        startActivity(intent);
    }

    private void UpdateView()
    {
        productsList = findViewById(R.id.productsList);
        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.list_item, products);
        productsList.setAdapter(productAdapter);
    }

}