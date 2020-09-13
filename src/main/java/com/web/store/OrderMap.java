package com.web.store;

import com.web.store.entity.Order;


import java.util.HashMap;
import java.util.Map;

public class OrderMap {

    private static Map<Integer, Order> orderMap;

    public static Map<Integer, Order> getOrderMap(){
        if (orderMap == null){
            orderMap = new HashMap<>();
            return orderMap;
        }
        return orderMap;
    }

}
