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
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="publisher" var="publisher_logo"/>
    <fmt:message key="name" var="publisherName"/>
    <fmt:message key="common.email" var="publisherEmail"/>
    <fmt:message key="phone" var="publisherPhone"/>
    <fmt:message key="common.address" var="publisherAddress"/>
    <fmt:message key="country" var="publisher_country"/>
</fmt:bundle>

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
<title>${publisher_logo}</title>
<jsp:include page="header.jsp"/>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">${publisher_logo}</h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class=" col-md-9 col-lg-9 ">
                <table class="table table-user-information">
                    <tbody>
                    <tr>
                        <td>${publisherName}</td>
                        <td><p>${publisher.name}</p></td>
                    </tr>

                    <tr>
                        <td>${publisherEmail}</td>
                        <td><p>${publisher.email}</p></td>
                    </tr>

                    <tr>
                        <td>${publisherPhone}</td>
                        <td><p>${publisher.phone}</p></td>
                    </tr>

                    <tr>
                        <td>${publisherAddress}</td>
                        <td><p>${publisher.address}</p></td>
                    </tr>

                    <tr>
                        <td>${publisher_country}</td>
                        <td><p>${publisher.country}</p></td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
