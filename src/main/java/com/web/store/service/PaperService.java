package com.web.store.service;

import com.web.store.entity.goods.Paper;
import com.web.store.entity.goods.Product;
import com.web.store.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService{

    private PaperRepository paperRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    public void save(Paper paper) {
        paperRepository.save(paper);
    }

    public List<Paper> getAllProducts(){
        return  paperRepository.findAll();
    }

    public Paper findProduct(String name) {
        return paperRepository.findByName(name);
    }

    public void addProduct(Paper paper) {
        paperRepository.save(paper);
    }

    public void delete(int id) {
        paperRepository.deleteById(id);
    }

    public void update(Paper paper) {
        String SQL = "UPDATE papers SET paper_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, paper.getName(), paper.getPrice(), paper.getQuantity(),
                paper.getDescription(),paper.getUrl(), paper.getId());
    }

    public Paper findById(int id){
        return paperRepository.findById(id);
    }

}
