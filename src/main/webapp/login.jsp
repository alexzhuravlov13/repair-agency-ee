<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
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

    <springForm:form method="POST" action="${contextPath}/login"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="login.LogIn"/></h2>
        <div class="row mb-3">
            <div class="col-xs-offset-1 col-xs-10">
                <spring:message code="registration.Email" var="emailPh"/>
                <input name="username" type="email" class="form-control" placeholder="${emailPh}"
                       autofocus="autofocus">
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-xs-offset-1 col-xs-10">
                <spring:message code="registration.Password" var="passwordPh"/>
                <input name="password" type="password" class="form-control" placeholder="${passwordPh}">
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-xs-15">
                <div>
                    <!-- Check for login error -->
                    <jstlC:if test="${param.error != null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            <span><spring:message code="login.error"/></span>
                        </div>
                    </jstlC:if>
                    <!-- Check for logout -->
                    <jstlC:if test="${param.logout != null}">
                        <div class="alert alert-success col-xs-offset-1 col-xs-10">
                            <span><spring:message code="login.message"/></span>
                        </div>
                    </jstlC:if>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col text-center">
                <spring:message code="login.SignIn" var="signInPh"/>
                <button type="submit" class="btn btn-primary">${signInPh}</button>
            </div>
        </div>
        <div class="row mb-3">
            <spring:message code="login.Create" var="createPh"/>
            <h4 class="text-center"><a href="/registration">${createPh}</a></h4>
        </div>
    </springForm:form>


</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>