package com.wasey.android.firebaseapp;

import java.util.ArrayList;

public class User {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public ArrayList<Mobile> getWishList() {
        return wishList;
    }

    public void setWishList(ArrayList<Mobile> wishList) {
        this.wishList = wishList;
    }

    public ArrayList<Cart> getmCart() {
        return mCart;
    }

    public void setmCart(ArrayList<Cart> mCart) {
        this.mCart = mCart;
    }

    String userId;
    String name;
    String userType;
    String username;
    String password;
    String emailId;
    String phone_no;

    ArrayList<Mobile> wishList;

    ArrayList<Cart> mCart;

    public User(){
        this.emailId=null;
        this.name=null;
        this.password=null;
        this.phone_no=null;
        this.userId=null;
        this.username=null;
        this.userType=null;
        wishList=new ArrayList<>();
        mCart=new ArrayList<>();
    }

    public User(String uId, String n, String type, String uname, String pw, String eId, String p_no) {
        this.emailId = eId;
        this.name = n;
        this.password = pw;
        this.phone_no = p_no;
        this.userId = uId;
        this.username = uname;
        this.userType = type;

        wishList = new ArrayList<>();
        mCart = new ArrayList<>();
    }

    public void addToWishList(Mobile p){
        wishList.add(p);
    }

    public void removeFromWishList(Mobile p){
        wishList.remove(p);
    }


    public void addToCart(Mobile product){
        Cart temp= new Cart(product,1);
        mCart.add(temp);
    }

    public void removeFromCart(Cart cartItem){
        mCart.remove(cartItem);
    }

    public ArrayList<Cart> getCart(){
        return this.mCart;
    }


}
