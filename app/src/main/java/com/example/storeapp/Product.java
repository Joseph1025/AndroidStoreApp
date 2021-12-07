package com.example.storeapp;

public class Product {
    /***
     * Product contains the name and description of a product.
     */

    private String productName;
    private String description;
    private int price;
    private int amount;

    public Product() {
        this.productName = "";
        this.description = "";
    }

    public Product(String name, String description, int price, int count) {
        this.productName = name;
        this.description = description;
        this.price = price;
        this.amount = count;
    }

    public Product(String name, String description, int price) {
        this.productName = name;
        this.description = description;
        this.price = price;
        this.amount = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Product obj = (Product) o;
        return obj.productName.equals(this.productName) && obj.description.equals(this.description);

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductCount() {
        return amount;
    }

    public void setAmount(int amount) { this.amount = amount; }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addItem(){
        amount++;
    }

    public void deleteItem(){
        amount--;
    }

    public void setZero(){
        amount = 0;
    }

}
