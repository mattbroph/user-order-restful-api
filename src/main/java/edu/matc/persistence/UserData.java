package edu.matc.persistence;

import edu.matc.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;


/**
 * Access users in the user table.
 *
 * @author pwaite
 */
public class UserData {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        String sql = "SELECT * FROM users";

        users = getUsersData(sql, users);

        return users;
    }

    //TODO add a method or methods to return a users based on search criteria
    public List<User> getSearchedUser(String searchTerm) {

        List<User> users = new ArrayList<User>();

        String sql = "SELECT * FROM users WHERE last_name = \"" + searchTerm + "\"";

        users = getUsersData(sql, users, searchTerm);

        return users;
        }

        /**
        *
        * Referenced https://stackoverflow.com/questions/965690/how-do-i-use-optional-parameters-in-java
        * for Varargs (optional parameter).
        */
        public List<User> getUsersData(String sql, List<User> users, String... searchTerm) {

            Database database = Database.getInstance();
            Connection connection = null;

            try {
                database.connect();
                connection = database.getConnection();
                Statement selectStatement = connection.createStatement();
                ResultSet results = selectStatement.executeQuery(sql);
                while (results.next()) {
                    User employee = createUserFromResults(results);
                    users.add(employee);
                }
                database.disconnect();
            } catch (SQLException e) {
                System.out.println("SearchUser.getAllUsers()...SQL Exception: " + e);
            } catch (Exception e) {
                System.out.println("SearchUser.getAllUsers()...Exception: " + e);
            }

            return users;
        }



    private User createUserFromResults(ResultSet results) throws SQLException {

        String dateOfBirth;
        LocalDate formattedDate;
        User user = new User();

        user.setFirstName(results.getString("first_name"));
        user.setLastName(results.getString("last_name"));
        user.setUserName(results.getString("user_name"));

        // Store the result as a string so you can convert it to LocalDate
        dateOfBirth = results.getString("date_of_birth");
        // Convert to LocalDate
        formattedDate = LocalDate.parse(dateOfBirth);
        // Set the user instance variable
        user.setDateOfBirth(formattedDate);

        return user;
    }

}