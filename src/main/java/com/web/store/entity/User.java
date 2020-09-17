package com.web.store.entity;


import com.web.store.entity.goods.Product;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("All")
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "name_organization")
    private String organization;
    @Column(name = "UNN")
    private int UNN;
    @Column(name = "address")
    private String address;
    @Column(name = "name_bank")
    private String nameBank;
    @Column(name = "address_bank")
    private String addressBank;
    @Column(name = "IBAN")
    private String IBAN;
    @Column(name = "BIC")
    private String BIC;
    @Column(name = "number_tel")
    private String tel;
    @Column(name = "email")
    private String email;
    @Column(name = "address_store")
    private String address_store;

    @Transient
    private List<Product> products;

    @Transient
    private List<Order> orders;

    public User() {
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress_store() {
        return address_store;
    }

    public void setAddress_store(String address_store) {
        this.address_store = address_store;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getUNN() {
        return UNN;
    }

    public void setUNN(int UNN) {
        this.UNN = UNN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressBank() {
        return addressBank;
    }

    public void setAddressBank(String addressBank) {
        this.addressBank = addressBank;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts(){
        if (products == null){
            products = new ArrayList<>();
            return products;
        }
       return products;
    }
    public List<Order> getPayedOrders(){
        if (orders == null){
            orders = new ArrayList<>();
            return orders;
        }
        return orders;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}