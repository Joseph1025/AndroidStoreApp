package com.example.storeapp;

import java.util.ArrayList;

public class Order {
    /***
     * Order contains a product and the amount of that product.
     * It has two states: complete or incomplete
     */
    private ArrayList<Product> productList;
    private boolean complete = false;
    private String customer_order;
    private String store_order;

    public String getCustomer_order() {
        return customer_order;
    }

    public void setCustomer_order(String customer_order) {
        this.customer_order = customer_order;
    }

    public String getStore_order() {
        return store_order;
    }

    public void setStore_order(String store_order) {
        this.store_order = store_order;
    }

    public Order() {
        this.productList = new ArrayList<>();
    }

    public Order(ArrayList<Product> arrayList, String storeOwner){
        this.productList = arrayList;
        this.store_order = storeOwner;
        this.complete = false;
    }
    public Order(ArrayList<Product> arrayList, String storeOwner, String customer){
        this.productList = arrayList;
        this.store_order = storeOwner;
        this.complete = false;
        this.customer_order = customer;
    }

    public ArrayList<Product> getProducts() {
        return productList;
    }

    public void addProduct(Product p) {
        /**
         * Add product to order
         */
        this.productList.add(p);
    }

    public void delProduct(Product p) {
        /**
         * Remove product from order
         */
        this.productList.remove(p);
    }

    public boolean getStatus() {
        return this.complete;
    }

    public void setStatus() {
        /**
         * Change order to status 'complete'
         * or vice versa
         */
        this.complete = !this.complete;
    }



}
