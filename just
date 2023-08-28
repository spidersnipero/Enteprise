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
    


}
order items.  package com.vignan.hibernate.CRUD;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;



@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    // setters
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
    // getters
    public Long getId() {
        return id;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    

}
orderDao is package com.vignan.hibernate.CRUD;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class OrderDAO {
    private SessionFactory sessionFactory;

    public OrderDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Object[]> getAllOrdersWithTotalValue() {
        try (Session session = sessionFactory.openSession()) {
            String query = "SELECT o.id, SUM(oi.price) FROM Order o " +
                           "JOIN o.orderItems oi GROUP BY o.id";
            return session.createQuery(query, Object[].class).getResultList();
        }
    }
}

