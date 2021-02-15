<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
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
                <li class="nav-item">
                    <a class="nav-link active" href="/"><fmt:message key="navbar.home"/></a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/admin/listUsers"><fmt:message
                                key="navbar.users"/></a>
                    </sec:authorize>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/app/user/userRepairFormList"><fmt:message
                            key="navbar.RepairFormList"/></a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="hasRole('ROLE_MANAGER')">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/repairs/manager/list"><fmt:message
                                key="navbar.AllRepairFormList"/></a>
                    </sec:authorize>
                </li>
                <li class="nav-item">
                    <sec:authorize access="hasRole('ROLE_REPAIRMAN')">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/repairs/repairman/list"><fmt:message
                                key="navbar.AllRepairFormList"/></a>
                    </sec:authorize>
                </li>
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
                        <span class="navbar-text mr-sm-2">Email: ${pageContext.request.userPrincipal.name}</span>
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