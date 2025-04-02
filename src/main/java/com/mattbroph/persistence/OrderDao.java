package com.mattbroph.persistence;

import com.mattbroph.entity.Order;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class OrderDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get order by id
     */
    public Order getById(int id) {
        Session session = sessionFactory.openSession();
        Order order = session.get(Order.class, id);
        session.close();
        return order;
    }

    /**
     * update order
     * @param order  Order to be updated
     */
    public void update(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(order);
        transaction.commit();
        session.close();
    }

    /**
     * insert a new order
     * @param order  Order to be inserted
     */
    public int insert(Order order) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(order);
        transaction.commit();
        id = order.getId();
        session.close();
        return id;
    }

    /**
     * Delete a order
     * @param order Order to be deleted
     */
    public void delete(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(order);
        transaction.commit();
        session.close();
    }


    /** Return a list of all orders
     *
     * @return All orders
     */
    public List<Order> getAll() {

        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        List<Order> orders = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of orders " + orders);
        session.close();

        return orders;
    }

    /**
     * Get order by property (exact match)
     * sample usage: getByPropertyEqual("lastname", "Curry")
     */
    public List<Order> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for order with " + propertyName + " = " + value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Order> orders = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of orders is: " + orders);
        session.close();
        return orders;
    }

    /**
     * Get order by property (like)
     * sample usage: getByPropertyLike("lastname", "C")
     */
    public List<Order> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for order with {} = {}",  propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Order> orders = session.createQuery( query ).getResultList();
        logger.debug("The list of orders is: " + orders);
        session.close();
        return orders;
    }


}
