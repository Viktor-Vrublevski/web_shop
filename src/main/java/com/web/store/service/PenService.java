package com.web.store.service;

import com.web.store.entity.goods.HolePuncher;
import com.web.store.entity.goods.Pen;
import com.web.store.repository.PenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenService{

    private PenRepository penRepository;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setPenRepository(PenRepository penRepository) {
        this.penRepository = penRepository;
    }


    public void save(Pen pen) {
        penRepository.save(pen);
    }

    public List<Pen> getAllProducts() {
        return penRepository.findAll();
    }

    public void addProduct(Pen pen) {
        penRepository.save(pen);
    }

    public void delete(int id) {
        penRepository.deleteById(id);
    }


    public void update(Pen pen) {
        String SQL = "UPDATE pens SET pen_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, pen.getName(), pen.getPrice(), pen.getQuantity(),
                pen.getDescription(), pen.getUrl(), pen.getId());
    }

    public Pen findById(int id){
        return penRepository.findById(id);
    }
}
