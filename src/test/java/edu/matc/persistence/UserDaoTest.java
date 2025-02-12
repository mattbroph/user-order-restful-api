package edu.matc.persistence;

import edu.matc.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
    }

    @Test
    void getAll() {
    }

    @Test
    void getByPropertyEqual() {
    }

    @Test
    void getByPropertyLike() {
    }
}