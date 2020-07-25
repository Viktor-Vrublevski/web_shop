package com.web.store.controllers;

import com.web.store.entity.User;
import com.web.store.service.SecurityServiceImpl;
import com.web.store.service.UserServiceImpl;
import com.web.store.validator.MyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private MyValidator validator;

    private String login;
    @GetMapping("/")
    public String getHeadPage() {
        return "head-page";
    }

    @GetMapping("/user-page")
    public String getUserPage() {

        return "user-page";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "check/login";
    }

    @PostMapping("/login")
    public String logIn() {

        return "check/login";
    }

    @GetMapping("/registration")
    public String getCheckIn(Model model) {
        model.addAttribute("user",new User());
        return "check/registration";
    }


    @PostMapping("/registration")
    public String checkIn(@ModelAttribute("user") User user, BindingResult bindingResult) {

        validator.validate(user,bindingResult);
        if (bindingResult.hasErrors()){
            System.out.println(user.getLogin()+" "+user.getPassword()+" "+user.getConfirmPassword());
            System.out.println("Problem");
            return "check/registration";
        }
        userService.save(user);
        securityService.autoLogin(user.getLogin(),user.getConfirmPassword());
        return "check/login";
    }
}
