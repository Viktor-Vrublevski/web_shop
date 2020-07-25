package com.web.store.service;

public interface SecurityService {
    String loggedInUsername();
    void autoLogin(String username, String password);
}
