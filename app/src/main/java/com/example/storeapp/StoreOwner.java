package com.example.storeapp;

import java.util.ArrayList;

public class StoreOwner extends User {

    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
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
