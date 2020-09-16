package com.web.store;

import com.web.store.entity.Order;


import java.util.ArrayList;
import java.util.List;

public class OrderList {

    private static List<Order> orderList;

    public static List<Order> getOrderList(){
        if (orderList == null){
            orderList = new ArrayList<>();
            return orderList;
        }
        return orderList;
    }

    public static List<Order> getNoPayedOrders(){
        List<Order> orders = new ArrayList<>();
        for (Order order : getOrderList()){
            if (!order.isStatus()){
                orders.add(order);
            }
        }
        return orders;
    }

    public static List<Order> getPayedOrders(){
        List<Order> orders = new ArrayList<>();
        for (Order order : getOrderList()){
            if (order.isStatus()){
                orders.add(order);
            }
        }
        return orders;
    }

}
