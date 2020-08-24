package com.web.store.repository;

import com.web.store.entity.goods.HolePuncher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoleRepository extends JpaRepository<HolePuncher,Integer> {
    HolePuncher findByName(String name);
    HolePuncher findById(int id);
}
