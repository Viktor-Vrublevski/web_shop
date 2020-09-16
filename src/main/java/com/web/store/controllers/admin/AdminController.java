package com.web.store.controllers.admin;

import com.web.store.OrderList;
import com.web.store.entity.Order;
import com.web.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class AdminController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getAdmin(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
      return "admin/admin";
    }
    @GetMapping("/orders")
    public String getOrders(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy | HH:mm");
        String time = sdf.format(date);
        model.addAttribute("date",time);

        List<Order> orders = OrderList.getNoPayedOrders();
        model.addAttribute("list",orders);

        DecimalFormat df = new DecimalFormat("#.##");
        model.addAttribute("df",df);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("form",format);

        int count = 0;
        model.addAttribute("incr", count);

        int id = 0;
        model.addAttribute("order_id",id);
        return "admin/admin_orders";
    }

    @PostMapping("/orders")
    public String payOrders(@ModelAttribute("order_id") int order_id,Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<Order> list = OrderList.getOrderList();
        for (Order order : list){
            if (order.getId()==order_id){
                order.setStatus(true);
                orderService.update(order);
                break;
            }
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy | HH:mm");
        String time = sdf.format(date);
        model.addAttribute("date",time);

        List<Order> orders = OrderList.getNoPayedOrders();
        model.addAttribute("list",orders);

        DecimalFormat df = new DecimalFormat("#.##");
        model.addAttribute("df",df);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("form",format);

        int count = 0;
        model.addAttribute("incr", count);

        int id = 0;
        model.addAttribute("order_id",id);

        return "admin/admin_orders";
    }

}
