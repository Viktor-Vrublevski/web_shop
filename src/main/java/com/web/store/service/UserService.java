package com.web.store.service;

import com.web.store.entity.Role;
import com.web.store.entity.User;
import com.web.store.entity.goods.HolePuncher;
import com.web.store.entity.goods.Paper;
import com.web.store.entity.goods.Pen;
import com.web.store.entity.goods.Product;
import com.web.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PaperRepository paperRepository;
    private final HoleRepository holeRepository;
    private final PenRepository penRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(UserRepository userRepository, PaperRepository paperRepository, HoleRepository holeRepository,
                       PenRepository penRepository, RoleRepository roleRepository, JdbcTemplate jdbcTemplate,
                       BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.holeRepository = holeRepository;
        this.penRepository = penRepository;
        this.roleRepository = roleRepository;
        this.jdbcTemplate = jdbcTemplate;
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

    public void addPaper(User user, Paper paper) {
        if (user.getPapers() == null) {
            Set<Paper> papers = new HashSet<>();
            papers.add(paper);
            System.out.printf("Бумага %s в кол-ве %d",paper.getName(),paper.getQuantity());
            user.setPapers(papers);
            userRepository.save(user);
        } else
            user.getPapers().add(paper);
        System.out.printf("Бумага %s в кол-ве %d",paper.getName(),paper.getQuantity());
        userRepository.save(user);
    }

    public void addPen(User user, Pen pen) {
        if (user.getPens() == null) {
            Set<Pen> pens = new HashSet<>();
            pens.add(pen);
            user.setPens(pens);
            userRepository.save(user);
        } else
            user.getPens().add(pen);

        userRepository.save(user);
    }

    public void addHole(User user, HolePuncher puncher) {
        if (user.getHoles() == null) {
            Set<HolePuncher> holes = new HashSet<>();
            holes.add(puncher);
            user.setHoles(holes);
            userRepository.save(user);
        } else
            user.getHoles().add(puncher);

        userRepository.save(user);
    }

    public Set<Product> allProduct(User user) {
        Set<Product> products = new HashSet<>();
        if (user.getPapers()!=null) products.addAll(user.getPapers());
        if (user.getPens()!=null) products.addAll(user.getPens());
        if (user.getHoles()!=null) products.addAll(user.getHoles());
        return products;
    }
}
