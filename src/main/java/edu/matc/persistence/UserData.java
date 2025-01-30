package edu.matc.persistence;

import edu.matc.entity.User;

import java.sql.*;
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

        users = getUsersData(sql, users, null);

        return users;
    }

    /**
     *
     * @param searchTerm
     * @return
     */
    public List<User> getSearchedUser(String searchTerm) {

        List<User> users = new ArrayList<User>();

        String sql = "SELECT * FROM users WHERE last_name = ?";

        users = getUsersData(sql, users, searchTerm);

        return users;
        }

        /**
        *
        */
        public List<User> getUsersData(String sql, List<User> users, String searchTerm) {

            Database database = Database.getInstance();
            Connection connection = null;

            try {
                database.connect();
                connection = database.getConnection();

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    if (searchTerm != null) {
                        preparedStatement.setString(1, searchTerm);
                    }
                    try (ResultSet results = preparedStatement.executeQuery()) {
                        while (results.next()) {
                            User employee = createUserFromResults(results);
                            users.add(employee);
                        }
                    }
                }

                database.disconnect();
            } catch (SQLException e) {
                System.out.println("SearchUser.getUserData()...SQL Exception: " + e);
            } catch (Exception e) {
                System.out.println("SearchUser.getUserData()...Exception: " + e);
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