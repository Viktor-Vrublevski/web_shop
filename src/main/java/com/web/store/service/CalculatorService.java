package com.web.store.service;

import com.web.store.entity.goods.Calculator;
import com.web.store.entity.goods.Paper;
import com.web.store.repository.CalculatorRepository;
import com.web.store.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {
    private CalculatorRepository calculatorRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(CalculatorRepository calculatorRepository) {
        this.calculatorRepository = calculatorRepository;
    }

    public void save(Calculator calculator) {
        calculatorRepository.save(calculator);
    }

    public List<Calculator> getAllProducts(){
        return  calculatorRepository.findAll();
    }

    public Calculator findProduct(String name) {
        return calculatorRepository.findByName(name);
    }

    public void addProduct(Calculator calculator) {
        calculatorRepository.save(calculator);
    }

    public void delete(int id) {
        calculatorRepository.deleteById(id);
    }

    public void update(Calculator calculator) {
        String SQL = "UPDATE web_store.calculators SET calculator_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, calculator.getName(), calculator.getPrice(), calculator.getQuantity(),
                calculator.getDescription(),calculator.getUrl(), calculator.getId());
    }

    public Calculator findById(int id){
        return calculatorRepository.findById(id);
    }
}
