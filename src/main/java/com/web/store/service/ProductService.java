package com.web.store.service;

import com.web.store.entity.Product;
import com.web.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private  ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }

    public Product findProduct(String name) {
        return productRepository.findByName(name);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public void update(Product product) {
        String SQL = "UPDATE products SET product_name=? , price=?, quantity=?, description=? WHERE id=?";
        jdbcTemplate.update(SQL,product.getName(),product.getPrice(),product.getQuantity(),
                product.getDescription(), product.getId());
    }

    public Product findById(int id){
        return productRepository.findById(id);
    }

}
