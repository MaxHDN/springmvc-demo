package com.lagou.edu.pojo.Car;

import com.lagou.edu.pojo.User.User;

public class Car {
    private String brand;
    private String money;

    private User user;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", money='" + money + '\'' +
                ", user=" + user +
                '}';
    }
}
