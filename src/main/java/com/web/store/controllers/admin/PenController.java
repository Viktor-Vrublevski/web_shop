package com.web.store.controllers.admin;

import com.web.store.entity.goods.HolePuncher;
import com.web.store.entity.goods.Pen;
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
public class PenController {

   private PenService penService;

   @Autowired
    public void setPenService(PenService penService) {
        this.penService = penService;
    }

    @GetMapping("/admin/pens/add")
    public String getAddingPage(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("pen",new Pen());
        return "admin/pens/add_pen";
    }

    @PostMapping("/admin/pens/add")
    public String addProduct(@ModelAttribute("pen")Pen pen){
        penService.save(pen);
        return "redirect:/admin/pens/all_pens";
    }

    @GetMapping("/admin/pens/all_pens" )
    public String getProductList(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        List<Pen> pens = penService.getAllProducts();
        model.addAttribute("pens", pens);
        return "admin/pens/all_pens";
    }

    @GetMapping("/admin/pens/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("pen",penService.findById(id));
        return "admin/pens/update_pen";
    }
    @PostMapping("/admin/pens/update")
    public String update(@ModelAttribute("pen") Pen pen){
        penService.update(pen);
        return "redirect:/admin/pens/all_pens";
    }


    @GetMapping("/admin/pens/pen/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("pen", penService.findById(id));
        return "admin/pens/pen_product";
    }

    @GetMapping("/admin/pens/delete/{id}")
    public String delete(@PathVariable("id") int id){
        penService.delete(id);
        return "redirect:/admin/pens/all_pens";
    }
}
