package com.web.store.dto;

import com.web.store.entity.User;
import com.web.store.entity.abstracts.Product;

import java.util.List;

public class DtoObjectProduct {

    private List<? extends Product> papers;
    private User user;

    public DtoObjectProduct(List<? extends Product> papers, User user) {
        this.papers = papers;
        this.user = user;
    }

    public List<? extends Product> getPapers() {
        return papers;
    }

    public void setPapers(List<Product> papers) {
        this.papers = papers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
