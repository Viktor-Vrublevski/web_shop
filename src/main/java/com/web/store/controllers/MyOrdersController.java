package com.web.store.controllers;

import com.web.store.entity.Order;
import com.web.store.entity.User;
import com.web.store.service.OrderService;
import com.web.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class MyOrdersController {

    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("user_pages/orders")
    public String getMyAccounts(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
        User user = userService.getUser(username);

        List<Order> paidOrders = orderService.getPaidOrdersByUserId(user.getId());
        for (Order or : paidOrders){
            System.out.println(or.getId());
        }
        model.addAttribute("paidOrders",paidOrders);

        List<Order> orders = orderService.getOrderId(user.getId());
        model.addAttribute("list",orders);

        DecimalFormat df = new DecimalFormat("#.##");
        model.addAttribute("df",df);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("form1",format);

        SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
        model.addAttribute("form2",form);

        int count = 0;
        model.addAttribute("incr", count);

        int id = 0;
        model.addAttribute("id_order",id);
        return "user_pages/my_accounts";
    }

    @PostMapping("user_pages/orders")
    public String sendToArchive(@ModelAttribute("id_order") int id_order,Model model){
        Order order = orderService.getOrderById(id_order);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
        User user = userService.getUser(username);
        user.getPayedOrders().add(order);
        orderService.deleteOrderById(id_order);

        List<Order> payedOrders = orderService.getPaidOrdersByUserId(user.getId());
        model.addAttribute("paidOrders",payedOrders);

        List<Order> orders = orderService.getOrderId(user.getId());
        model.addAttribute("list",orders);


        DecimalFormat df = new DecimalFormat("#.##");
        model.addAttribute("df",df);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("form1",format);

        SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
        model.addAttribute("form2",form);

        int count = 0;
        model.addAttribute("incr", count);

        int id = 0;
        model.addAttribute("id",id);

        return "user_pages/my_accounts";
    }
}
