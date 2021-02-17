<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="jstlC" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="repairFormAdd.h"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <springform:form method="POST" action="/repairs/manager/editRepairForm" modelAttribute="repairFormAttribute"
                     class="mx-auto p-5 m-3" style="width: 50%; background-color: #eee;">
        <h2><spring:message code="repairFormView.name"/></h2>
        <springForm:hidden path="id"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message
                        code="repairForm.Author"/></strong>: ${repairFormAttribute.author.firstName} ${repairFormAttribute.author.lastName}
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message
                        code="repairForm.Created"/></strong>: ${repairFormAttribute.creationDate.toLocalDate()}
            </div>
        </div>
        <springForm:hidden path="creationDate"/>

        <springForm:hidden path="author"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.car"/></strong>: ${repairFormAttribute.car}
            </div>
        </div>
        <springForm:hidden path="car"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message
                        code="repairForm.ShortDescription"/></strong>: ${repairFormAttribute.shortDescription}
            </div>
        </div>
        <springForm:hidden path="shortDescription"/>

        <div class="row mb-3">
            <div class="col-xs-15">
                <strong><spring:message code="repairForm.description"/></strong>: ${repairFormAttribute.description}
            </div>
        </div>
        <springForm:hidden path="description"/>


        <spring:message code="repairFormEdit.repairman"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <div class="col-xs-15">
                <springForm:select
                        cssClass="form-select form-select-sm"
                        multiple="true"
                        path="repairman"
                        items="${repairmans}"
                        itemLabel="firstName" itemValue="userId"/>
            </div>
        </div>

        <spring:message code="repairForm.Status"/>
        <div class="row mb-3 ${status.error ? 'has-error' : ''}">
            <jstlC:choose>
                <jstlC:when test="${repairFormAttribute.status ne statusReady}">
                    <div class="col-xs-15">
                        <springForm:select
                                cssClass="form-select form-select-sm"
                                path="status"
                                items="${statuses}"
                                itemLabel="name" itemValue="name"/>
                    </div>
                </jstlC:when>
                <jstlC:otherwise>
                    <div class="col-xs-15">
                        <springForm:input path="status" disabled="true" class="form-control"/>
                    </div>
                </jstlC:otherwise>
            </jstlC:choose>

        </div>

        <jstlC:if test="${param.error != null}">
            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                <span><spring:message code="repairFormEdit.error"/></span>
            </div>
        </jstlC:if>


        <spring:bind path="price">
            <spring:message code="repairForm.price" var="pricePh"/>
            <div class="row mb-3 ${status.error ? 'has-error' : ''}">
                <div class="col-xs-15">
                    <springForm:input type="number" min="0" value="0" step=".01" path="price" class="form-control"
                                      placeholder="${pricePh}"/>
                    <springForm:errors path="price"/>
                </div>
            </div>
        </spring:bind>


        <spring:message code="repairFormView.ok" var="ok"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button type="submit"
                        class="btn btn-success"
                        onclick="location.href='/repairs/list'">${ok}
                </button>
            </div>
        </div>

    </springform:form>
</div>
<jsp:include page="navbottom.jsp"/>
</body>
</html>

