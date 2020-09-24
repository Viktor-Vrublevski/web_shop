package com.web.store.controllers.admin;

import com.web.store.OrderList;
import com.web.store.controllers.HeadPageController;
import com.web.store.controllers.LoginAndRegistrationController;
import com.web.store.entity.Order;
import com.web.store.entity.User;
import com.web.store.service.OrderService;
import com.web.store.service.UserService;
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
    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/admin")
    public String getAdmin(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name", username);
        return "admin/admin";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name", username);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy | HH:mm");
        String time = sdf.format(date);
        model.addAttribute("dateFormat", sdf);
        model.addAttribute("date", time);

        List<Order> orders = orderService.getNoPayedOrders();
        model.addAttribute("list", orders);

        List<Order> paidOrders = orderService.getAllPaidOrders();
        model.addAttribute("paidOrders", paidOrders);

        DecimalFormat df = new DecimalFormat("#.##");
        model.addAttribute("df", df);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("form", format);

        int count = 0;
        model.addAttribute("incr", count);

        long countUsers = HeadPageController.countOfUsers;
        model.addAttribute("countView", countUsers);

        List<User> users = userService.getAllUsers();
        model.addAttribute("countUser", users.size());

        int id = 0;
        model.addAttribute("order_id", id);
        return "admin/admin_orders";
    }

    @PostMapping("/orders")
    public String payOrders(@ModelAttribute("order_id") int order_id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name", username);

        List<Order> list = OrderList.getOrderList();
        for (Order order : list) {
            if (order.getId() == order_id) {
                order.setStatus(true);
                orderService.update(order);
                break;
            }
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy | HH:mm");
        String time = sdf.format(date);
        model.addAttribute("dateFormat", sdf);
        model.addAttribute("date", time);

        List<Order> orders = orderService.getNoPayedOrders();
        model.addAttribute("list", orders);

        List<Order> paidOrders = orderService.getAllPaidOrders();
        model.addAttribute("paidOrders", paidOrders);

        DecimalFormat df = new DecimalFormat("#.##");
        model.addAttribute("df", df);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("form", format);

        int count = 0;
        model.addAttribute("incr", count);

        long countUsers = HeadPageController.countOfUsers;
        model.addAttribute("countView", countUsers);

        List<User> users = userService.getAllUsers();
        model.addAttribute("countUser", users.size());

        int id = 0;
        model.addAttribute("order_id", id);

        return "admin/admin_orders";
    }

}
