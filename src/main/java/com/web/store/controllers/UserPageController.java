package com.web.store.controllers;


import com.web.store.entity.User;
import com.web.store.entity.goods.*;
import com.web.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sun.reflect.generics.tree.Tree;

import java.util.List;
import java.util.TreeSet;


@Controller
public class UserPageController {

    private UserService userService;
    private PenService penService;
    private HoleService holeService;
    private PaperService paperService;
    private CalculatorService calculatorService;
    private FoldersService foldersService;
    private StaplerService staplerService;
    private TraysService traysService;
    private HouseHoldersService holdersService;

    @Autowired
    public void setHoldersService(HouseHoldersService holdersService) {
        this.holdersService = holdersService;
    }

    @Autowired
    public void setTraysService(TraysService traysService) {
        this.traysService = traysService;
    }

    @Autowired
    public void setStaplerService(StaplerService staplerService) {
        this.staplerService = staplerService;
    }

    @Autowired
    public void setPaperService(PaperService paperService) {
        this.paperService = paperService;
    }

    @Autowired
    public void setFoldersService(FoldersService foldersService) {
        this.foldersService = foldersService;
    }

    @Autowired
    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPenService(PenService penService) {
        this.penService = penService;
    }

    @Autowired
    public void setHoleService(HoleService holeService) {
        this.holeService = holeService;
    }


