package com.web.store.controllers;

import com.web.store.entity.Product;
import com.web.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String getAdmin(){
      return "admin/admin";
    }
    @GetMapping("/admin/add")
    public String getAddingPage(Model model){
        model.addAttribute("product",new Product());
        return "admin/add_product";
    }

    @PostMapping("/admin/add")
    public String addProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/admin/all_product";
    }

    @GetMapping("/admin/all_product" )
    public String getProductList(Model model){
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products",productList);
        return "admin/all_product";
    }

    @GetMapping("/admin/update/{id}")
    public String getUpdate(@PathVariable("id") int id,Model model){
        model.addAttribute("product",productService.findById(id));
        return "admin/update_product";
    }
    @PostMapping("/admin/update")
    public String update(@ModelAttribute("product") Product product){
        System.out.println("метод update класса AdminController  "+product.getId());
        productService.update(product);
        return "redirect:/admin/all_product";
    }


    @GetMapping("/admin/product/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.findById(id));
        return "admin/product";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") int id,Model model){
       productService.delete(id);
        return "redirect:/admin/all_product";
    }
}
