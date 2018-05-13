package com.example.administrator.diandianclient.data;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Video {
    private String imageURL;
    private String name;
    private double price;
    private int id;

    public Video(String url, String name,String price,String id) {
        this.imageURL = url;
        this.name = name;
        this.price = Double.valueOf(price).doubleValue();
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
}
