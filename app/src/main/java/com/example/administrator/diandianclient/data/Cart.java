package com.example.administrator.diandianclient.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Cart implements Serializable {
    private String imageURL;
    private String name;
    private double price;
    private int number;
    private int id;
    public Cart(){

    }
    public Cart(String imageURL, String name, double price, int number,int id){
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.number = number;
        this.id = Integer.valueOf(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
