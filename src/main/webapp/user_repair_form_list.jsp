<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <title><fmt:message key="repairForm.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container mt-3">
    <c:if test="${not empty amount}">
        <h5><fmt:message key="users.Amount"/>: ${amount}</h5>
    </c:if>
    <h5><fmt:message key="repairForm.title"/></h5>
    <div class="row">
        <div class="col m-3" style="max-width: 20%; display:inline-block">
            <button type="button" class="btn btn-primary m-3"
                    onclick="location.href='/repairs/add'">
                <fmt:message key="repairForm.add"/></button>
        </div>
    </div>


    <table class="table table-striped table-light">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><a href="${basePath}/page/${currentPage}?sortField=creationDate&sortDir=${reverseSortDir}">
                <fmt:message key="repairForm.Created"/></a></th>
            <th scope="col"><fmt:message key="repairForm.Author"/></th>
            <th scope="col"><fmt:message key="repairForm.car"/></th>
            <th scope="col"><fmt:message key="repairForm.ShortDescription"/></th>
            <th scope="col"><a href="${basePath}/page/${currentPage}?sortField=status&sortDir=${reverseSortDir}">
                <fmt:message key="repairForm.Status"/></a></th>
            <th scope="col"><a href="${basePath}/page/${currentPage}?sortField=price&sortDir=${reverseSortDir}">
                <fmt:message key="repairForm.price"/></a></th>
            <th scope="col"><fmt:message key="users.Action"/></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="repairForm" items="${repairForms}" varStatus="i">
            <tr>
                <c:choose>
                    <c:when test="${currentPage != 1}">
                        <td>${i.index+1+(currentPage-1)*10}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${i.index+1}</td>
                    </c:otherwise>
                </c:choose>

                <td>${repairForm.creationDate.toLocalDate()}</td>
                <td>${repairForm.author.firstName} ${repairForm.author.lastName}</td>
                <td>${repairForm.car} </td>
                <td>${repairForm.shortDescription} </td>
                <sec:authorize access="hasRole('ROLE_MANAGER')">
                    <c:choose>
                        <c:when test="${repairForm.repairmanId==0}">
                            <td><fmt:message key="repairFormEdit.repairmanNull"/></td>
                        </c:when>
                        <c:otherwise>
                            <td>${repairForm.repairmanId} </td>
                        </c:otherwise>
                    </c:choose>
                </sec:authorize>
                <td>${repairForm.status} </td>
                <td>${repairForm.price} </td>
                <td>
                    <button type="button" class="btn btn-outline-info"
                            onclick="location.href='/repairs/view/${repairForm.id}'">
                        <fmt:message key="repairForm.view"/></button>

                    <c:if test="${repairForm.status eq statusReady}">
                        <button type="button" class="btn btn-outline-primary"
                                onclick="location.href='/repairs/review/${repairForm.id}'">
                            <fmt:message key="repairForm.review"/></button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <nav aria-label="...">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item item">
                    <a class="page-link"
                       href="${basePath}/page/${currentPage - 1}?sortField=${sortField}&sortDir=${sortDir}"
                       tabindex="-1"><spring:message
                            code="pagination.previous"/></a>
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
                        <li class="page-item"><a class="page-link"
                                                 href="${basePath}/page/${i}?sortField=${sortField}&sortDir=${sortDir}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage lt totalPages}">
                <li class="page-item">
                    <a class="page-link"
                       href="${basePath}/page/${currentPage + 1}?sortField=${sortField}&sortDir=${sortDir}"><spring:message
                            code="pagination.next"/></a>
                </li>
            </c:if>
        </ul>
    </nav>
    <jsp:include page="navbottom.jsp"/>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>