<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="head.jsp" />

<html><body>

<div class="container-fluid">

    <a href="${pageContext.request.contextPath}">Go Home</a>
    <br>
    <%-- If a user was just added, display success or failure message --%>
    <c:if test="${not empty userAddMessage}">
        <h2>${userAddMessage}</h2>
    </c:if>

    <h2>Search Results:</h2>

    <c:if test="${not empty users}">
        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>User Name</th>
                <th>Age</th>
            </tr>

            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.userName}</td>
                    <td>${user.userAge}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${empty users}">
        <h2>No users were found</h2>
    </c:if>

    <%-- Clear userAddMessage --%>
    <c:if test="${not empty userAddMessage}">
        <c:remove var="userAddMessage" scope="session" />
    </c:if>


</div>

</body>
</html>
