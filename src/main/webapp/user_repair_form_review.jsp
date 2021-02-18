<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <link rel="shortcut icon" href="#">
    <meta charset="utf-8">
    <title><fmt:message key="repairFormAdd.h"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <form method="POST" action="/app/user/saveReview"
          class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="repairFormView.name"/></h2>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message
                        key="repairForm.Created"/></strong>: ${sessionScope.repairForm.creationDate.toLocalDate()}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message key="repairForm.car"/></strong>: ${sessionScope.repairForm.car}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message
                        key="repairForm.ShortDescription"/></strong>: ${sessionScope.repairForm.shortDescription}
            </div>
        </div>


        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message key="repairForm.description"/></strong>: ${sessionScope.repairForm.description}
            </div>
        </div>


        <fmt:message key="repairForm.review" var="reviewPh"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <strong><fmt:message key="repairForm.review"/></strong>
                <input type="text" name="feedback" value="${sessionScope.repairForm.feedback}" class="form-control"/>
            </div>
        </div>


        <fmt:message key="repairFormView.ok" var="ok"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit"
                        class="btn btn-success"><fmt:message key="userEdit.save"/></button>

            </div>
        </div>

    </form>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>

