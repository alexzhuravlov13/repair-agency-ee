<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="repairFormAdd.h"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <form method="POST" action="${pageContext.request.contextPath}/app/user/addRepairForm"
          class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="repairFormAdd.h"/></h2>

        <fmt:message key="repairFormAdd.car" var="carPh"/>
        <div class="row mb-3">
            <div class="col-xs-15">
                <input type="text" name="car" class="form-control"
                       placeholder="${carPh}"
                       autofocus="autofocus"/>
            </div>
        </div>


        <fmt:message key="repairFormAdd.shortDescription" var="shortDescriptionPh"/>
        <div class="row mb-3">
            <div class="col-xs-15">
                <input type="text" name="shortDescription" class="form-control"
                       placeholder="${shortDescriptionPh}"/>
            </div>
        </div>


        <fmt:message key="repairFormAdd.description" var="descriptionPh"/>
        <div class="row mb-3">
            <div class="col-xs-15">
                <input type="text" name="description" class="form-control"
                       placeholder="${descriptionPh}"/>
            </div>
        </div>

        <c:if test="${repairFormEmptyFields ne null}">
            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                <span><fmt:message key="repairForm.error"/></span>
            </div>
        </c:if>


        <fmt:message key="repairFormAdd.save" var="save"/>
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