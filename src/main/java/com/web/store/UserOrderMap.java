package com.web.store;

import com.web.store.entity.User;
import com.web.store.entity.goods.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* -- Область памяти, где временно сохраняются пользователи
                  с не пустой корзиной.
 */
public class UserOrderMap {
    private static  Map<Integer, User> userOrderMap ;

    public static Map<Integer,User> getInstance(){
        if (userOrderMap == null){
            userOrderMap = new HashMap<>();
            return userOrderMap;
        }
        return userOrderMap;
    }
   public static double allSum(User user){
        double sum = 0;
        if (userOrderMap.containsKey(user.getId())){
            List<Product> products = userOrderMap.get(user.getId()).getProducts();
            for (Product pr : products){
                sum = sum + (pr.getPrice() * pr.getQuantity());
            }
            return sum;
        }
        return sum;
   }

}
