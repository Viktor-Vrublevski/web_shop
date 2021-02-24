package com.web.store.service;

import com.web.store.UserOrderList;
import com.web.store.entity.User;
import com.web.store.entity.abstracts.Product;
import com.web.store.entity.goods.*;
import com.web.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private PaperService paperService;
    private HoleService holeService;
    private  PenService penService;
    private CalculatorService calculatorService;
    private FoldersService foldersService;
    private StaplerService staplerService;
    private TraysService traysService;
    private HouseHoldersService holdersService;
    private CalculatorRepository calculatorRepository;
    private PaperRepository paperRepository;
    private HoleRepository holeRepository;
    private PenRepository penRepository;
    private FolderRepository folderRepository;
    private StaplerRepository staplerRepository;
    private TraysRepository traysRepository;
    private HouseHolderRepository holderRepository;

    @Autowired
    public void setHoldersService(HouseHoldersService holdersService) {
        this.holdersService = holdersService;
    }
    @Autowired
    public void setHolderRepository(HouseHolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }
    @Autowired
    public void setTraysService(TraysService traysService) {
        this.traysService = traysService;
    }
    @Autowired
    public void setTraysRepository(TraysRepository traysRepository) {
        this.traysRepository = traysRepository;
    }
    @Autowired
    public void setStaplerService(StaplerService staplerService) {
        this.staplerService = staplerService;
    }
    @Autowired
    public void setStaplerRepository(StaplerRepository staplerRepository) {
        this.staplerRepository = staplerRepository;
    }
    @Autowired
    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }
    @Autowired
    public void setCalculatorRepository(CalculatorRepository calculatorRepository) {
        this.calculatorRepository = calculatorRepository;
    }
    @Autowired
    public void setFoldersService(FoldersService foldersService) {
        this.foldersService = foldersService;
    }
    @Autowired
    public void setFolderRepository(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
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
    @Autowired
    public void setPaperRepository(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }
    @Autowired
    public void setHoleRepository(HoleRepository holeRepository) {
        this.holeRepository = holeRepository;
    }
    @Autowired
    public void setPenRepository(PenRepository penRepository) {
        this.penRepository = penRepository;
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
                returnQuantityProduct(productName,user);
                products.remove(i);
                user.setProducts(products);
                map.put(user.getId(), user);
                break;
            }
        }
    }

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
    //----------------------Вспомогательые методы-------------------------

    private void returnQuantityProduct(String productName, User user) {
        List<Paper> papers = paperRepository.findAll();
        List<HolePuncher> holes = holeRepository.findAll();
        List<Pen> pens = penRepository.findAll();
        List<Calculator> calculators = calculatorRepository.findAll();
        List<Folders> folders = folderRepository.findAll();
        List<Stapler> staplers = staplerRepository.findAll();
        List<Trays> trays = traysRepository.findAll();
        List<HouseHold> holds = holderRepository.findAll();

        Map<Integer, User> map = UserOrderList.getInstance();
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
