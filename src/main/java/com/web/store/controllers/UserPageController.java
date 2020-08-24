package com.web.store.controllers;

import com.web.store.entity.User;
import com.web.store.entity.goods.HolePuncher;
import com.web.store.entity.goods.Paper;
import com.web.store.entity.goods.Pen;
import com.web.store.entity.goods.Product;
import com.web.store.service.HoleService;
import com.web.store.service.PaperService;
import com.web.store.service.PenService;
import com.web.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class UserPageController {

    private UserService userService;
    private PenService penService;
    private HoleService holeService;
    private PaperService paperService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPapertService(PaperService paperService) {
        this.paperService = paperService;
    }

    @Autowired
    public void setPenService(PenService penService) {
        this.penService = penService;
    }

    @Autowired
    public void setHoleService(HoleService holeService) {
        this.holeService = holeService;
    }

    @GetMapping("/user_pages/block_1")
    public String getOneBlock(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<Paper> papers = paperService.getAllProducts();
        model.addAttribute("list_papers", papers);
        Paper paper = new Paper();
        model.addAttribute("paper2",paper);

        return "user_pages/block_1";
    }


    @PostMapping("/user_pages/block_1")
    public String addPaperUser(@ModelAttribute("paper2")Paper paper,Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<Paper> papers = paperService.getAllProducts();
        model.addAttribute("list_papers", papers);

        int id = paper.getId();
        Paper paper1 = paperService.findById(id);
            if ((paper1.getQuantity() - paper.getQuantity() >= 0)) {
                // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
                paper1.setQuantity(paper1.getQuantity() - paper.getQuantity());
                paperService.update(paper1);

                paper.setName(paper1.getName());
                paper.setPrice(paper1.getPrice());
                User user = userService.getUser(username);
                userService.addPaper(user, paper);
            }
        return "user_pages/block_1";
    }

    @GetMapping("/user_pages/block_2")
    public String getTwoBlock(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<Pen> pens = penService.getAllProducts();
        model.addAttribute("list_pens", pens);
        Pen pen = new Pen();
        model.addAttribute("pen2",pen);
        return "user_pages/block_2";
    }
    @PostMapping("/user_pages/block_2")
    public String addUsePen(Model model,@ModelAttribute("pen2")Pen pen){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<Pen> pens = penService.getAllProducts();
        model.addAttribute("list_pens", pens);

        int id = pen.getId();
        Pen pen1 = penService.findById(id);
        if ((pen1.getQuantity() - pen.getQuantity()>=0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            pen1.setQuantity(pen1.getQuantity() - pen.getQuantity());
            penService.update(pen1);

            pen.setName(pen1.getName());
            pen.setPrice(pen1.getPrice());
            User user = userService.getUser(username);
            userService.addPen(user,pen);
        }
        return "user_pages/block_2";
    }

    @GetMapping("/user_pages/block_5")
    public String getFiveBlock(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<HolePuncher> holes = holeService.getAllProducts();
        model.addAttribute("list_holes", holes);

        HolePuncher puncher = new HolePuncher();
        model.addAttribute("hole2",puncher);
        return "user_pages/block_5";
    }
    @PostMapping("/user_pages/block_5")
    public String addUserHole(Model model, @ModelAttribute("hole2")HolePuncher puncher){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);

        List<HolePuncher> holes = holeService.getAllProducts();
        model.addAttribute("list_holes", holes);

        int id = puncher.getId();
        HolePuncher puncher1 = holeService.findById(id);
        if ((puncher1.getQuantity() - puncher.getQuantity()>=0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            puncher1.setQuantity(puncher1.getQuantity() - puncher.getQuantity());
            holeService.update(puncher1);

            puncher.setName(puncher1.getName());
            puncher.setPrice(puncher1.getPrice());
            User user = userService.getUser(username);
            userService.addHole(user,puncher);
        }

        return "user_pages/block_5";
    }
    @GetMapping("/user_pages/basket")
    public String getBasket(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
        User user = userService.getUser(username);
        Set<Product> products = userService.allProduct(user);
        model.addAttribute("products",products);
        return "user_pages/basket";
    }
}
