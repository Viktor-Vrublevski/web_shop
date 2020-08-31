package com.web.store.controllers;


import com.web.store.entity.goods.Calculator;
import com.web.store.entity.goods.HouseHold;
import com.web.store.service.CalculatorService;
import com.web.store.service.HoleService;
import com.web.store.service.HouseHoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HouseHoldController {
    private HouseHoldersService holdersService;

    @Autowired
    public void setPenService(HouseHoldersService holdersService) {
        this.holdersService = holdersService;
    }

    @GetMapping("/admin/holders/add")
    public String getAddingPage(Model model){
        model.addAttribute("holder",new HouseHold());
        return "admin/holders/add_holder";
    }

    @PostMapping("/admin/holders/add")
    public String addProduct(@ModelAttribute("holder")HouseHold houseHold){
        holdersService.save(houseHold);
        return "redirect:/admin/holders/all_holder";
    }

    @GetMapping("/admin/holders/all_holder" )
    public String getProductList(Model model){
        List<HouseHold> holds = holdersService.getAllProducts();
        model.addAttribute("holds", holds);
        return "admin/holders/all_holder";
    }

    @GetMapping("/admin/holders/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        model.addAttribute("holder",holdersService.findById(id));
        return "admin/holders/update_holder";
    }
    @PostMapping("/admin/holders/update")
    public String update(@ModelAttribute("holder") HouseHold houseHold){
        holdersService.update(houseHold);
        return "redirect:/admin/holders/all_holder";
    }


    @GetMapping("/admin/holders/holder/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("holder", holdersService.findById(id));
        return "admin/holders/holder_product";
    }

    @GetMapping("/admin/holders/delete/{id}")
    public String delete(@PathVariable("id") int id){
        holdersService.delete(id);
        return "redirect:/admin/holders/all_holder";
    }
}
