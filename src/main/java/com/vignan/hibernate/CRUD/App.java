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

		try (Session session = factory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            Transaction transaction = session.beginTransaction();
            System.out.println("Choose an option:");
            System.out.println("1. Create order");
            System.out.println("2. Add order item");
            System.out.println("3. See all orders");
            System.out.println("4. See individual orders");
            System.out.println("5. Delete orderItem");
            System.out.println("6. Delete order");
            System.out.println("7. View price of order");
            System.out.println("8. Exit");
            while (true) {
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                

                switch (choice) {
                    case 1:
                        createOrder(session, scanner);
                        break;
                    case 2:
                        addOrderItem(session, scanner);
                        break;
                    case 3:
                        seeAllOrders(session);
                        break;
                    case 4:
                        seeIndividualOrders(session, scanner);
                        break;
                    case 5:
                        deleteOrderItem(session, scanner);
                        break;
                    case 6:
                        deleteOrder(session, scanner);
                        break;
                    case 7:
                        viewPriceOfOrder(session, scanner);
                        break;
                    case 8:
                        session.close();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
	}
    public static void createOrder(Session session, Scanner scanner) {
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
        Order order = new Order(customerName);
        session.persist(order);
        System.out.println("Order created with id: " + order.getId());
    }
    public static void addOrderItem(Session session, Scanner scanner) {
        System.out.println("Enter order id:");
        Long orderId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        Order order = session.get(Order.class, orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        System.out.println("Enter product name:");
        String productName = scanner.nextLine();
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline
        OrderItem orderItem = new OrderItem();
        orderItem.setProductName(productName);
        orderItem.setPrice(price);
        order.addOrderItem(orderItem);
        session.persist(orderItem);
        System.out.println("Order item added with id: " + orderItem.getId());
    }
    public static void seeAllOrders(Session session) {
        List<Order> orders = session.createQuery("from Order", Order.class).list();
        System.out.println(orders.size() + " orders found:");
        for (Order order : orders) {
            order.printdata();            
        }
    }
    public static void seeIndividualOrders(Session session, Scanner scanner) {
        System.out.println("Enter order id:");
        Long orderId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        Order order = session.get(Order.class, orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        order.printdata();
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.printdata();
        }
    }
    public static void deleteOrderItem(Session session, Scanner scanner) {
        System.out.println("Enter order item id:");
        Long orderItemId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        OrderItem orderItem = session.get(OrderItem.class, orderItemId);
        if (orderItem == null) {
            System.out.println("Order item not found.");
            return;
        }
        Order order = orderItem.getOrder();
        order.removeOrderItem(orderItem);
        session.remove(orderItem);
        System.out.println("Order item deleted."+ orderItem.getId());
    }
    public static void deleteOrder(Session session, Scanner scanner) {
        // removing order as well as order items associated with it from database
        System.out.println("Enter order id:");
        Long orderId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        Order order = session.get(Order.class, orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            session.remove(orderItem);
        }
        session.remove(order);
        System.out.println("Order deleted."+ order.getId());
    }
    public static void viewPriceOfOrder(Session session, Scanner scanner) {
        System.out.println("Enter order id:");
        Long orderId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        Order order = session.get(Order.class, orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        order.printdata();
        System.out.println("Total price of order: " + order.getTotalPrice());
    }

}


    
