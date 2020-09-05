package com.web.store.controllers.admin;


import com.web.store.entity.goods.HolePuncher;
import com.web.store.service.HoleService;
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
public class HoleController {

    private HoleService holeService;

    @Autowired
    public void setHoleService(HoleService holeService) {
        this.holeService = holeService;
    }
    @GetMapping("/admin/holes/add")
    public String getAddingPage(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("hole",new HolePuncher());
        return "admin/holes/add_holes";
    }

    @PostMapping("/admin/holes/add")
    public String addProduct(@ModelAttribute("hole")HolePuncher puncher){
        holeService.save(puncher);
        return "redirect:/admin/holes/all_holes";
    }

    @GetMapping("/admin/holes/all_holes" )
    public String getProductList(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        List<HolePuncher> holePunchers = holeService.getAllProducts();
        model.addAttribute("holes", holePunchers);
        return "admin/holes/all_holes";
    }

    @GetMapping("/admin/holes/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("puncher",holeService.findById(id));
        return "admin/holes/update_hole";
    }
    @PostMapping("/admin/holes/update")
    public String update(@ModelAttribute("puncher") HolePuncher puncher){
        holeService.update(puncher);
        return "redirect:/admin/holes/all_holes";
    }


    @GetMapping("/admin/holes/hole/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("puncher", holeService.findById(id));
        return "admin/holes/hole_product";
    }

    @GetMapping("/admin/holes/delete/{id}")
    public String delete(@PathVariable("id") int id){
        holeService.delete(id);
        return "redirect:/admin/holes/all_holes";
    }
}
