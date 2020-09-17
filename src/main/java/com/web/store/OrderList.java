package com.web.store;

import com.web.store.entity.Order;
import com.web.store.entity.goods.*;
import com.web.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
/*
место для хранения заказов с перечнем товара.
Хранятся заказы оплаченные и ожидающие оплаты.
*/

public class OrderList {

    private static List<Order> orderList;

    public static List<Order> getOrderList(){
        if (orderList == null){
            orderList = new ArrayList<>();
            return orderList;
        }
        return orderList;
    }
}
