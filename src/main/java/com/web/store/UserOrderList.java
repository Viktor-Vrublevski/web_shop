package com.web.store;

import com.web.store.entity.User;
import com.web.store.entity.goods.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOrderList {
    private static  Map<Integer, User> orderList ;

    public static Map<Integer,User> getInstance(){
        if (orderList == null){
            orderList = new HashMap<>();
            return orderList;
        }
        return orderList;
    }
   public static double allSum(User user){
        double sum = 0;
        if (orderList.containsKey(user.getId())){
            List<Product> products = orderList.get(user.getId()).getProducts();
            for (Product pr : products){
                sum = sum + (pr.getPrice() * pr.getQuantity());
            }
            return sum;
        }
        return sum;
   }

}
