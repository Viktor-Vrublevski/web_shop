package com.web.store.controllers;

import com.web.store.entity.goods.Calculator;
import com.web.store.entity.goods.Stapler;
import com.web.store.service.CalculatorService;
import com.web.store.service.StaplerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StaplerController {
    private StaplerService staplerService;

    @Autowired
    public void setPenService(StaplerService staplerService) {
        this.staplerService = staplerService;
    }

    @GetMapping("/admin/staplers/add")
    public String getAddingPage(Model model){
        model.addAttribute("stapler",new Stapler());
        return "admin/staplers/add_stapler";
    }

    @PostMapping("/admin/staplers/add")
    public String addProduct(@ModelAttribute("stapler")Stapler stapler){
        staplerService.save(stapler);
        return "redirect:/admin/staplers/all_stapler";
    }

    @GetMapping("/admin/staplers/all_stapler" )
    public String getProductList(Model model){
        List<Stapler> staplers = staplerService.getAllProducts();
        model.addAttribute("staplers", staplers);
        return "admin/staplers/all_stapler";
    }

    @GetMapping("/admin/staplers/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        model.addAttribute("stapler",staplerService.findById(id));
        return "admin/staplers/update_stapler";
    }
    @PostMapping("/admin/staplers/update")
    public String update(@ModelAttribute("stapler") Stapler stapler){
        staplerService.update(stapler);
        return "redirect:/admin/staplers/all_stapler";
    }


    @GetMapping("/admin/staplers/stapler/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("stapler", staplerService.findById(id));
        return "admin/staplers/stapler_product";
    }

    @GetMapping("/admin/staplers/delete/{id}")
    public String delete(@PathVariable("id") int id){
        staplerService.delete(id);
        return "redirect:/admin/staplers/all_stapler";
    }
}
