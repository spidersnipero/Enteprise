package com.vignan.hibernate.CRUD;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String productName;
    private double price;

    public OrderItem() {
    }
    // setters
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    // getters
    public Long getId() {
        return id;
    }
    public Order getOrder() {
        return order;
    }
    public String getProductName() {
        return productName;
    }
    public double getPrice() {
        return price;
    }

    public void   printdata(){
        System.out.println( "Order Item ID: " + this.getId() + ", Product name: " + this.getProductName() + ", Price: " + this.getPrice());
    }
    


}
