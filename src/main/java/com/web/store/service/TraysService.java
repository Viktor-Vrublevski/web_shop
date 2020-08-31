package com.web.store.service;

import com.web.store.entity.goods.Paper;
import com.web.store.entity.goods.Trays;
import com.web.store.repository.TraysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraysService {
    private TraysRepository traysRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(TraysRepository traysRepository) {
        this.traysRepository = traysRepository;
    }

    public void save(Trays trays) {
        traysRepository.save(trays);
    }

    public List<Trays> getAllProducts(){
        return  traysRepository.findAll();
    }

    public Trays findProduct(String name) {
        return traysRepository.findByName(name);
    }

    public void addProduct(Trays trays) {
        traysRepository.save(trays);
    }

    public void delete(int id) {
        traysRepository.deleteById(id);
    }

    public void update(Trays tray) {
        String SQL = "UPDATE trays SET trays_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, tray.getName(), tray.getPrice(), tray.getQuantity(),
                tray.getDescription(),tray.getUrl(), tray.getId());
    }

    public Trays findById(int id){
        return traysRepository.findById(id);
    }
}
