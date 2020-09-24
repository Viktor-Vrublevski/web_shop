package com.web.store.controllers;


import com.web.store.entity.goods.*;
import com.web.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.TreeSet;

@Controller
public class HeadPageController {

    private PenService penService;
    private HoleService holeService;
    private PaperService paperService;
    private CalculatorService calculatorService;
    private FoldersService foldersService;
    private HouseHoldersService houseHoldersService;
    private StaplerService staplerService;
    private TraysService traysService;

    @Autowired
    public void setPaperService(PaperService paperService) {
        this.paperService = paperService;
    }

    @Autowired
    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Autowired
    public void setFoldersService(FoldersService foldersService) {
        this.foldersService = foldersService;
    }

    @Autowired
    public void setHouseHoldersService(HouseHoldersService houseHoldersService) {
        this.houseHoldersService = houseHoldersService;
    }

    @Autowired
    public void setStaplerService(StaplerService staplerService) {
        this.staplerService = staplerService;
    }

    @Autowired
    public void setTraysService(TraysService traysService) {
        this.traysService = traysService;
    }


    @Autowired
    public void setPenService(PenService penService) {
        this.penService = penService;
    }

    @Autowired
    public void setHoleService(HoleService holeService) {
        this.holeService = holeService;
    }

    public static long countOfUsers=0;
    @GetMapping("/")
    public String getHeadPage() {
        SecurityContextHolder.getContext().setAuthentication(null);
         countOfUsers++;
        return "head-page";
    }

    @GetMapping("/head_pages/blockOne")
    public String getOneBlock(Model model){
        TreeSet<Paper> papers = new TreeSet<>(paperService.getAllProducts());
        model.addAttribute("list_papers", papers);
        return "head_pages/block_one";
    }

    @GetMapping("head_pages/blockTwo")
    public String getTwoBlock(Model model){
        TreeSet<Pen> pens = new TreeSet<>(penService.getAllProducts());
        model.addAttribute("list_pens", pens);
        return "head_pages/block_two";
    }

    @GetMapping("head_pages/blockThree")
    public String getThreeBlock(Model model){
        TreeSet<Stapler> staplers = new TreeSet<>(staplerService.getAllProducts());
        model.addAttribute("list_staplers", staplers);
        return "head_pages/block_three";
    }

    @GetMapping("head_pages/blockFour")
    public String getFourBlock(Model model){
        TreeSet<Trays> trays = new TreeSet<>(traysService.getAllProducts());
        model.addAttribute("list_trays", trays);
        return "head_pages/block_four";
    }

    @GetMapping("/head_pages/blockFive")
    public String getFiveBlock(Model model){
        TreeSet<HolePuncher> punchers = new TreeSet<>(holeService.getAllProducts());
        model.addAttribute("list_holes", punchers);
        return "head_pages/block_five";
    }

    @GetMapping("/head_pages/blockSix")
    public String getSixBlock(Model model){
        TreeSet<Folders> folders = new TreeSet<>(foldersService.getAllProducts());
        model.addAttribute("list_folders",folders);
        return "head_pages/block_six";
    }

    @GetMapping("/head_pages/blockSeven")
    public String getSevenBlock(Model model){
        TreeSet<Calculator> calculators = new TreeSet<>(calculatorService.getAllProducts());
        model.addAttribute("list_calculators", calculators);
        return "head_pages/block_seven";
    }

    @GetMapping("/head_pages/blockEight")
    public String getEightBlock(Model model){
        TreeSet<HouseHold> holds = new TreeSet<>(houseHoldersService.getAllProducts());
        model.addAttribute("list_holds", holds);
        return "head_pages/block_eight";
    }
}
