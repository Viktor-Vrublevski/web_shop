package com.web.store.service;

import com.web.store.entity.Role;
import com.web.store.entity.User;
import com.web.store.repository.RoleRepository;
import com.web.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username);
    }

    public void save(User user) {
        user.setLogin(user.getLogin());
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (userRepository.findAll().size() == 0) {
            roles.add(roleRepository.getOne(1));
            roles.add(roleRepository.getOne(2));
            user.setRoles(roles);
            userRepository.save(user);
        } else
            roles.add(roleRepository.getOne(1));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public User getUser(String username){
        List<User> users = userRepository.findAll();
        User user = null;
        for (User user1: users){
            if (user1.getLogin().equals(username)){
                user = user1;
            }
        }
        return user;
    }

}
