package com.web.store.service;

import com.web.store.entity.User;


public interface UserService {
    void save(User user);
    User findByUsername(String username);
    boolean equalsPassword(String password, String encoderPassword);
}
