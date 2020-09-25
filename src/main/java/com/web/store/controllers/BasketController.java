package com.web.store.controllers;

import com.web.store.OrderList;
import com.web.store.UserOrderMap;
import com.web.store.entity.Order;
import com.web.store.entity.User;
import com.web.store.entity.goods.Product;
import com.web.store.service.OrderService;
import com.web.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BasketController {

    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user_pages/basket")
    public String getBasket(Model model) {
       String name = getNameOfUser(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("name", name);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        model.addAttribute("user",user);

        List<Product> products = userService.getAllProduct(user);
        model.addAttribute("products", products);

        int count = 0;
        model.addAttribute("incr", count);
        double sum = userService.allSum(user);
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(sum);
        model.addAttribute("allSum", sum);
        return "user_pages/basket";
    }

    @GetMapping("/user_pages/basket/{productName}")
    public String delete(@PathVariable("productName") String name) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        userService.deleteProduct(user, name);
        return "redirect:/user_pages/basket";
    }

    @PostMapping("/user_pages/basket")
    public String addRequisites(@ModelAttribute("user") User user, Model model) {
        String username = getNameOfUser(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("name", username);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user1 = userService.getUser(name);

        user1.setOrganization(user.getOrganization());
        user1.setAddress(user.getAddress());
        user1.setUNN(user.getUNN());
        user1.setNameBank(user.getNameBank());
        user1.setAddressBank(user.getAddressBank());
        user1.setBIC(user.getBIC());
        user1.setIBAN(user.getIBAN());
        user1.setEmail(user.getEmail());
        user1.setTel(user.getTel());
        user1.setAddress_store(user.getAddress_store());
        userService.update(user1);

        double allSum = 0;
        model.addAttribute("allSum", allSum);

        Order order = new Order();
        List<Product> products = userService.getAllProduct(user1);
        order.setProducts(products);
        order.setUser(user1);
        order.setDate(new Date());
        double sum = userService.allSum(user1);
        order.setAllCost(sum);
        order.setStatus(false);
        orderService.save(order);
        OrderList.getOrderList().add(order);
        UserOrderMap.getInstance().get(user1.getId()).setProducts(null);
        return "user_pages/basket";
    }

//--------------------------------------------------
    private String getNameOfUser(String username){
        if (username.length()>11){
            username = username.substring(0,10)+"...";
        }
        return username;
    }
}
