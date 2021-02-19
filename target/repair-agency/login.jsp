<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <link rel="shortcut icon" href="#">
    <meta charset="utf-8">
    <title>Log in with your account</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="locale.jsp"/>
<div class="container">

    <form method="POST" action="${pageContext.request.contextPath}/app/loginUser"
          class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="login.LogIn"/></h2>
        <div class="row mb-3">
            <div class="col-xs-offset-1 col-xs-10">
                <fmt:message key="registration.Email" var="emailPh"/>
                <input name="email" type="email" class="form-control" placeholder="${emailPh}"
                       autofocus="autofocus">
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-xs-offset-1 col-xs-10">
                <fmt:message key="registration.Password" var="passwordPh"/>
                <input name="pass" type="password" class="form-control" placeholder="${passwordPh}">
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-xs-15">
                <div>
                    <!-- Check for login error -->
                    <c:if test="${error!= null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span><fmt:message key="login.error"/></span>
                        </div>
                    </c:if>
                    <c:if test="${errorField!= null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span><fmt:message key="login.errorField"/></span>
                        </div>
                    </c:if>
                    <!-- Check for logout -->
                    <c:if test="${logout != null}">
                        <div class="alert alert-success col-xs-offset-1 col-xs-10">
                            <span><fmt:message key="login.message"/></span>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col text-center">
                <fmt:message key="login.SignIn" var="signInPh"/>
                <button type="submit" class="btn btn-primary">${signInPh}</button>
            </div>
        </div>
        <div class="row mb-3">
            <fmt:message key="login.Create" var="createPh"/>
            <h4 class="text-center"><a href="${pageContext.request.contextPath}/app/registration">${createPh}</a></h4>
        </div>
    </form>

</div>

</body>
</html>