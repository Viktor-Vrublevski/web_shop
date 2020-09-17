package com.web.store.controllers;

import com.web.store.entity.Role;
import com.web.store.entity.User;
import com.web.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginAndRegistrationController {

    public static long countOfUsers=0;
    private final UserService userService;

    @Autowired
    public LoginAndRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHeadPage() {
        countOfUsers++;
        SecurityContextHolder.getContext().setAuthentication(null);
        return "head-page";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "check/login";
    }

    @GetMapping("/registration")
    public String getCheckIn(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "check/registration";
    }

    @PostMapping("/registration")
    public String checkIn(@ModelAttribute("user") User user,Model model) {

        if (userService.loadUserByUsername(user.getLogin())!=null){
            model.addAttribute("exist","Пользователь с таким именем существует");
            return "check/registration";
        }
        if (user.getLogin().length()<4 || user.getLogin().length()>40){
            model.addAttribute("lengthName","Имя не должно быть меньше 4 и" +
                    " больше 40 символов");
            return "check/registration";
        }
        if (user.getPassword().length() <4 || user.getPassword().length()>50){
            model.addAttribute("lengthPassword","Пароль не должен" +
                    " быть меньше 4 и больше 50 символов");
            return "check/registration";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            model.addAttribute("equals","Пароли не совпадают");
            return "check/registration";
        }
        else userService.save(user);
        return "redirect:/success";
    }
    @GetMapping("/success")
    public String success(){
        return "check/success";
    }

    @GetMapping("/user-page")
    public String getUserPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
        return "user-page";
    }

}
