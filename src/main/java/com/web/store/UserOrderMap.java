package com.web.store;

import com.web.store.entity.User;
import com.web.store.entity.goods.Product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* -- Область памяти, где временно сохраняются пользователи
                  с не пустой корзиной.
 */
public class UserOrderMap {
    private static  Map<Integer, Map<Date,User>> userOrderMap ;

    public static Map<Integer,Map<Date,User>> getInstance(){
        if (userOrderMap == null){
            userOrderMap = new HashMap<>();
            return userOrderMap;
        }
        return userOrderMap;
    }

   public static double allSum(User user){
        double sum = 0;
        if (userOrderMap.containsKey(user.getId())){
            Map<Date,User> map = userOrderMap.get(user.getId());
            for (Map.Entry<Date,User> entry : map.entrySet()) {
                List<Product> products = entry.getValue().getProducts();
                for (Product pr : products) {
                    sum = sum + (pr.getPrice() * pr.getQuantity());
                }
                return sum;
            }
        }
        return sum;
   }

}
