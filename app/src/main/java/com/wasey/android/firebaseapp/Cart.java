package com.wasey.android.firebaseapp;

import java.io.Serializable;

public class Cart implements Serializable{

    Mobile prod;
    int quantity;

    public Cart(){
        Mobile prod=null;
        int quantity=0;
    }
    public Cart(Mobile prod, int quantity){
        this.prod=prod;
        this.quantity=quantity;
    }

    public Mobile getProd() {
        return prod;
    }

    public void setProd(Mobile prod) {
        this.prod = prod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}