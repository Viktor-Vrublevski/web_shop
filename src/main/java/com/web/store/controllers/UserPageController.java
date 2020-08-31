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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
        Paper paperDB = paperService.findById(id);
            if ((paperDB.getQuantity() - paper.getQuantity() >= 0)) {
                //В БД уменьшаем  общее кол-во товара на выбранное кол-во клиентом
                paperDB.setQuantity(paperDB.getQuantity() - paper.getQuantity());
                paperService.update(paperDB);

                Paper copy = new Paper();
                copy.setName(paperDB.getName());
                copy.setPrice(paperDB.getPrice());
                copy.setQuantity(paper.getQuantity());

                User user = userService.getUser(username);
                userService.addProduct(user, copy);
            }
        paper.setQuantity(0);
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
        Pen penDB = penService.findById(id);
        if ((penDB.getQuantity() - pen.getQuantity()>=0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            penDB.setQuantity(penDB.getQuantity() - pen.getQuantity());
            penService.update(penDB);

            Pen copy = new Pen();
            copy.setQuantity(pen.getQuantity());
            copy.setName(penDB.getName());
            copy.setPrice(penDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user,copy);
        }
        pen.setQuantity(0);
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
        HolePuncher puncherDB = holeService.findById(id);
        if ((puncherDB.getQuantity() - puncher.getQuantity()>=0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            puncherDB.setQuantity(puncherDB.getQuantity() - puncher.getQuantity());
            holeService.update(puncherDB);

            HolePuncher copy = new HolePuncher();
            copy.setQuantity(puncher.getQuantity());
            copy.setName(puncherDB.getName());
            copy.setPrice(puncherDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user,copy);
        }
        puncher.setQuantity(0);
        return "user_pages/block_5";
    }
    @GetMapping("/user_pages/basket")
    public String getBasket(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("name",username);
        User user = userService.getUser(username);

        List<Product> products = userService.getAllProduct(user);
        model.addAttribute("products",products);
        int count = 0;
        model.addAttribute("incr",count);
        double sum = userService.allSum(user);
        DecimalFormat df = new DecimalFormat("#.##");
        df.format(sum);
        model.addAttribute("allSum",sum);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String day = format.format(date);
        model.addAttribute("date",day);
        return "user_pages/basket";
    }

    @GetMapping("/user_pages/basket/{name}")
    public String delete(@PathVariable("name") String name){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        userService.deleteProduct(user,name);
        return "redirect:/user_pages/basket";
    }

}
