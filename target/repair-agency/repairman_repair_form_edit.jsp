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
    <form method="POST" action="${pageContext.request.contextPath}/app/repairman/saveRepairForm"
          class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><fmt:message key="repairFormView.name"/></h2>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message
                        key="repairForm.Author"/></strong>: ${sessionScope.editedForm.author.firstName} ${sessionScope.editedForm.author.lastName}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message
                        key="repairForm.Created"/></strong>: ${sessionScope.editedForm.creationDate.toLocalDate()}
            </div>
        </div>


        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message key="repairForm.car"/></strong>: ${sessionScope.editedForm.car}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message
                        key="repairForm.ShortDescription"/></strong>: ${sessionScope.editedForm.shortDescription}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><fmt:message key="repairForm.description"/></strong>: ${sessionScope.editedForm.description}
            </div>
        </div>

        <fmt:message key="repairForm.Status"/>
        ${sessionScope.editedForm.status}
        <div class="row mb-3">
            <c:choose>
                <c:when test="${(editedForm.status ne sessionScope.statusReady) and (editedForm.status ne sessionScope.statusCanceled)}">
                    <div class="col-xs-15">
                        <select
                                class="form-select form-select-sm"
                                name="status">
                            <c:forEach items="${statuses}" var="status">
                                <option value="${status.name()}">${status.name()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-xs-15">
                        <input name="status" disabled="disabled" class="form-control"/>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>


        <fmt:message key="repairForm.price" var="pricePh"/>
        <div class="row mb-3">
            <div class="col-xs-15">
                <input name="number" disabled="disabled" value="${sessionScope.editedForm.price}" class="form-control"/>
            </div>
        </div>

        <fmt:message key="repairFormView.ok" var="ok"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit"
                        class="btn btn-success">${ok}
                </button>
            </div>
        </div>

    </form>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>