    @GetMapping("/user-page")
    public String getUserPage(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);
        return "user-page";
    }

    //----------------------------------------------------
    @GetMapping("/user_pages/block_1")
    public String getOneBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Paper> papers = new TreeSet<>(paperService.getAllProducts());
        model.addAttribute("list_papers", papers);

        Paper paper = new Paper();
        model.addAttribute("paper2", paper);

        return "user_pages/block_1";
    }


    @PostMapping("/user_pages/block_1")
    public String addPaperUser(@ModelAttribute("paper2") Paper paper, Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Paper> papers = new TreeSet<>(paperService.getAllProducts());
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

    //-------------------------------------------
    @GetMapping("/user_pages/block_2")
    public String getTwoBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);


        TreeSet<Pen> pens = new TreeSet<>(penService.getAllProducts());
        model.addAttribute("list_pens", pens);
        Pen pen = new Pen();
        model.addAttribute("pen2", pen);
        return "user_pages/block_2";
    }

    @PostMapping("/user_pages/block_2")
    public String addUsePen(Model model, @ModelAttribute("pen2") Pen pen) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Pen> pens = new TreeSet<>(penService.getAllProducts());
        model.addAttribute("list_pens", pens);

        int id = pen.getId();
        Pen penDB = penService.findById(id);
        if ((penDB.getQuantity() - pen.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            penDB.setQuantity(penDB.getQuantity() - pen.getQuantity());
            penService.update(penDB);

            Pen copy = new Pen();
            copy.setQuantity(pen.getQuantity());
            copy.setName(penDB.getName());
            copy.setPrice(penDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        pen.setQuantity(0);
        return "user_pages/block_2";
    }

    //----------------------------------------------
    @GetMapping("/user_pages/block_3")
    public String getThreeBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Stapler> staplers = new TreeSet<>(staplerService.getAllProducts());
        model.addAttribute("list_stapler", staplers);

        Stapler stapler = new Stapler();
        model.addAttribute("stapler2", stapler);
        return "user_pages/block_3";
    }

    @PostMapping("/user_pages/block_3")
    public String addUserStapler(Model model, @ModelAttribute("stapler2") Stapler stapler) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Stapler> staplers = new TreeSet<>(staplerService.getAllProducts());
        model.addAttribute("list_stapler", staplers);

        int id = stapler.getId();
        Stapler staplerDB = staplerService.findById(id);
        if ((staplerDB.getQuantity() - stapler.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            staplerDB.setQuantity(staplerDB.getQuantity() - stapler.getQuantity());
            staplerService.update(staplerDB);

            Stapler copy = new Stapler();
            copy.setQuantity(stapler.getQuantity());
            copy.setName(staplerDB.getName());
            copy.setPrice(staplerDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        stapler.setQuantity(0);
        return "user_pages/block_3";
    }

    //-----------------------------------------------------
    @GetMapping("/user_pages/block_4")
    public String getFourBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Trays> trays = new TreeSet<>(traysService.getAllProducts());
        model.addAttribute("list_trays", trays);

        Trays tray = new Trays();
        model.addAttribute("tray2", tray);
        return "user_pages/block_4";
    }

    @PostMapping("/user_pages/block_4")
    public String addUserTray(Model model, @ModelAttribute("tray2") Trays tray) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Trays> trays = new TreeSet<>(traysService.getAllProducts());
        model.addAttribute("list_trays", trays);

        int id = tray.getId();
        Trays trayDB = traysService.findById(id);
        if ((trayDB.getQuantity() - tray.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            trayDB.setQuantity(trayDB.getQuantity() - tray.getQuantity());
            traysService.update(trayDB);

            Trays copy = new Trays();
            copy.setQuantity(tray.getQuantity());
            copy.setName(trayDB.getName());
            copy.setPrice(trayDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        tray.setQuantity(0);
        return "user_pages/block_4";
    }

    //----------------------------------------
    @GetMapping("/user_pages/block_5")
    public String getFiveBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<HolePuncher> holes = new TreeSet<>(holeService.getAllProducts());
        model.addAttribute("list_holes", holes);

        HolePuncher puncher = new HolePuncher();
        model.addAttribute("hole2", puncher);
        return "user_pages/block_5";
    }

    @PostMapping("/user_pages/block_5")
    public String addUserHole(Model model, @ModelAttribute("hole2") HolePuncher puncher) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<HolePuncher> holes = new TreeSet<>(holeService.getAllProducts());
        model.addAttribute("list_holes", holes);

        int id = puncher.getId();
        HolePuncher puncherDB = holeService.findById(id);
        if ((puncherDB.getQuantity() - puncher.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            puncherDB.setQuantity(puncherDB.getQuantity() - puncher.getQuantity());
            holeService.update(puncherDB);

            HolePuncher copy = new HolePuncher();
            copy.setQuantity(puncher.getQuantity());
            copy.setName(puncherDB.getName());
            copy.setPrice(puncherDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        puncher.setQuantity(0);
        return "user_pages/block_5";
    }

    //----------------------------------------------
    @GetMapping("/user_pages/block_6")
    public String getSixBlock(Model model) {
        String username = getNameOfUser();;
        model.addAttribute("name", username);

        TreeSet<Folders> folders = new TreeSet<>(foldersService.getAllProducts());
        model.addAttribute("list_folders", folders);

        Folders folder = new Folders();
        model.addAttribute("folder2", folder);
        return "user_pages/block_6";
    }

    @PostMapping("/user_pages/block_6")
    public String addUserFolder(Model model, @ModelAttribute("folder2") Folders folder) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Folders> folders = new TreeSet<>(foldersService.getAllProducts());
        model.addAttribute("list_folders", folders);

        int id = folder.getId();
        Folders folderDB = foldersService.findById(id);
        if ((folderDB.getQuantity() - folder.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            folderDB.setQuantity(folderDB.getQuantity() - folder.getQuantity());
            foldersService.update(folderDB);

            Folders copy = new Folders();
            copy.setQuantity(folder.getQuantity());
            copy.setName(folderDB.getName());
            copy.setPrice(folderDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        folder.setQuantity(0);
        return "user_pages/block_6";
    }
//----------------------------------------------

    @GetMapping("/user_pages/block_7")
    public String getSevenBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Calculator> calculators = new TreeSet<>(calculatorService.getAllProducts());
        model.addAttribute("list_calculators", calculators);

        Calculator calculator = new Calculator();
        model.addAttribute("calculator2", calculator);
        return "user_pages/block_7";
    }

    @PostMapping("/user_pages/block_7")
    public String addUserCalculator(Model model, @ModelAttribute("calculator2") Calculator calculator) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<Calculator> calculators = new TreeSet<>(calculatorService.getAllProducts());
        model.addAttribute("list_calculators", calculators);

        int id = calculator.getId();
        Calculator calculatorDB = calculatorService.findById(id);
        if ((calculatorDB.getQuantity() - calculator.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            calculatorDB.setQuantity(calculatorDB.getQuantity() - calculator.getQuantity());
            calculatorService.update(calculatorDB);

            Calculator copy = new Calculator();
            copy.setQuantity(calculator.getQuantity());
            copy.setName(calculatorDB.getName());
            copy.setPrice(calculatorDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        calculator.setQuantity(0);
        return "user_pages/block_7";
    }

    /*-------------------------------------------------------------------------*/
    @GetMapping("/user_pages/block_8")
    public String getEightBlock(Model model) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<HouseHold> holds = new TreeSet<>(holdersService.getAllProducts());
        model.addAttribute("list_holds", holds);

        HouseHold houseHold = new HouseHold();
        model.addAttribute("household2", houseHold);
        return "user_pages/block_8";
    }

    @PostMapping("/user_pages/block_8")
    public String addUserHouseHold(Model model, @ModelAttribute("household2") HouseHold houseHold) {
        String username = getNameOfUser();
        model.addAttribute("name", username);

        TreeSet<HouseHold> holds = new TreeSet<>(holdersService.getAllProducts());
        model.addAttribute("list_holds", holds);

        int id = houseHold.getId();
        HouseHold holdDB = holdersService.findById(id);
        if ((holdDB.getQuantity() - houseHold.getQuantity() >= 0)) {
            // уменьшаем  общее кол-во товара на выбранное кол-во клиентом
            holdDB.setQuantity(holdDB.getQuantity() - houseHold.getQuantity());
            holdersService.update(holdDB);

            HouseHold copy = new HouseHold();
            copy.setQuantity(houseHold.getQuantity());
            copy.setName(holdDB.getName());
            copy.setPrice(holdDB.getPrice());
            User user = userService.getUser(username);
            userService.addProduct(user, copy);
        }
        houseHold.setQuantity(0);
        return "user_pages/block_8";
    }

    private String getNameOfUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.length()>11){
            username = username.substring(0,10)+"...";
        }
        return username;
    }

}
