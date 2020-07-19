package com.web.store.controllers;

import com.web.store.entity.User;
import com.web.store.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String getHeadPage(){
        return "head-page";
    }
    @GetMapping("/user-page")
    public String getUserPage(){
        return "user-page";
    }
    @GetMapping("/registration")
    public String getCheckIn(){
        return "check/checkin";
    }

    @PostMapping("/registration")
    public String checkIn(Model model, @RequestParam String username, @RequestParam String password,
                          @RequestParam String confirmPassword){
        if (!userService.findByUsername(username) & password.equals(confirmPassword)){
            User user = new User(username,password);
            userService.save(user);
            return "redirect:/user-page";
        }
        return "check/checkin";
    }
}
