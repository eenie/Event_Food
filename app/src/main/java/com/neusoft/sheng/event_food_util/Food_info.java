package com.neusoft.sheng.event_food_util;


import java.io.Serializable;

public class Food_info implements Serializable {

    private String food_id;
    private String  food_name;
    private String food_mater;
    private String food_price;
    private String shop_id;
    private String food_pic;
    private String food_sell;

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_mater() {
        return food_mater;
    }

    public void setFood_mater(String food_mater) {
        this.food_mater = food_mater;
    }

    public String getFood_price() {
        return food_price;
    }

    public void setFood_price(String food_price) {
        this.food_price = food_price;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getFood_pic() {
        return food_pic;
    }

    public void setFood_pic(String food_pic) {
        this.food_pic = food_pic;
    }

    public String getFood_sell() {
        return food_sell;
    }

    public void setFood_sell(String food_sell) {
        this.food_sell = food_sell;
    }
}
