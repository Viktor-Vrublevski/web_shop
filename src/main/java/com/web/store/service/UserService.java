package com.web.store.service;

import com.web.store.UserOrderList;
import com.web.store.entity.Role;
import com.web.store.entity.User;
import com.web.store.entity.goods.Product;
import com.web.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder encoder) {
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

    public void addProduct(User user, Product product) {
        Map<Integer, User> map = UserOrderList.getInstance();
        if (map.containsKey(user.getId())) {
            user = map.get(user.getId());
            if (product.getQuantity() > 0) {
                List<Product> products = user.getProducts();
                int count = 0;
                for (Product value : products) {
                    if (value.getName().equals(product.getName())) {
                        int sum = value.getQuantity() + product.getQuantity();
                        value.setQuantity(sum);
                        count++;
                        break;
                    }
                }
                if (count == 0) products.add(product);
            }

        } else if (product.getQuantity() > 0) {
            user.getProducts().add(product);
            map.put(user.getId(), user);
        }
    }

    public List<Product> getAllProduct(User user) {
        Map<Integer, User> map = UserOrderList.getInstance();
        if (map.containsKey(user.getId())) {
            user = map.get(user.getId());
            return user.getProducts();
        }
        return user.getProducts();
    }

    public void deleteProduct(User user, String productName) {
        Map<Integer, User> map = UserOrderList.getInstance();
        user = map.get(user.getId());
        List<Product> products = user.getProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(productName)) {
                products.remove(i);
                user.setProducts(products);
                map.put(user.getId(), user);
                break;
            }
        }
    }
    //----------------------Вспомогательые методы-------------------------

    public double allSum(User user) {
        double sum = 0;
        Map<Integer, User> map = UserOrderList.getInstance();
        if (map.containsKey(user.getId())) {
            user = map.get(user.getId());
            sum = UserOrderList.allSum(user);
            return sum;
        }
        return sum;
    }
}
