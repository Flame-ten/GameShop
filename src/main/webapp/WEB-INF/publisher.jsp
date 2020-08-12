<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<%--@elvariable id="publisher" type="epam.andrew.gameShop.entity.Publisher"--%>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title><fmt:message key="publisher"/></title>
<jsp:include page="header.jsp"/>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title"><fmt:message key="publisher"/></h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class=" col-md-9 col-lg-9 ">
                <table class="table table-user-information">
                    <tbody>
                    <tr>
                        <td><fmt:message key="name"/></td>
                        <td><p>${publisher.name}</p></td>
                    </tr>

                    <tr>
                        <td><fmt:message key="email"/></td>
                        <td><p>${publisher.email}</p></td>
                    </tr>

                    <tr>
                        <td><fmt:message key="phone"/></td>
                        <td><p>${publisher.phone}</p></td>
                    </tr>

                    <tr>
                        <td><fmt:message key="common.address"/></td>
                        <td><p>${publisher.address}</p></td>
                    </tr>

                    <tr>
                        <td><fmt:message key="country"/></td>
                        <td><p>${publisher.country}</p></td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
