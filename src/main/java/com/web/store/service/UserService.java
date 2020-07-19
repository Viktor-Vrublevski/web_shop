package com.web.store.service;

import com.web.store.entity.User;

public interface UserService {
    void save(User user);
    boolean findByUsername(String username);
    boolean equalsPassword(User user);
}
