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

    /** Builds database query to search for all users and calls method to
     * retrieve query results.
     *
     * @return the list of users that meet this criteria
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        users = getUsersData(sql, users, null);
        return users;
    }

    /** Builds database query to search for last name and calls method to
    * retrieve query results.
     *
     * @param searchTerm the last name of the user to search for
     * @return the list of users that meet this criteria
     */
    public List<User> getSearchedUser(String searchTerm) {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE last_name = ?";
        users = getUsersData(sql, users, searchTerm);
        return users;
        }

        /** Connects to the database, runs select query and builds a list of
        * users that meet criteria.
        *
        * @param sql the select query to run
        * @param users the empty list of users to add to
        * @param searchTerm the search term to look for
        * @return the list of users that meet the given criteria
        */
        public List<User> getUsersData(String sql, List<User> users,
                 String searchTerm) {

            Database database = Database.getInstance();
            Connection connection = null;

            try {
                database.connect();
                connection = database.getConnection();

                try (PreparedStatement preparedStatement
                         = connection.prepareStatement(sql)) {
                    // If no search term is given, program just selects all
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


    /** Adds a user to the user database
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userName the user's username
     * @param dateOfBirth the user's date of birth
     * @return the number of rows inserted into the database
     */
        public int addUser(String firstName, String lastName, String userName,
                 String dateOfBirth) {

            Database database = Database.getInstance();
            Connection connection = null;

            String sql = "INSERT into users (first_name, last_name, user_name, "
                    + "date_of_birth) VALUES (?, ?, ?, ?)";

            int rowsAffected = 0;

            try {
                database.connect();
                connection = database.getConnection();

                try (PreparedStatement preparedStatement
                             = connection.prepareStatement(sql)) {

                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, userName);
                    preparedStatement.setString(4, dateOfBirth);

                    rowsAffected = preparedStatement.executeUpdate();
                }

                database.disconnect();
            } catch (SQLException e) {
                System.out.println("SearchUser.getUserData()...SQL Exception: " + e);
            } catch (Exception e) {
                System.out.println("SearchUser.getUserData()...Exception: " + e);
            }

            return rowsAffected;
        }

    /** Creates a user and sets it's instance variables
     *
     * @param results the database results used to build the user
     * @return the user once built
     * @throws SQLException if there is an issue with the SQL
     */
    private User createUserFromResults(ResultSet results) throws SQLException {

        String dateOfBirth;
        LocalDate formattedDate;
        User user = new User();

        user.setFirstName(results.getString("first_name"));
        user.setLastName(results.getString("last_name"));
        user.setUserName(results.getString("user_name"));
        user.setId(results.getInt("id"));

        // Store the result as a string so you can convert it to LocalDate
        dateOfBirth = results.getString("date_of_birth");
        // Convert to LocalDate
        formattedDate = LocalDate.parse(dateOfBirth);
        // Set the user instance variable
        user.setDateOfBirth(formattedDate);

        return user;
    }

}