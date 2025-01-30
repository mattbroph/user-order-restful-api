<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />
<html>
    <body>
        <h2>User Display Exercise</h2>
        <a href = "searchUser">Click Here to Search for All Users</a>
        <h2>Use the form below to search by last name</h2>
        <form action="searchUser" method="GET">
            <%-- Search Term --%>
            <label for="searchTerm">Search Term</label>
            <input type="text" name="searchTerm" id="searchTerm">
            <br>
            <input type="submit" name="" value="Search" />
        </form>
    </body>
</html>