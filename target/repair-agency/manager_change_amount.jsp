<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <link rel="shortcut icon" href="#">
    <meta charset="utf-8">
    <title><fmt:message key="userEdit.h"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <form method="POST" action="${pageContext.request.contextPath}/app/manager/listUsers/saveAmount"
          class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="userEdit.changeAmount"/></h2>

        <springForm:hidden path="userId"/>

        <div class="col-xs-15">
            <strong><fmt:message key="users.Amount"/></strong>: ${editedUser.amount}
        </div>

        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <strong><fmt:message key="userEdit.changeAmount"/></strong>
            </div>
            <div class="col-xs-15">
                <input type="number" min="0" value="0" step=".01" name="amount" class="form-control"
                       placeholder="${amountPh}"/>
            </div>
        </div>


        <fmt:message key="userEdit.save" var="save"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-success">${save}</button>
            </div>
        </div>

    </form>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>