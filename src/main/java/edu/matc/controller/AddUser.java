package edu.matc.controller;

import edu.matc.entity.User;
import edu.matc.persistence.UserDao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/** Listens for HTTP POST from Add User form and adds the new
 * user record to the database.
 *
 *@author mbrophy
 */
@WebServlet(
        name = "addUserServlet",
        urlPatterns = { "/addUser" }
)
public class AddUser extends HttpServlet {

    /** Adds a new user to the application database
     *
     *@param request the HttpServletRequest object
     *@param response the HttpServletRequest object
     *@exception ServletException if there is a Servlet failure
     *@exception IOException if there is an IO failure
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        // Get access to Session
        HttpSession session = request.getSession();

        // Number of rows inserted
        int newUserID;

        // Need to provide the results.jsp with a List even though only
        // one will be returned on an insert
        List<User> userList = new ArrayList<User>();

        // Create a UserData Instance
        UserDao userDao = new UserDao();

        // Extract data for the new user from the HTML form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String dateOfBirth = request.getParameter("dateOfBirth");

        LocalDate formattedBirthDate = LocalDate.parse(dateOfBirth);

        // Create the User
        User newUser = new User(firstName, lastName, userName, formattedBirthDate);

        // Insert the new user
        newUserID = userDao.insert(newUser);

        // Add the new user to the list for the jsp
        userList.add(userDao.getById(newUserID));

        // Provide a success or fail message for the db insert via the Session
        if (newUserID > 0) {
            session.setAttribute("userAddMessage",
                    "Your user was added to the database");
            // Pass the user "list" so it can be displayed on results.jsp
            request.setAttribute("users", userList);
        } else {
            session.setAttribute("userAddMessage",
                    "Something went wrong, your user was not "
                            + " added to the database");
        }

        // Set the url for the forward
        String url = "/results.jsp";

        // Send a redirect to the results jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }
}