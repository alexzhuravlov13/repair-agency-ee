<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <title><fmt:message key="users.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container-fluid">
    <div class="container pb-5">
        <h3><fmt:message key="users.title"/></h3>
        <table class="table table-striped table-light">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Email</th>
                <th scope="col"><fmt:message key="users.First"/></th>
                <th scope="col"><fmt:message key="users.Last"/></th>
                <th scope="col"><fmt:message key="users.Roles"/></th>
                <th scope="col"><fmt:message key="users.Amount"/></th>
                <th scope="col"><fmt:message key="users.Action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="i">
                <tr>
                    <c:choose>
                        <c:when test="${currentPage != 1}">
                            <td>${i.index+1+(currentPage-1)*10}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${i.index+1}</td>
                        </c:otherwise>
                    </c:choose>

                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.roles}</td>
                    <td>${user.amount}</td>
                    <td>
                        <button type="button" class="btn btn-outline-info"
                                onclick="location.href='/app/admin/listUsers/edit?userId=${user.userId}'">
                            <fmt:message key="userEdit.h"/></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav aria-label="...">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item item">
                        <a class="page-link" href="${basePath}/page/${currentPage - 1}" tabindex="-1"><fmt:message
                                key="pagination.previous"/></a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active">
                                <a class="page-link">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="${basePath}/page/${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="${basePath}/page/${currentPage + 1}"><fmt:message
                                key="pagination.next"/></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>