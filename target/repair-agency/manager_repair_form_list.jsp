<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <link rel="shortcut icon" href="#">
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

    <%--<div class="row">
        <div class="col m-3" style="max-width: 80%; display:inline-block">
            <c:if test="${not empty filterDto}">

                    <form action="/repairs/manager/list" method="post" modelAttribute="filterDto">
                        <div class="col m-3" style="display:inline-block">
                            <div><strong><fmt:message key="repairForm.filter"/></strong></div>
                        </div>

                        <div class="col m-3" style="display:inline-block">
                            <div class="row">
                                <div><fmt:message key="repairFormEdit.repairman"/></div>
                            </div>

                            <div class="row">
                                <form:select path="masterId" class="form-select form-select-sm m-3" id="selectMaster">
                                    <option value=""></option>
                                    <c:forEach items="${masters}" var="master">
                                        <option value="${master.userId}">${master.firstName} ${master.lastName}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>

                        <div class="col m-3" style="display:inline-block">
                            <div class="row">
                                <div><fmt:message key="repairForm.Status"/></div>
                            </div>

                            <div class="row">
                                <form:select path="status" class="form-select form-select-sm m-3" id="selectStatus">
                                    <option value=""></option>
                                    <c:forEach items="${statuses}" var="status">
                                        <option value="${status}">${status}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="col m-3" style="display:inline-block">
                            <div class="row">
                            </div>
                            <div class="row">
                                <div class="col">
                                    <button type="submit" class="btn btn-outline-success m-3"><spring:message
                                            code="repairForm.apply"/>
                                    </button>
                                </div>
                                <div class="col">
                                    <button onclick="location.href='/repairs/manager/list/clear'" type="button"
                                            class="btn btn-outline-danger m-3"><fmt:message key="repairForm.clear"/>
                                    </button>
                                </div>

                            </div>
                        </div>
                    </form>
            </c:if>
        </div>
    </div>--%>


    <table class="table table-striped table-light">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><a
                    href="${sessionScope.basePath}?page=${currentPage}&sortField=creationDate&sortDir=${reverseSortDir}">
                <fmt:message key="repairForm.Created"/></a></th>
            <th scope="col"><fmt:message key="repairForm.Author"/></th>
            <th scope="col"><fmt:message key="repairForm.car"/></th>
            <th scope="col"><fmt:message key="repairForm.ShortDescription"/></th>
            <th scope="col"><a
                    href="${sessionScope.basePath}?page=${currentPage}&sortField=repairman&sortDir=${reverseSortDir}">
                <fmt:message key="repairFormEdit.repairman"/></a>
            </th>
            <th scope="col"><a
                    href="${sessionScope.basePath}?page=${currentPage}&sortField=status&sortDir=${reverseSortDir}">
                <fmt:message key="repairForm.Status"/></a></th>
            <th scope="col"><a
                    href="${sessionScope.basePath}?page=${currentPage}&sortField=price&sortDir=${reverseSortDir}">
                <fmt:message key="repairForm.price"/></a></th>
            <th scope="col"><fmt:message key="users.Action"/></th>
            <th scope="col"><fmt:message key="users.Action"/></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="repairForm" items="${repairForms}" varStatus="i">
            <tr>
                <c:choose>
                    <c:when test="${currentPage != 1}">
                        <td>${i.index+1+(currentPage-1)*(perPageSize)}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${i.index+1}</td>
                    </c:otherwise>
                </c:choose>

                <td>${repairForm.creationDate.toLocalDate()}</td>
                <td>${repairForm.authorFirstName} ${repairForm.authorLastName}</td>
                <td>${repairForm.car} </td>
                <td>${repairForm.shortDescription} </td>
                <c:choose>
                    <c:when test="${repairForm.repairmanFirstName==null}">
                        <td><fmt:message key="repairFormEdit.repairmanNull"/></td>
                    </c:when>
                    <c:otherwise>
                        <td>${repairForm.repairmanFirstName} ${repairForm.repairmanLastName}  </td>
                    </c:otherwise>
                </c:choose>
                <td>${repairForm.status} </td>
                <td>${repairForm.price} </td>
                <td>
                    <button type="button" class="btn btn-outline-info"
                            onclick="location.href='/app/user/viewRepairForm?repairFormId=${repairForm.id}'">
                        <fmt:message key="repairForm.view"/></button>
                </td>

                <td>
                    <button type="button" class="btn btn-outline-info"
                            onclick="location.href='/app/manager/editRepairForm?repairFormId=${repairForm.id}'">
                        <fmt:message key="repairForm.edit"/></button>
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
                       href="${sessionScope.basePath}?page=${currentPage - 1}&sortField=${sortField}&sortDir=${sortDir}"
                       tabindex="-1"><fmt:message key="pagination.previous"/></a>
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
                                                 href="${sessionScope.basePath}?page=${i}&sortField=${sortField}&sortDir=${sortDir}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage lt totalPages}">
                <li class="page-item">
                    <a class="page-link"
                       href="${sessionScope.basePath}?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}"><fmt:message
                            key="pagination.next"/></a>
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