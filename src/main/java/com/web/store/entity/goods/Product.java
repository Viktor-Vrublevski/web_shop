package com.web.store.entity.goods;

public interface Product {
    String getName();
    double getPrice();
    int getQuantity();
    int getId();
    String getDescription();
    void setName(String name);
    void setPrice(double price);
    void setQuantity(int quantity);
}
