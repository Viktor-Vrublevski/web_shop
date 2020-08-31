package com.web.store.repository;

import com.web.store.entity.goods.Folders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folders,Integer> {
    Folders findByName(String name);
    Folders findById(int id);
}
