package com.web.store.service;

import com.web.store.UserOrderMap;
import com.web.store.entity.Role;
import com.web.store.entity.User;
import com.web.store.entity.goods.*;
import com.web.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private BCryptPasswordEncoder encoder;
    private PaperService paperService;
    private HoleService holeService;
    private PenService penService;
    private CalculatorService calculatorService;
    private FoldersService foldersService;
    private StaplerService staplerService;
    private TraysService traysService;
    private HouseHoldersService holdersService;

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Autowired
    public void setHoldersService(HouseHoldersService holdersService) {
        this.holdersService = holdersService;
    }


    @Autowired
    public void setTraysService(TraysService traysService) {
        this.traysService = traysService;
    }

    @Autowired
    public void setStaplerService(StaplerService staplerService) {
        this.staplerService = staplerService;
    }

    @Autowired
    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Autowired
    public void setFoldersService(FoldersService foldersService) {
        this.foldersService = foldersService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setPaperService(PaperService paperService) {
        this.paperService = paperService;
    }

    @Autowired
    public void setHoleService(HoleService holeService) {
        this.holeService = holeService;
    }

    @Autowired
    public void setPenService(PenService penService) {
        this.penService = penService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void save(User user) {
        user.setLogin(user.getLogin());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAvailability(false);
        user.setTel("+375 __ ___ __ __");
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

    public void update(User user) {
        String SQL = "UPDATE users SET bic=?, iban=?, unn=?, address=?," +
                "address_bank=?, name_bank=?, name_organization=?, email=?, address_store=?," +
                "number_tel=?, availability=?  WHERE id=?";
        jdbcTemplate.update(SQL, user.getBIC(), user.getIBAN(), user.getUNN(), user.getAddress(),
                user.getAddressBank(), user.getNameBank(), user.getOrganization(), user.getEmail(),
                user.getAddress_store(), user.getTel(), user.isAvailability(), user.getId());
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
        if (product.getQuantity() > 0) {
            Map<Integer, Map<Date, User>> maps = UserOrderMap.getInstance();
            if (maps.containsKey(user.getId())) {
                Map<Date, User> map = maps.get(user.getId());
                for (Map.Entry<Date, User> entry : map.entrySet()) {
                    List<Product> products = entry.getValue().getProducts();
                    int count = 0;
                    for (Product value : products) {
                        if (value.getName().equals(product.getName())) {
                            int sum = value.getQuantity() + product.getQuantity();
                            value.setQuantity(sum);
                            user.setProducts(products);
                            map.put(entry.getKey(), user);
                            maps.put(user.getId(), map);
                            count++;
                            break;
                        }
                    }
                    if (count == 0) {
                        products.add(product);
                        user.setProducts(products);
                        map.put(entry.getKey(), user);
                        maps.put(user.getId(), map);
                    }
                }
            } else user.getProducts().add(product);
            Map<Date, User> map = new HashMap<>();
            map.put(new Date(), user);
            maps.put(user.getId(), map);
        }
    }

    public List<Product> getAllProduct(User user) {
        Map<Integer, Map<Date, User>> maps = UserOrderMap.getInstance();
        if (maps.containsKey(user.getId())) {
            Map<Date, User> map = maps.get(user.getId());
            for (Map.Entry<Date, User> entry : map.entrySet()) {
                user = entry.getValue();
                return user.getProducts();
            }
        }
        return user.getProducts();

    }

    public void deleteProduct(User user, String productName) {
        Map<Integer, Map<Date, User>> maps = UserOrderMap.getInstance();
        Map<Date, User> map = maps.get(user.getId());
        for (Map.Entry<Date, User> entry : map.entrySet()) {
            user = entry.getValue();
            List<Product> products = user.getProducts();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().equals(productName)) {
                    returnQuantityProduct(productName, user);
                    products.remove(i);
                    user.setProducts(products);
                    map.put(entry.getKey(), user);
                    maps.put(user.getId(), map);
                    break;
                }
            }
        }
    }

    public double allSum(User user) {
        double sum = 0;
        Map<Integer, Map<Date, User>> maps = UserOrderMap.getInstance();
        if (maps.containsKey(user.getId())) {
            Map<Date, User> map = maps.get(user.getId());
            for (Map.Entry<Date, User> entry : map.entrySet()) {
                user = entry.getValue();
                sum = UserOrderMap.allSum(user);
                return sum;
            }
        }
        return sum;
    }

    public void deleteByDate(Date date) {
        long limiteTime = 30 * 60 * 1000;
        Map<Integer, Map<Date, User>> maps = UserOrderMap.getInstance();
        for (Map.Entry<Integer, Map<Date, User>> entry1 : maps.entrySet()) {
            for (Map.Entry<Date, User> entry2 : entry1.getValue().entrySet()) {
                if ((date.getTime() - entry2.getKey().getTime()) > limiteTime) {
                    returnProductFromBasket(entry2.getValue().getProducts());
                    entry2.getValue().setProducts(null);
                }
            }
        }
    }
    //----------------------Вспомогательые методы-------------------------

    private void returnQuantityProduct(String productName, User user) {
        List<Paper> papers = paperService.getAllProducts();
        List<HolePuncher> holes = holeService.getAllProducts();
        List<Pen> pens = penService.getAllProducts();
        List<Calculator> calculators = calculatorService.getAllProducts();
        List<Folders> folders = foldersService.getAllProducts();
        List<Stapler> staplers = staplerService.getAllProducts();
        List<Trays> trays = traysService.getAllProducts();
        List<HouseHold> holds = holdersService.getAllProducts();

        Map<Integer, Map<Date, User>> maps = UserOrderMap.getInstance();
        Map<Date, User> map = maps.get(user.getId());
        for (Map.Entry<Date, User> entry : map.entrySet()) {
            List<Product> list = entry.getValue().getProducts();
            int quantity = 0;
            for (Product pr : list) {
                if (pr.getName().equals(productName)) {
                    quantity = pr.getQuantity();
                    break;
                }
            }
            for (Paper paper : papers) {
                if (paper.getName().equals(productName)) {
                    paper.setQuantity(paper.getQuantity() + quantity);
                    paperService.update(paper);
                    break;
                }
            }
            for (HolePuncher puncher : holes) {
                if (puncher.getName().equals(productName)) {
                    puncher.setQuantity(puncher.getQuantity() + quantity);
                    holeService.update(puncher);
                    break;
                }
            }
            for (Pen pen : pens) {
                if (pen.getName().equals(productName)) {
                    pen.setQuantity(pen.getQuantity() + quantity);
                    penService.update(pen);
                    break;
                }
            }
            for (Calculator calc : calculators) {
                if (calc.getName().equals(productName)) {
                    calc.setQuantity(calc.getQuantity() + quantity);
                    calculatorService.update(calc);
                    break;
                }
            }
            for (Folders fold : folders) {
                if (fold.getName().equals(productName)) {
                    fold.setQuantity(fold.getQuantity() + quantity);
                    foldersService.update(fold);
                    break;
                }
            }
            for (Stapler stap : staplers) {
                if (stap.getName().equals(productName)) {
                    stap.setQuantity(stap.getQuantity() + quantity);
                    staplerService.update(stap);
                    break;
                }
            }
            for (Trays tr : trays) {
                if (tr.getName().equals(productName)) {
                    tr.setQuantity(tr.getQuantity() + quantity);
                    traysService.update(tr);
                    break;
                }
            }
            for (HouseHold hold : holds) {
                if (hold.getName().equals(productName)) {
                    hold.setQuantity(hold.getQuantity() + quantity);
                    holdersService.update(hold);
                    break;
                }
            }
        }
    }

    private void returnProductFromBasket(List<Product> products) {
        List<Paper> papers = paperService.getAllProducts();
        List<HolePuncher> holes = holeService.getAllProducts();
        List<Pen> pens = penService.getAllProducts();
        List<Calculator> calculators = calculatorService.getAllProducts();
        List<Folders> folders = foldersService.getAllProducts();
        List<Stapler> staplers = staplerService.getAllProducts();
        List<Trays> trays = traysService.getAllProducts();
        List<HouseHold> holds = holdersService.getAllProducts();

        for (Product pr : products) {
            for (Paper paper : papers) {
                if (paper.getName().equals(pr.getName())) {
                    paper.setQuantity(paper.getQuantity() + pr.getQuantity());
                    paperService.update(paper);
                    break;
                }
            }
            for (HolePuncher puncher : holes) {
                if (puncher.getName().equals(pr.getName())) {
                    puncher.setQuantity(puncher.getQuantity() + pr.getQuantity());
                    holeService.update(puncher);
                    break;
                }
            }
            for (Pen pen : pens) {
                if (pen.getName().equals(pr.getName())) {
                    pen.setQuantity(pen.getQuantity() + pr.getQuantity());
                    penService.update(pen);
                    break;
                }
            }
            for (Calculator calc : calculators) {
                if (calc.getName().equals(pr.getName())) {
                    calc.setQuantity(calc.getQuantity() + pr.getQuantity());
                    calculatorService.update(calc);
                    break;
                }
            }
            for (Folders fold : folders) {
                if (fold.getName().equals(pr.getName())) {
                    fold.setQuantity(fold.getQuantity() + pr.getQuantity());
                    foldersService.update(fold);
                    break;
                }
            }
            for (Stapler stap : staplers) {
                if (stap.getName().equals(pr.getName())) {
                    stap.setQuantity(stap.getQuantity() + pr.getQuantity());
                    staplerService.update(stap);
                    break;
                }
            }
            for (Trays tr : trays) {
                if (tr.getName().equals(pr.getName())) {
                    tr.setQuantity(tr.getQuantity() + pr.getQuantity());
                    traysService.update(tr);
                    break;
                }
            }
            for (HouseHold hold : holds) {
                if (hold.getName().equals(pr.getName())) {
                    hold.setQuantity(hold.getQuantity() + pr.getQuantity());
                    holdersService.update(hold);
                    break;
                }
            }
        }
    }
}
