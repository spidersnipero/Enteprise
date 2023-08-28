package com.vignan.hibernate.CRUD;

import java.util.List;
import java.util.Scanner;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class App {
 	public static void main(String[] args) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		// OrderDAO orderDAO = new OrderDAO(factory);
		// List<Object[]> ordersWithTotalValue = orderDAO.getAllOrdersWithTotalValue();
		// for(Object[] result:ordersWithTotalValue)
		// {
		// 	Long orderId = (Long) result[0];
		// 	Double totalValue = (Double) result[1];
		// 	System.out.println("Order ID: " + orderId + ", Total Value: " + totalValue);
		// }

		try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Create an order
            Order order = new Order("java");
            
            // Create and add order items
            OrderItem item1 = new OrderItem();
            item1.setProductName("Product 1");
            item1.setPrice(100.0);
            order.addOrderItem(item1);

            OrderItem item2 = new OrderItem();
            item2.setProductName("Product 2");
            item2.setPrice(150.0);
            order.addOrderItem(item2);

            // Save the order (including order items)
			session.persist(order);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
	}
}


    
