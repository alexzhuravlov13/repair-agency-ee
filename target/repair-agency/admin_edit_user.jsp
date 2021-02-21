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
    <form method="POST" action="${pageContext.request.contextPath}/app/admin/saveEditedUser"
          class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="userEdit.h"/></h2>

        <input type="hidden" name="userId" value="${sessionScope.editedUser.userId}"/>

        <input type="hidden" name="amount" value="${sessionScope.editedUser.amount}"/>

        <fmt:message key="registration.Email" var="emailPh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="email" name="email" value="${sessionScope.editedUser.email}"
                       class="form-control"
                       placeholder="${emailPh}"
                       autofocus="autofocus"/>
            </div>
        </div>

        <fmt:message key="registration.FirstName" var="firstNamePh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="text" name="firstName" value="${sessionScope.editedUser.firstName}"
                       class="form-control"
                       placeholder="${firstNamePh}"/>
            </div>
        </div>

        <fmt:message key="registration.LastName" var="lastNamePh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="text" name="lastName" value="${sessionScope.editedUser.lastName}"
                       class="form-control"
                       placeholder="${lastNamePh}"/>
            </div>
        </div>


        <fmt:message key="registration.Password" var="passwordPh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="password" name="password" value="${sessionScope.editedUser.password}"
                       class="form-control"
                       placeholder="${passwordPh}"/>
            </div>
        </div>


        <fmt:message key="userEdit.roles"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                ${sessionScope.editedUser.roles}
                <select
                        class="form-select form-select-sm"
                        multiple="multiple"
                        name="roles">
                    <c:forEach items="${sessionScope.allRoles}" var="role">
                        <option value="${role.name()}">${role.name()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <c:if test="${userEmptyFields ne null}">
            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                <span><fmt:message key="login.errorField"/></span>
            </div>
        </c:if>


        <fmt:message key="userEdit.save" var="save"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-success">${save}</button>
            </div>
        </div>

    </form>
</div>
</body>
</html>