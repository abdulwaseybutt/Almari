package com.wasey.android.firebaseapp;

import java.io.Serializable;

public class Mobile implements Serializable{
    public int pId;
    public String brand;
    public String model;
    public String color;
    public String display_Size;
    public int battery;
    public int ram;
    public double price;
    public double os;
    public double rate;
    public String img;
    public int stock;

    public Mobile(){
        this.pId=-1;
        this.brand=null;
        this.model=null;
        this.color=null;
        this.display_Size=null;
        this.battery=-1;
        this.ram=-1;
        this.price=-1;
        this.os=-1;
        this.rate=-1;
        this.img=null;
        this.stock=-1;
    }
    public Mobile(int id, String brand, String model, String color, String disp, int batt, int RAM, double pric, double os, double rate, String Image,
    int stock){
        this.pId=id;
        this.brand=brand;
        this.model=model;
        this.color=color;
        this.display_Size=disp;
        this.battery=batt;
        this.ram=RAM;
        this.price=pric;
        this.os=os;
        this.rate=rate;
        this.img=Image;
        this.stock=stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getName(){
        return (this.model);

    }

    public String getColor() {
        return color;
    }

    public String getDisplay_Size() {
        return display_Size;
    }

    public int getBattery() {
        return battery;
    }

    public int getRam() {
        return ram;
    }

    public double getOS() {
        return os;
    }

    public double getRate() {
        return rate;
    }

    public double getPrice(){
        return this.price;
    }
    public String getImg(){
        return this.img;
    }
}
