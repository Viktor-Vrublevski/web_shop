package com.web.store.controllers;


import com.web.store.entity.goods.HolePuncher;
import com.web.store.entity.goods.Paper;
import com.web.store.entity.goods.Pen;
import com.web.store.service.HoleService;
import com.web.store.service.PaperService;
import com.web.store.service.PenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HeadPageController {

    private PenService penService;
    private HoleService holeService;
    private PaperService paperService;

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

    @GetMapping("/head_pages/blockOne")
    public String getOneBlock(Model model){
        List<Paper> papers = paperService.getAllProducts();
        model.addAttribute("list_papers", papers);
        return "head_pages/block_one";
    }

    @GetMapping("head_pages/blockTwo")
    public String getTwoBlock(Model model){
        List<Pen> pens = penService.getAllProducts();
        model.addAttribute("list_pens", pens);
        return "head_pages/block_two";
    }

    @GetMapping("/head_pages/blockFive")
    public String getFiveBlock(Model model){
        List<HolePuncher> punchers = holeService.getAllProducts();
        model.addAttribute("list_holes", punchers);
        return "head_pages/block_five";
    }
}
