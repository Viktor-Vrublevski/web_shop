package com.web.store.repository;

import com.web.store.entity.goods.Stapler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaplerRepository extends JpaRepository<Stapler,Integer> {
    Stapler findByName(String name);
    Stapler findById(int id);
}
