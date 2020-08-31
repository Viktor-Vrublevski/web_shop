package com.web.store.service;


import com.web.store.entity.goods.Stapler;
import com.web.store.repository.StaplerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaplerService {
    private StaplerRepository staplerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(StaplerRepository staplerRepository) {
        this.staplerRepository = staplerRepository;
    }

    public void save(Stapler stapler) {
        staplerRepository.save(stapler);
    }

    public List<Stapler> getAllProducts(){
        return  staplerRepository.findAll();
    }

    public Stapler findProduct(String name) {
        return staplerRepository.findByName(name);
    }

    public void addProduct(Stapler stapler) {
        staplerRepository.save(stapler);
    }

    public void delete(int id) {
        staplerRepository.deleteById(id);
    }

    public void update(Stapler stapler) {
        String SQL = "UPDATE staplers SET stapler_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, stapler.getName(), stapler.getPrice(), stapler.getQuantity(),
                stapler.getDescription(),stapler.getUrl(), stapler.getId());
    }

    public Stapler findById(int id){
        return staplerRepository.findById(id);
    }
}
