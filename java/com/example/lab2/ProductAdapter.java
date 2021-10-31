package com.example.lab2;

import android.content.Context;
import android.view.*;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.viewProductName);
        TextView priceView = view.findViewById(R.id.viewProductPrice);
        TextView amountView = view.findViewById(R.id.viewProductAmount);
        TextView dateView = view.findViewById(R.id.viewProductDate);

        Product product = products.get(position);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //Опасный код
        String strDate = dateFormat.format(product.getDate()); //Тут тоже, играемся с датой

        nameView.setText(nameView.getText() + product.getName());
        priceView.setText(priceView.getText() + String.valueOf(product.getPrice()));
        amountView.setText(amountView.getText() + String.valueOf(product.getAmount()));
        dateView.setText(dateView.getText() + strDate);

        return view;
    }
}
