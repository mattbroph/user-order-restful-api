package edu.matc.persistence;

import edu.matc.entity.Order;
import edu.matc.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    // Step 1 is always create the class / thing we want to test
    UserDao userDao;

    /*
    * This will run the database_dump.sql and reset the test database
    * before each unit test.
    */
    @BeforeEach
    void setup() {
        Database database = Database.getInstance();
        database.runSQL("clean_db.sql");
    }

    @Test
    void getById() {

        userDao = new UserDao();
        User retrievedUser = userDao.getById(1);
        assertNotNull(retrievedUser);
        assertEquals("Joe", retrievedUser.getFirstName());

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

        userDao = new UserDao();
        User myUser = new User("Mary", "Smith",
                "marySmith", LocalDate.of(1900, 1, 1));
        userId = userDao.insert(myUser);

        assertNotEquals(0, userId);

        User inserteredUser = userDao.getById(userId);

        assertEquals("Mary", inserteredUser.getFirstName());


    }

    @Test
    void delete() {
        userDao = new UserDao();
        User userToDelete = userDao.getById(3);
        userDao.delete(userToDelete);
        assertNull(userDao.getById(3));
    }

    @Test
    void deleteWithOrders() {
        // create the userDao
        userDao = new UserDao();
        // get the user we want to delete that has 2 orders associated
        User userToBeDeleted = userDao.getById(3);
        List<Order> orders = userToBeDeleted.getOrders();
        // get the associated order numbers
        int orderNumber1 = orders.get(0).getId();
        int orderNumber2 = orders.get(1).getId();
        // delete the user
        userDao.delete(userToBeDeleted);
        // verify user is deleted
        assertNull(userDao.getById(3));
        // verify the orders are deleted
        OrderDao orderDao = new OrderDao();
        assertNull(orderDao.getById(orderNumber1));
        assertNull(orderDao.getById(orderNumber2));


    }

    @Test
    void getAll() {
        userDao = new UserDao();
        List<User> users = userDao.getAll();
        assertEquals(6, users.size());
    }

    @Test
    void getByPropertyEqual() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyLike("lastName", "Curry");
        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getId());
    }

    @Test
    void getByPropertyLike() {

        userDao = new UserDao();
        List<User> users = userDao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }
}