package com.mattbroph.persistence;

import com.mattbroph.entity.Order;
import com.mattbroph.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {

    GenericDao genericDao;
    GenericDao genericDaoUser;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("clean_db.sql");
        /*******************************************************************************
         * ATTENTION: Need to create two in order to do insert with two class types!!!!!
         ********************************************************************************/
        genericDao = new GenericDao(Order.class);
        genericDaoUser = new GenericDao(User.class);
    }

    @Test
    void getById() {
        Order retrievedOrder = (Order)genericDao.getById(6);
        assertNotNull(retrievedOrder);
        assertEquals("party supplies", retrievedOrder.getDescription());
        assertEquals(4, retrievedOrder.getUser().getId());
    }

    @Test
    void update() {
        Order order = (Order)genericDao.getById(3);
        order.setDescription("more cool stuff");
        genericDao.update(order);
        Order retrievedOrder = (Order)genericDao.getById(3);
        assertEquals("more cool stuff", retrievedOrder.getDescription());

    }

    @Test
    void insert() {
        // get a user
        User user = (User)genericDaoUser.getById(6);

        // create an order with that user
        Order order = new Order("fun things", user);

        // insert the order
        int insertedOrderId = genericDao.insert(order);

        // retrieve the order
        Order retrievedOrder = (Order)genericDao.getById(insertedOrderId);

        // verify
        assertNotNull(retrievedOrder);
        assertEquals("fun things", retrievedOrder.getDescription());
        assertEquals("Dawn", order.getUser().getFirstName());
    }

    @Test
    void delete() {
        // Get the order to delete
        Order orderToDelete = (Order)genericDao.getById(3);
        // Delete the order
        genericDao.delete(orderToDelete);
        // Make sure the order no longer exists
        assertNull((Order)genericDao.getById(3));
    }

    @Test
    void getAll() {
        List<Order> orders = (List<Order>)genericDao.getAll();
        assertEquals(4, orders.size());
    }

    @Test
    void getByPropertyEqual() {
        List<Order> orders = (List<Order>)genericDao.getByPropertyEqual("description", "party supplies");
        assertEquals(1, orders.size());
    }

    @Test
    void getByPropertyLike() {
        List<Order> orders = (List<Order>)genericDao.getByPropertyLike("description", "party");
        assertEquals(1, orders.size());
    }
}