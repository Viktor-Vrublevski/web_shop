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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public List<User> getAllUsers(){
        return userRepository.findAll();
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

    public void update(User user) {
        String SQL = "UPDATE web_store.users SET bic=?, iban=?, unn=?, address=?," +
                "address_bank=?, name_bank=?, name_organization=?, email=?, address_store=?," +
                "number_tel=? WHERE id=?";
        jdbcTemplate.update(SQL, user.getBIC(), user.getIBAN(), user.getUNN(), user.getAddress(),
                user.getAddressBank(), user.getNameBank(), user.getOrganization(), user.getEmail(),
                user.getAddress_store(), user.getTel(), user.getId());
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
            Map<Integer, User> map = UserOrderMap.getInstance();
            if (map.containsKey(user.getId())) {
                user = map.get(user.getId());
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

            } else user.getProducts().add(product);
                map.put(user.getId(), user);
        }
    }

    public List<Product> getAllProduct(User user) {
        Map<Integer, User> map = UserOrderMap.getInstance();
        if (map.containsKey(user.getId())) {
            user = map.get(user.getId());
            return user.getProducts();
        }
        return user.getProducts();

    }

    public void deleteProduct(User user, String productName) {
        Map<Integer, User> map = UserOrderMap.getInstance();
        user = map.get(user.getId());
        List<Product> products = user.getProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(productName)) {
                returnQuantityProduct(productName, user);
                products.remove(i);
                user.setProducts(products);
                map.put(user.getId(), user);
                break;
            }
        }
    }

    public double allSum(User user) {
        double sum = 0;
        Map<Integer, User> map = UserOrderMap.getInstance();
        if (map.containsKey(user.getId())) {
            user = map.get(user.getId());
            sum = UserOrderMap.allSum(user);
            return sum;
        }
        return sum;
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

        Map<Integer, User> map = UserOrderMap.getInstance();
        List<Product> list = map.get(user.getId()).getProducts();
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
