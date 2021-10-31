package com.example.lab2;

import java.io.Serializable;
import java.util.Date;

public class Product implements Comparable<Product>, Serializable {
    private String name;
    private int amount;
    private float price;
    private Date date;

    public  Product(String name, int amount, float price, Date date) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.date = date;
    }

    public Product(String str){
        String[] input = str.split(";");
        name = input[0];
        amount = Integer.parseInt(input[1]);
        price = Float.parseFloat(input[2]);
        date = new Date(Long.parseLong(input[3]));
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public int compareTo(Product product) {
        return this.name.compareTo(product.name);
    }
}
