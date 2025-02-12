package edu.matc.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * A simple servlet to welcome the user.
 * @author pwaite
 */

@WebServlet(
        urlPatterns = {"/searchUser"}
)

public class SearchUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        UserData userData = new UserData();
//        String searchTerm = req.getParameter("searchTerm");

        /* If search term is null, then get all users.
        * If search term is not null, then use the search criteria.
        */
//        if (searchTerm != null) {
//            req.setAttribute("users", userData.getSearchedUser(searchTerm));
//        } else {
//            req.setAttribute("users", userData.getAllUsers());
//        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/results.jsp");
        dispatcher.forward(req, resp);
    }
}