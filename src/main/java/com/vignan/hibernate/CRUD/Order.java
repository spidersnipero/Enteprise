package com.vignan.hibernate.CRUD;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;



@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;

    public Order(String name) {
        this.customerName = name;
    }

    @Column(name = "total_price")
    private double totalPrice; 


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    // setters
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        this.totalPrice += orderItem.getPrice();
    }
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
        this.totalPrice -= orderItem.getPrice();
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    // getters
    public Long getId() {
        return id;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public String getCustomerName() {
        return customerName;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void printdata(){
        System.out.println( "Order ID: " + this.getId() + ", Customer name: " + this.getCustomerName());
    }


}
