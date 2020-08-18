<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 11.08.2020
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="common.users" var="users"/>
    <fmt:message key="id" var="id"/>
    <fmt:message key="name" var="userName"/>
    <fmt:message key="login" var="userLogin"/>
    <fmt:message key="actions" var="actions"/>
    <fmt:message key="common.email" var="userEmail"/>
    <fmt:message key="cash" var="userCash"/>
</fmt:bundle>

<%--@elvariable id="loggedUser" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="users" type="java.util.List"--%>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
    body {
        background: url(https://mileyfrost.com/wp-content/uploads/2018/06/156689-download-white-gradient-background-2447x1468-1080p.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title>${users}</title>
<jsp:include page="header.jsp"/>
<body>
<section class="pro-box">
    <div class="form-inline p-3">
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-8"><h2><b>${users}</b></h2></div>
                    </div>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${userName}</th>
                        <th>${userLogin}</th>
                        <th>${userEmail}</th>
                        <th>${userCash}</th>
                        <th>${actions}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${user.email}</td>
                            <td>${user.cash}</td>
                            <td>
                                <a class="add" title="Detail" href="<c:url value="/do/editBalance?userId=${user.id}"/>"
                                   data-toggle="tooltip"><i
                                        class="material-icons"></i></a>
                                <a class="delete" title="Delete"
                                   href="<c:url value="/do/deleteUser?userId=${user.id}"/>" data-toggle="tooltip"><i
                                        class="material-icons"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row justify-content-center" style="margin-top: 30px">
        <div class="col-12 p-0">
            <:pagination url="/do/gameUsers" pagesCount="${pagesCount}"/>
        </div>
    </div>
</section>
