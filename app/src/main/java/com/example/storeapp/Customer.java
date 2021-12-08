package com.example.storeapp;

import java.util.ArrayList;

public class Customer extends User{
    ArrayList<Order> orders;
    public Customer() {
        super();
    }

    public Customer(String name, String password) {
        super(name, password);
        orders = new ArrayList<>();
    }
}
