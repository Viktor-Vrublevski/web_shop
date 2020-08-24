package com.web.store.repository;

import com.web.store.entity.goods.Paper;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaperRepository extends JpaRepository<Paper,Integer> {

    Paper findByName(String name);
    Paper findById(int id);
}
