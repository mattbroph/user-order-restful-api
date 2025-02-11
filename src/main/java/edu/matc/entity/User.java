package edu.matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import javax.ejb.Local;
import java.time.LocalDate;
import java.time.Period;

// This lets hibernate know that this class is mapped to a particular table
// Use jakarta persistance
@Entity
// Specifies the table
@Table(name = "user") // case-sensitive
// Need to do with columns down below by the instane variables


/**
 * A class to represent a user.
 *
 * @author pwaite
 */
public class User {
    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column (name = "user_name")
    private String userName;

    @Column (name = "date_of_birth")
    private LocalDate dateOfBirth;
    // Every Entity must have a unique identifier which is annotated @Id
    // Notice there is no @Column here as the column and instance variable name are the same
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;

    /* Age will be calculated and passed back in getter.
    * There is no setter so it is not stored (this is a requirement).
    */
    private int userAge;


    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param userName  the user name
     * @param id        the id
     * @param dateOfBirth the date of birth
     */
    public User(String firstName, String lastName, String userName, int id, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
    }


    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }

    /** Calculates the user's age. Age should not be stored, it should be
     *  calculated only.
     *
     * @return the user's age in years
     */
    public int getUserAge() {

        // Get today's date
        LocalDate currentDate = LocalDate.now();
        // Derive a Period and store the value
        Period userAgePeriod = Period.between(dateOfBirth, currentDate);
        // Derive the years from the Period and store as an int
        int userAge = userAgePeriod.getYears();

        return userAge;
    }



}