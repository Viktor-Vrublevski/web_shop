package com.web.store.repository;


import com.web.store.entity.goods.Trays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraysRepository extends JpaRepository<Trays,Integer> {
    Trays findByName(String name);
    Trays findById(int id);
}
