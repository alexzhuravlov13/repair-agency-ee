<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <h2>
           Users List
        </h2>
        <c:forEach var="user" items="${usersMap}">
            Key is ${user.key}
            Value is ${user.value}
        </c:forEach>

    </body>
</html>
