package com.web.store.controllers;

import com.web.store.entity.goods.Calculator;
import com.web.store.entity.goods.Trays;
import com.web.store.service.CalculatorService;
import com.web.store.service.TraysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TrayController {

    private TraysService traysService;

    @Autowired
    public void setPenService(TraysService traysService) {
        this.traysService = traysService;
    }

    @GetMapping("/admin/trays/add")
    public String getAddingPage(Model model){
        model.addAttribute("tray",new Trays());
        return "admin/trays/add_tray";
    }

    @PostMapping("/admin/trays/add")
    public String addProduct(@ModelAttribute("tray")Trays tray){
        traysService.save(tray);
        return "redirect:/admin/trays/all_tray";
    }

    @GetMapping("/admin/trays/all_tray" )
    public String getProductList(Model model){
        List<Trays> trays = traysService.getAllProducts();
        model.addAttribute("trays", trays);
        return "admin/trays/all_tray";
    }

    @GetMapping("/admin/trays/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        model.addAttribute("tray",traysService.findById(id));
        return "admin/trays/update_tray";
    }
    @PostMapping("/admin/trays/update")
    public String update(@ModelAttribute("tray") Trays tray){
        traysService.update(tray);
        return "redirect:/admin/trays/all_tray";
    }


    @GetMapping("/admin/trays/tray/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("tray", traysService.findById(id));
        return "admin/trays/tray_product";
    }

    @GetMapping("/admin/trays/delete/{id}")
    public String delete(@PathVariable("id") int id){
        traysService.delete(id);
        return "redirect:/admin/trays/all_tray";
    }
}
