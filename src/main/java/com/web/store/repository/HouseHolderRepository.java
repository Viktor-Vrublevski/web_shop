package com.web.store.repository;

import com.web.store.entity.goods.HouseHold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseHolderRepository extends JpaRepository<HouseHold,Integer> {
    HouseHold findByName(String name);
    HouseHold findById(int id);
}
