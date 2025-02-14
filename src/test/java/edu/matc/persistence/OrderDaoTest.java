package edu.matc.persistence;

import edu.matc.entity.Order;
import edu.matc.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {

    OrderDao orderDao;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("clean_db.sql");
        orderDao = new OrderDao();
    }

    @Test
    void getById() {
        Order retrievedOrder = orderDao.getById(1);
        assertNotNull(retrievedOrder);
        assertEquals("party supplies", retrievedOrder.getDescription());
        assertEquals(3, retrievedOrder.getUser().getId());
    }

    @Test
    void update() {
        Order order = orderDao.getById(1);
        order.setDescription("more cool stuff");
        orderDao.update(order);
        Order retrievedOrder = orderDao.getById(1);
        assertEquals("more cool stuff", retrievedOrder.getDescription());

    }

    @Test
    void insert() {
        // get a user
        UserDao userDao = new UserDao();
        User user = userDao.getById(6);

        // create an order with that user
        Order order = new Order("fun things", user);

        // insert the order
        int insertedOrderId = orderDao.insert(order);

        // retrieve the order
        Order retrievedOrder = orderDao.getById(insertedOrderId);

        // verify
        assertNotNull(retrievedOrder);
        assertEquals("fun things", retrievedOrder.getDescription());
        assertEquals("Dawn", order.getUser().getFirstName());
    }

    @Test
    void delete() {
        orderDao.delete(orderDao.getById(3));
        assertNull(orderDao.getById(3));
    }

    @Test
    void getAll() {
        List<Order> orders = orderDao.getAll();
        assertEquals(4, orders.size());
    }

    @Test
    void getByPropertyEqual() {
        List<Order> orders = orderDao.getByPropertyEqual("description", "party supplies");
        assertEquals(1, orders.size());
    }

    @Test
    void getByPropertyLike() {
        List<Order> orders = orderDao.getByPropertyLike("description", "party");
        assertEquals(1, orders.size());
    }
}