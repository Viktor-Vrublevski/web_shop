package com.web.store.repository;

import com.web.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByLogin(String login);
}
