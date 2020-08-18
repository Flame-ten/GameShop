<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="editProfile.button" var="editProfile"/>
    <fmt:message key="common.profile" var="profile"/>
    <fmt:message key="name" var="userName"/>
    <fmt:message key="surname" var="userSur"/>
    <fmt:message key="login" var="userLogin"/>
    <fmt:message key="common.email" var="userEmail"/>
    <fmt:message key="phone" var="userPhone"/>
    <fmt:message key="gender" var="userGender"/>
    <fmt:message key="country" var="user_country"/>
    <fmt:message key="cash" var="user_cash"/>
    <fmt:message key="transactions.button" var="transactions"/>
    <fmt:message key="password.button" var="password"/>
</fmt:bundle>

<%--@elvariable id="loggedUser" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title>${profile}</title>
<jsp:include page="header.jsp"/>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">${profile}</h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class=" col-md-9 col-lg-9 ">
                <table class="table table-user-information">
                    <tbody>
                    <tr>
                        <td>${userName}</td>
                        <td><p>${loggedUser.name}</p></td>
                    </tr>
                    <tr>
                        <td>${userSur}</td>
                        <td><p>${loggedUser.surname}</p></td>
                    </tr>

                    <tr>
                        <td>${userLogin}</td>
                        <td><p>${loggedUser.login}</p></td>
                    </tr>

                    <tr>
                        <td>${userEmail}</td>
                        <td><p>${loggedUser.email}</p></td>
                    </tr>

                    <tr>
                        <td>${userPhone}</td>
                        <td><p>${loggedUser.phone}</p></td>
                    </tr>

                    <tr>
                        <td>${userGender}</td>
                        <td><p>${loggedUser.gender}</p></td>
                    </tr>

                    <tr>
                        <td>${user_country}</td>
                        <td><p>${loggedUser.country}</p></td>
                    </tr>

                    <tr>
                        <td>${user_cash}</td>
                        <td><p>${loggedUser.cash}</p></td>
                    </tr>

                    </tbody>
                </table>

                <a href="<c:url value="/do/editProfile?userId=${user.id}"/>" class="btn btn-primary"><${editProfile}</a>
                <a href="<c:url value="/do/password?userId=${user.id}"/>" class="btn btn-primary">${password}</a>
                <a href="<c:url value="/do/userTransaction?userId=${user.id}"/>"
                   class="btn btn-primary">${transactions}</a>
            </div>
        </div>
    </div>
</div>


