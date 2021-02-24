package com.web.store.controllers.admin;

import com.web.store.dto.DtoObjectProduct;
import com.web.store.entity.User;
import com.web.store.entity.goods.Paper;
import com.web.store.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaperController {


    private final PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/admin/paper/add")
    public String getAddingPage(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("paper",new Paper());
        return "admin/paper/add_product";
    }

    @PostMapping("/admin/paper/add")
    public String addProduct(@ModelAttribute("paper") Paper paper){
        paperService.save(paper);
        return "redirect:/admin/paper/all_product";
    }

    @GetMapping(value = "/admin/paper/all_product" )
    @ResponseBody
    public ResponseEntity<DtoObjectProduct> getProductList(){
        List<Paper> paperList = paperService.getAllProducts();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(new DtoObjectProduct(paperList, user), HttpStatus.OK);
    }

    @GetMapping("/admin/paper/update/{id}")
    public String getUpdate(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("paper",paperService.findById(id));
        return "admin/paper/update_product";
    }
    @PostMapping("/admin/paper/update")
    public String update(@ModelAttribute("paper") Paper paper){
        paperService.update(paper);
        return "redirect:/admin/paper/all_product";
    }


    @GetMapping("/admin/paper/paper/{id}")
    public String getProduct(@PathVariable("id") int id, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",name);
        model.addAttribute("paper", paperService.findById(id));
        return "admin/paper/product";
    }

    @GetMapping("/admin/paper/delete/{id}")
    public String getDelete(@PathVariable("id") int id){
        paperService.delete(id);
        return "redirect:/admin/paper/all_product";
    }

}
