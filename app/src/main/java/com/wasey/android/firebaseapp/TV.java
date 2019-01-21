package com.wasey.android.firebaseapp;

public class TV {
    int pId;
    String brand;
    String pname;
    float price;
    int stock;

    String color;
    float size;     //screen
    int resolution;

    String imageURL;

    public TV(){
        this.pId=0;
        this.brand=null;
        this.pname=null;
        this.price=0;
        this.stock=0;

        this.color=null;
        this.size=0;
        this.resolution=0;
        imageURL=null;
    }
    public TV(int pId, String br, String pn, float pric, int stk , String col, float siz, int resolution,String url){
        this.pId=pId;
        this.brand=br;
        this.pname=pn;
        this.price=pric;
        this.stock=stk;

        this.color=col;
        this.size=siz;
        this.resolution=resolution;
        this.imageURL=url;
    }
}
