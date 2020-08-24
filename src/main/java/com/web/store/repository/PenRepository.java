package com.web.store.repository;

;
import com.web.store.entity.goods.Pen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenRepository extends JpaRepository<Pen,Integer> {
    Pen findByName(String name);
    Pen findById(int id);
}
