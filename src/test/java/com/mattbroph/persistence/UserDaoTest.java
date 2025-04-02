package com.mattbroph.persistence;

import com.mattbroph.entity.Order;
import com.mattbroph.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    // Step 1 is always create the class / thing we want to test
    UserDao userDao;
    GenericDao genericDao;
    GenericDao genericDaoOrder;

    /*
    * This will run the database_dump.sql and reset the test database
    * before each unit test.
    */
    @BeforeEach
    void setup() {
        Database database = Database.getInstance();
        database.runSQL("clean_db.sql");
        genericDao = new GenericDao(User.class);
        genericDaoOrder = new GenericDao(Order.class);
    }

    @Test
    void getById() {

        User retrievedUser = (User)genericDao.getById(6);
        assertNotNull(retrievedUser);
        assertEquals("Dawn", retrievedUser.getFirstName());

    }

    @Test
    void update() {

        userDao = new UserDao();
        User userToUpdate = userDao.getById(1);
        userToUpdate.setLastName("Smith");
        userDao.update(userToUpdate);

        // retrieve the user and check that the name change worked
        User actualUser = userDao.getById(1);
        assertEquals("Smith", actualUser.getLastName());

    }

    @Test
    void insert() {

        int userId = 0;

        User myUser = new User("Mary", "Smith",
                "marySmith", LocalDate.of(1900, 1, 1));
        userId = genericDao.insert(myUser);

        assertNotEquals(0, userId);

        User inserteredUser = (User)genericDao.getById(userId);

        assertEquals("Mary", inserteredUser.getFirstName());


    }

    @Test
    void delete() {
        User userToDelete = (User)genericDao.getById(4);
        genericDao.delete(userToDelete);
        assertNull((User)genericDao.getById(4));
    }

    @Test
    void deleteWithOrders() {
        // get the user we want to delete that has 2 orders associated
        User userToBeDeleted = (User)genericDao.getById(4);
        List<Order> orders = userToBeDeleted.getOrders();
        // get the associated order numbers
        int orderNumber1 = orders.get(0).getId();
        int orderNumber2 = orders.get(1).getId();
        // delete the user
        genericDao.delete(userToBeDeleted);
        // verify user is deleted
        assertNull((User)genericDao.getById(4));
        // verify the orders are deleted
        assertNull(genericDaoOrder.getById(orderNumber1));
        assertNull(genericDaoOrder.getById(orderNumber2));


    }

    @Test
    void getAll() {
        List<User> users = (List<User>)genericDao.getAll();
        assertEquals(5, users.size());
    }

    @Test
    void getByPropertyEqual() {
        List<User> users = (List<User>)genericDao.getByPropertyLike("lastName", "Mack");
        assertEquals(1, users.size());
        assertEquals(4, users.get(0).getId());
    }

    @Test
    void getByPropertyLike() {

        List<User> users = (List<User>)genericDao.getByPropertyLike("lastName", "k");
        assertEquals(2, users.size());
    }
}