package com.vignan.hibernate.CRUD;

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





