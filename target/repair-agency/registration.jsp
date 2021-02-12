<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="locale.jsp"/>
<div class="container">
    <form action="${pageContext.request.contextPath}/app/register" method="POST" class="mx-auto p-5 m-3"
          style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="registration.createAccount"/></h2>


        <div class="row mb-3">
            <div class="col-xs-15">
                <div>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span><fmt:message key="registration.emailRegistered"/></span>
                        </div>
                    </c:if>

                    <c:if test="${not empty status.error}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span><fmt:message key="registration.errorEmailBlank"/></span>
                        </div>
                    </c:if>

                </div>
            </div>
        </div>

        <fmt:message key="registration.Email" var="emailPh"/>
        <div class="row mb-3">
            <div class="col-xs-15">
                <input autofocus="autofocus" class="form-control" name="email"
                       placeholder="${emailPh}"
                       type="email"/>
            </div>
        </div>


        <fmt:message key="registration.FirstName" var="firstNamePh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="text" name="firstName" class="form-control"
                       placeholder="${firstNamePh}"/>
            </div>
        </div>


        <fmt:message key="registration.LastName" var="lastNamePh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="text" name="lastName" class="form-control"
                       placeholder="${lastNamePh}"/>
            </div>
        </div>

        <fmt:message key="registration.Password" var="passwordPh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <input type="password" name="password" class="form-control"
                       placeholder="${passwordPh}"/>
            </div>
        </div>

        <fmt:message key="registration.Register" var="register"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit" class="btn btn-primary">${register}</button>
            </div>
        </div>

        <fmt:message key="registration.AlreadyRegistered" var="alreadyRegistered"/>
        <div class="row mb-3">
            <h4 class="text-center"><a href="/app/login">${alreadyRegistered}</a></h4>
        </div>
    </form>
</div>

</body>
</html>
