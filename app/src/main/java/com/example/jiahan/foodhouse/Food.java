package com.example.jiahan.foodhouse;

public class Food {

    private int number;
    private String fruit;
    private double price;

    public Food(){}

    public Food(String fruit, double price,int number){

        this.fruit = fruit;
        this.price = price;
        this.number = number;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}