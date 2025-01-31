package edu.matc.controller;

import edu.matc.persistence.UserData;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
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
        int rowsAffected;

        // Create a UserData Instance
        UserData userData = new UserData();

        // Extract data for the new user from the HTML form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String dateOfBirth = request.getParameter("dateOfBirth");

        // Set the url for the forward
        String url = "/results.jsp";

        // Call the add employee method and pass the form data
        rowsAffected = userData.addUser(firstName,
                lastName, userName, dateOfBirth);

        // Provide a success or fail message for the db insert
        if (rowsAffected == 1) {
            session.setAttribute("userAddMessage",
                    "Your user was added to the database");
            request.setAttribute("users", userData.getSearchedUser(lastName));
        } else {
            session.setAttribute("userAddMessage",
                    "Something went wrong, your user was not "
                            + " added to the database");
        }

        // Send a redirect to the results jsp
        // response.sendRedirect(url);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }
}