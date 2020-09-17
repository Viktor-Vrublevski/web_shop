package com.web.store.service;

import com.web.store.OrderList;
import com.web.store.entity.Order;
import com.web.store.entity.goods.*;
import com.web.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private JdbcTemplate jdbcTemplate;
    private PaperService paperService;
    private HoleService holeService;
    private PenService penService;
    private CalculatorService calculatorService;
    private FoldersService foldersService;
    private StaplerService staplerService;
    private TraysService traysService;
    private HouseHoldersService holdersService;

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
    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Autowired
    public void setFoldersService(FoldersService foldersService) {
        this.foldersService = foldersService;
    }

    @Autowired
    public void setStaplerService(StaplerService staplerService) {
        this.staplerService = staplerService;
    }

    @Autowired
    public void setTraysService(TraysService traysService) {
        this.traysService = traysService;
    }

    @Autowired
    public void setHoldersService(HouseHoldersService holdersService) {
        this.holdersService = holdersService;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getPaidOrdersByUserId(int id){
        String SQL = "SELECT * FROM public.orders WHERE user_id=? AND payment_status=true";

        return jdbcTemplate.query(SQL, new Object[]{id}, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int i) throws SQLException {
                return new Order(rs.getInt("id"),rs.getDate("date_order"),
                rs.getDouble("cost"),rs.getBoolean("payment_status"));
            }
        });
    }

    public List<Order> getAllPaidOrders(){
        String SQL = "SELECT * FROM public.orders WHERE payment_status=true";
        return jdbcTemplate.query(SQL, new Object[]{}, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int i) throws SQLException {
                return    new Order(rs.getInt("id"), rs.getDate("date_order"),
                        rs.getDouble("cost"), rs.getBoolean("payment_status"));
            }
        });
    }

    public void deleteOrderById(int id){
        for (int i=0; i<OrderList.getOrderList().size(); i++){
            if (OrderList.getOrderList().get(i).getId()==id){
                OrderList.getOrderList().remove(i);
                break;
            }
        }
    }

    public List<Order> getOrderId(int id) {
        List<Order> orders = new ArrayList<>();
        List<Order> list = OrderList.getOrderList();
        for (Order order : list) {
            if (order.getUser().getId() == id) {
                orders.add(order);
            }
        }
        return orders;
    }

    public void update(Order order) {
        String SQL = "UPDATE web_store.orders SET date_order=?,  payment_status=?, cost=? WHERE id=?";
        jdbcTemplate.update(SQL, order.getDate(), order.isStatus(), order.getAllCost(), order.getId());
    }

    public List<Order> getNoPayedOrders() {
        List<Order> orders = new ArrayList<>();
        Date date = new Date();
        long newTime = date.getTime();
        for (int i = 0; i < OrderList.getOrderList().size(); i++) {
            long time = newTime - OrderList.getOrderList().get(i).getDate().getTime();
            long ten_minutes = 5 * 24 * 60 * 1000;
            if (!OrderList.getOrderList().get(i).isStatus() && time < ten_minutes) {
                orders.add(OrderList.getOrderList().get(i));
            } else if (!OrderList.getOrderList().get(i).isStatus() && time > ten_minutes) {
                returnProductFromBasket(OrderList.getOrderList().get(i).getProducts());
                OrderList.getOrderList().remove(OrderList.getOrderList().get(i));
            }
        }
        return orders;
    }

    public Order getOrderById(int id){
        Order order = null;
        for (Order or : OrderList.getOrderList()){
            if (or.getId()==id){
                order = or;
            }
        }
        return order;
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
