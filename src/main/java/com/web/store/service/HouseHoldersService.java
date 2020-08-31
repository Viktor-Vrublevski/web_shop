package com.web.store.service;

import com.web.store.entity.goods.HouseHold;
import com.web.store.entity.goods.Paper;
import com.web.store.repository.HouseHolderRepository;
import com.web.store.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseHoldersService {
    private HouseHolderRepository holderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(HouseHolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    public void save(HouseHold houseHold) {
        holderRepository.save(houseHold);
    }

    public List<HouseHold> getAllProducts(){
        return  holderRepository.findAll();
    }

    public HouseHold findProduct(String name) {
        return holderRepository.findByName(name);
    }

    public void addProduct(HouseHold houseHold) {
        holderRepository.save(houseHold);
    }

    public void delete(int id) {
        holderRepository.deleteById(id);
    }

    public void update(HouseHold hold) {
        String SQL = "UPDATE householders SET holder_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, hold.getName(), hold.getPrice(), hold.getQuantity(),
                hold.getDescription(),hold.getUrl(), hold.getId());
    }

    public HouseHold findById(int id){
        return holderRepository.findById(id);
    }
}
