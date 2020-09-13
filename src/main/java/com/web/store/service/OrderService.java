package com.web.store.service;

import com.sun.javafx.collections.MappingChange;
import com.web.store.OrderMap;
import com.web.store.entity.Order;
import com.web.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(Order order){
        orderRepository.save(order);
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }
    public List<Order> getOrderId(int id){
        List<Order> orders = new ArrayList<>();
        Map<Integer,Order> map = OrderMap.getOrderMap();
        for (Map.Entry<Integer,Order> entry : map.entrySet()){
            if (entry.getValue().getUser().getId()==id){
                orders.add(entry.getValue());
            }
        }
        return orders;
    }
}
