package com.example.jiahan.foodhouse;


public class Restaurant {

    public String title,desc,price,type;

    Restaurant(){};

    Restaurant(String title, String price, String type, String desc){
        this.title = title;
        this.price = price;
        this.type = type;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
