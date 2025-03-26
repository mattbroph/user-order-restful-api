<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Referenced pages below to create custom error handling pages
https://help.sap.com/saphelp_em700_ehp01/helpdata/en/9a/e74d426332bd30e10000000a155106/content.htm?no_cache=true#:~:text=Configuration%20of%20custom%20error%20pages,mapping%20for%20it%20is%20used).
https://www.tutorialspoint.com/jsp/jsp_exception_handling.htm--%>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Ruh Roh! Something went wrong.</h1>
    <img src="images/scoobs.png" alt="Scooby Doo">

    <p>
        <b>The status code is:</b> <%= request.getAttribute("javax.servlet.error.status_code") %><br>
        <b>The error message is:</b> <%= request.getAttribute("javax.servlet.error.message") %><br>
        <b>The exception class is:</b> <%= request.getAttribute("javax.servlet.error.exception") %><br>
    </p>

</body>
</html>
