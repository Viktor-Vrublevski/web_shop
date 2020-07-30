package com.web.store.controllers;

import com.web.store.entity.User;
import com.web.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getHeadPage() {
        return "head-page";
    }


    @GetMapping("/registration")
    public String getCheckIn(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "check/registration";
    }

    @PostMapping("/registration")
    public String checkIn(@ModelAttribute("user") User user,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "check/registration";
        }
        if (userService.loadUserByUsername(user.getLogin())!=null){
            model.addAttribute("exist","Пользователь с таким именем существует");
            return "check/registration";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            System.out.println(user.getPassword());
            model.addAttribute("equals","Пароли не совпадают");
            return "check/registration";
        }
        else
            System.out.println(user.getLogin());
            userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "check/login";
    }

    @GetMapping("/user-page")
    public String getUserPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
        return "user-page";
    }

}
