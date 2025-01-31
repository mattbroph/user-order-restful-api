<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<html>
    <body>
        <%-- Search All Users --%>
        <h2>Search all Users</h2>
        <a href = "searchUser">Click Here to Search for All Users</a>
        <br>
        <br>
        <%-- Search Last Name        --%>
        <h2>Search by last name</h2>
        <form action="searchUser" method="GET">
            <label for="searchTerm">Last Name</label>
            <input type="text" name="searchTerm" id="searchTerm" required>
            <br>
            <br>
            <input type="submit" name="" value="Search" />
        </form>
        <br>
        <br>
        <%-- Add User --%>
        <h2>Add a user</h2>
        <form action="addUser" method="POST">
            <%-- First Name --%>
            <label for="firstName">First Name</label>
            <input type="text" name="firstName" id="firstName" required>
            <br>
            <%-- Last Name--%>
            <label for="lastName">Last Name</label>
            <input type="text" name="lastName" id="lastName" required>
            <br>
            <%-- User Name --%>
            <label for="userName">User Name</label>
            <input type="text" name="userName" id="userName" required>
            <br>
            <%-- Date of Birth --%>
            <label for="dateOfBirth">Date of Birth</label>
            <input type="date" name="dateOfBirth" id="dateOfBirth" required>
            <br>
            <br>
            <%-- Submit --%>
            <input type="submit" name="" value="Add User" />
        </form>
    </body>
</html>