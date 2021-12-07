package com.example.storeapp;


public class Store {
    private String storename;

    public Store(String storename) {
        this.storename = storename;
    }

    public String getStorename(){
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }
}

