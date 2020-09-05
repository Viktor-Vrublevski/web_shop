package com.web.store.controllers.admin;

import com.web.store.entity.goods.Calculator;
import com.web.store.entity.goods.Pen;
import com.web.store.service.CalculatorService;
import com.web.store.service.PenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CalculatorController {
    private CalculatorService calculatorService;

    @Autowired
    public void setPenService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/admin/calculators/add")
    public String getAddingPage(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("calculator",new Calculator());
        return "admin/calculators/add_calculator";
    }

    @PostMapping("/admin/calculators/add")
    public String addProduct(@ModelAttribute("calculator")Calculator calculator){
        calculatorService.save(calculator);
        return "redirect:/admin/calculators/all_calculator";
    }

    @GetMapping("/admin/calculators/all_calculator" )
    public String getProductList(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        List<Calculator> calculators = calculatorService.getAllProducts();
        model.addAttribute("calculators", calculators);
        return "admin/calculators/all_calculator";
    }

    @GetMapping("/admin/calculators/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("calculator",calculatorService.findById(id));
        return "admin/calculators/update_calculator";
    }
    @PostMapping("/admin/calculators/update")
    public String update(@ModelAttribute("calculator") Calculator calculator, Model model){
       calculatorService.update(calculator);
        return "redirect:/admin/calculators/all_calculator";
    }


    @GetMapping("/admin/calculators/calculator/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("calculator", calculatorService.findById(id));
        return "admin/calculators/calculate_product";
    }

    @GetMapping("/admin/calculator/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model){
        calculatorService.delete(id);
        return "redirect:/admin/calculators/all_calculator";
    }
}
