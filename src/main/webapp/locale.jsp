<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <link rel="shortcut icon" href="#">
    <title>Users list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #eee;">
    <div class="container-fluid">
        <span class="navbar-brand">Repair agency</span>

        <li class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <span class="nav-link"><fmt:message key="changeLocale"/>:</span>
                <li class="dropdown ms-3">
                    <select class="form-select form-select-sm" id="locales">
                        <option value=""></option>
                        <option value="en"><fmt:message key="locales.en"/></option>
                        <option value="ru"><fmt:message key="locales.ru"/></option>
                    </select>
                </li>
            </ul>
        </li>
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