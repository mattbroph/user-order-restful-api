<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Ruh Roh! Something went wrong.</h1>
    <img src="images/scoobs.png" alt="Scooby Doo">

    <p>
        <b>The status code is:</b> <%= request.getAttribute("javax.servlet.error.status_code") %><br>
        <b>The error message again is:</b> <%= request.getAttribute("javax.servlet.error.message") %><br>
    </p>

</body>
</html>
