package com.web.store.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class AdminController {

    @GetMapping("/admin")
    public String getAdmin(){
      return "admin/admin";
    }

}
