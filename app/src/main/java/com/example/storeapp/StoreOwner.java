package com.example.storeapp;

import java.util.ArrayList;

public class StoreOwner extends User {

    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    private String brand;
    private double price;
    private String store_name;

    public StoreOwner() {
        super();
        this.store_name = "";
        this.orderList = new ArrayList<>();
        this.productList = new ArrayList<>();
    }

    public StoreOwner(String name, String password) {
        super(name, password);
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void addOrder(Order ord) {
        orderList.add(ord);
    }

    public void delOrder(Order ord) {
        orderList.remove(ord);
    }

    public void addProduct(Product prod) {
        productList.add(prod);
    }

    public void delProduct(Product prod) {
        productList.remove(prod);
    }

}
