package com.web.store.repository;

import com.web.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByLogin(String login);
    Optional<User> findById(Integer id);
}
