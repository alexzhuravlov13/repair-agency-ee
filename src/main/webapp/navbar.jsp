<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <link rel="shortcut icon" href="#">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #eee;">
    <div class="container-fluid">
        <span class="navbar-brand">Repair agency</span>

        <li class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="nav mr-auto">
                <c:forEach var="role" items="${sessionScope.roles}">
                    <c:if test="${role eq sessionScope.roleAdmin}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/app/admin/listUsers"><fmt:message
                                    key="navbar.users"/></a>
                        </li>
                    </c:if>
                </c:forEach>
                <c:forEach var="role" items="${sessionScope.roles}">
                    <c:if test="${role eq sessionScope.roleManager}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/app/manager/listUsers"><fmt:message
                                    key="navbar.users"/></a>
                        </li>
                    </c:if>
                </c:forEach>

                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/app/user/userRepairFormList"><fmt:message
                            key="navbar.RepairFormList"/></a>
                </li>
                <c:forEach var="role" items="${sessionScope.roles}">
                    <c:if test="${role eq sessionScope.roleManager}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/app/manager/managerRepairFormList"><fmt:message
                                    key="navbar.AllRepairFormList"/></a>
                        </li>
                    </c:if>
                </c:forEach>

                <c:forEach var="role" items="${sessionScope.roles}">
                    <c:if test="${role eq sessionScope.roleRepairman}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/app/repairman/repairmanRepairFormList"><fmt:message
                                    key="navbar.AllRepairFormList"/></a>
                        </li>
                    </c:if>
                </c:forEach>


                <li class="nav-item">
                    <span class="nav-link"><fmt:message key="changeLocale"/>:</span>
                <li class="dropdown" style="padding-top: 3px">
                    <select class="form-select form-select-sm" id="locales">
                        <option value=""></option>
                        <option value="en"><fmt:message key="locales.en"/></option>
                        <option value="ru"><fmt:message key="locales.ru"/></option>
                    </select>
                </li>

                <li>
                    <c:url value="/app/logout?${_csrf.parameterName}=${_csrf.token}" var="logoutUrl"/>
                    <form class="d-flex position-absolute end-0 me-2" action="${logoutUrl}" method="POST"
                          enctype="multipart/form-data">
                        <span class="navbar-text mr-sm-2">Email: ${sessionScope.userName}</span>
                        <button type="Logout" class="btn btn-outline-danger ms-1">
                            <fmt:message key="login.LogOut"/></button>
                    </form>
                </li>
            </ul>
    </div>
</nav>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#locales").change(function () {
            const selectedOption = $('#locales').val();
            if (selectedOption !== '') {
                window.location.replace('?lang=' + selectedOption);
            }
        });
    });
</script>
</body>
</html>