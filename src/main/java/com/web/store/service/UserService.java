package com.web.store.service;

import com.web.store.UserOrderList;
import com.web.store.entity.Role;
import com.web.store.entity.User;
import com.web.store.entity.goods.*;
import com.web.store.repository.*;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {


    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;
    private UserRepository userRepository;
    private EntityManagerFactory factory;

    @Autowired
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username);
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public void save(User user) {
        if (roleRepository.findAll().size() == 0) {
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        user.setLogin(user.getLogin());
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (userRepository.findAll().size() == 0) {
            roles.add(roleRepository.findById(1).get());
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);
        } else {
            roles.add(roleRepository.findById(1).get());
        }
        user.setRoles(roles);
        user.setActive(true);
        userRepository.save(user);
    }

    public User getUser(String username) {
        List<User> users = userRepository.findAll();
        User user = null;
        for (User user1 : users) {
            if (user1.getLogin().equals(username)) {
                user = user1;
            }
        }
        return user;
    }

    public void delete(User user) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.contains(user) ? user : manager.merge(user));
        manager.getTransaction().commit();
    }

}
