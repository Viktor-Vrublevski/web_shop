package com.web.store.repository;

import com.web.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findByName(String name);
    Product findById(int id);
}
