package com.web.store.controllers;

import com.web.store.entity.goods.Calculator;
import com.web.store.entity.goods.Folders;
import com.web.store.service.CalculatorService;
import com.web.store.service.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FoldersController {
    private FoldersService foldersService;

    @Autowired
    public void setPenService(FoldersService foldersService) {
        this.foldersService= foldersService;
    }

    @GetMapping("/admin/folders/add")
    public String getAddingPage(Model model){
        model.addAttribute("folder",new Folders());
        return "admin/folders/add_folder";
    }

    @PostMapping("/admin/folders/add")
    public String addProduct(@ModelAttribute("folder")Folders folders){
       foldersService.save(folders);
        return "redirect:/admin/folders/all_folders";
    }

    @GetMapping("/admin/folders/all_folders" )
    public String getProductList(Model model){
        List<Folders> folders = foldersService.getAllProducts();
        model.addAttribute("folders", folders);
        return "admin/folders/all_folders";
    }

    @GetMapping("/admin/folders/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        model.addAttribute("folder",foldersService.findById(id));
        return "admin/folders/update_folder";
    }
    @PostMapping("/admin/folders/update")
    public String update(@ModelAttribute("folder") Folders folders){
        foldersService.update(folders);
        return "redirect:/admin/folders/all_folders";
    }


    @GetMapping("/admin/folders/folder/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("folder", foldersService.findById(id));
        return "admin/folders/folders_product";
    }

    @GetMapping("/admin/folders/delete/{id}")
    public String delete(@PathVariable("id") int id){
        foldersService.delete(id);
        return "redirect:/admin/folders/all_folders";
    }
}
