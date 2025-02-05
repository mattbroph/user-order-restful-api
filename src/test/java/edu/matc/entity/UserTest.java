package edu.matc.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUserAge() {
        // Create the object has the method I want to test
        User user = new User();

        // Set birthdate for the user
        LocalDate birthday = LocalDate.of(1975, 1, 1);

        // Assign the user's birthdate
        user.setDateOfBirth(birthday);

        // Create a variable for the expected value
        int expectedAge = 50;

        // Call the method and get actual value
        int actualAge = user.getUserAge();

        // Compare the two, pass or fail
        // Comparisons in junit are assertions
        assertEquals(expectedAge, actualAge);

    }
}