package com.web.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeadPageController {

    @GetMapping("/pageOne")
    public String getOnePage(){
        return "head_pages/page_one";
    }
}
