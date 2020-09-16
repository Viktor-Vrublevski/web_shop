package com.web.store.service;

import com.web.store.OrderList;
import com.web.store.entity.Order;
import com.web.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        List<Order> list = OrderList.getOrderList();
        for (Order order: list){
            if (order.getUser().getId()==id){
                orders.add(order);
            }
        }
        return orders;
    }

    public void update(Order order){
        String SQL = "UPDATE web_store.orders SET date_order=?,  payment_status=?, cost=? WHERE id=?";
        jdbcTemplate.update(SQL,order.getDate(),order.isStatus(),order.getAllCost(),order.getId());
    }
}
