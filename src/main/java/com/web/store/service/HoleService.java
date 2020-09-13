package com.web.store.service;

import com.web.store.entity.goods.HolePuncher;
import com.web.store.repository.HoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoleService {

    private HoleRepository holeRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setHoleRepository(HoleRepository holeRepository) {
        this.holeRepository = holeRepository;
    }

    public void save(HolePuncher puncher) {
        holeRepository.save(puncher);
    }

    public List<HolePuncher> getAllProducts(){
        return  holeRepository.findAll();
    }

    public void addProduct(HolePuncher puncher) {
        holeRepository.save(puncher);
    }

    public void delete(int id) {
        holeRepository.deleteById(id);
    }

    public void update(HolePuncher puncher){

        String SQL = "UPDATE web_store.holes SET hole_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL,puncher.getName(),puncher.getPrice(),puncher.getQuantity(),
                puncher.getDescription(),puncher.getUrl(),puncher.getId());
    }

    public HolePuncher findById(int id){
        return holeRepository.findById(id);
    }
}
