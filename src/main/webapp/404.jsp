<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <link rel="shortcut icon" href="#">
    <title>error</title>
</head>
<body>
<jsp:include page="locale.jsp"/>
<div class="container">
    <h3><fmt:message key="error"/></h3>
    <h5><fmt:message key="error.feedback"/></h5>
    <a href="${pageContext.request.contextPath}/app/home"><fmt:message key="error.back"/></a>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>
