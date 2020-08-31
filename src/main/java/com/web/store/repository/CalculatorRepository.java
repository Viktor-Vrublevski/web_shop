package com.web.store.repository;

import com.web.store.entity.goods.Calculator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatorRepository  extends JpaRepository<Calculator,Integer> {
    Calculator findByName(String name);
    Calculator findById(int id);
}
