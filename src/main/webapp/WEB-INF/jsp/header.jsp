<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="cart.button" var="cart"/>
    <fmt:message key="logout.button" var="logout"/>
    <fmt:message key="common.password" var="password"/>
    <fmt:message key="profile.button" var="profile"/>
    <fmt:message key="register.button" var="register"/>
    <fmt:message key="user.button" var="user"/>
    <fmt:message key="catalog.button" var="catalog"/>
    <fmt:message key="home.button" var="home"/>
    <fmt:message key="common.admin" var="admin"/>
    <fmt:message key="common.users" var="users"/>
    <fmt:message key="addGame" var="addGame"/>
    <fmt:message key="publishers.button" var="publishers"/>
    <fmt:message key="transactions.button" var="transactions"/>
    <fmt:message key="addPublisher" var="addPublisher"/>
    <fmt:message key="games" var="games"/>
    <fmt:message key="common.email" var="email"/>
    <fmt:message key="login.button" var="login"/>
    <fmt:message key="language.button" var="lang"/>
</fmt:bundle>

<%--@elvariable id="role" type="epam.andrew.gameShop.entity.Role"--%>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="loggedUser" type="epam.andrew.gameShop.entity.User"--%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<<div class="navbar-wrapper">
<div class="container">
    <div class="navbar navbar-inverse navbar-static-top">

        <div class="navbar-header">
            <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="navbar-brand" href="<c:url value="/do/home"/>">Game Shop</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="<c:url value="/do/home"/>">${home}</a></li>
                <li><a href="<c:url value="/do/catalog"/>">${catalog}</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user}<b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/do/register"/>">${register}</a></li>
                        <c:if test="${loggedUser.role.name.equals('user') || loggedUser.role.name.equals('admin')}">
                            <li><a href="<c:url value="/do/userProfile?id=${user.id}"/>">${profile}</a></li>
                            <li><a href="<c:url value="/do/changePassword?id=${user.id}"/>">${password}</a></li>
                            <li><a href="<c:url value="/do/logout"/>">${logout}</a></li>
                        </c:if>
                    </ul>
                </li>
                <li><a href="<c:url value="/do/cart"/>">${cart}</a></li>
                <c:if test="${loggedUser.role.name.equals('admin')}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${admin}<b
                                class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/do/users"/>">${users}</a></li>
                            <li><a href="<c:url value="/do/transactionManage"/>">${transactions}</a></li>
                            <li><a href="<c:url value="/do/publishers"/>">${publishers}</a></li>
                            <li><a href="<c:url value="/do/addPublisher"/>">${addPublisher}</a></li>
                            <li><a href="<c:url value="/do/addGame"/>">${addGame}</a></li>
                            <li><a href="<c:url value="/do/gameManage"/>">${games}</a></li>
                        </ul>
                    </li>
                </c:if>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${lang}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/do/locale?locale=ru"/>">Ru</a></li>
                        <li><a href="<c:url value="/do/locale?locale=en"/>">Eng</a></li>
                    </ul>
                </li>
            </ul>
            <c:if test="${!(loggedUser.role.name == 'user'  || loggedUser.role.name == 'admin')}">
                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right" action="<c:url value="/do/login"/>" method="post" c>
                        <div class="form-group">
                            <label>
                                <input type="text" name="email" placeholder="${email}" class="form-control">
                            </label>
                        </div>
                        <div class="form-group">
                            <label>
                                <input type="password" name="password" placeholder="${password}" class="form-control">
                            </label>
                        </div>
                        <button type="submit" class="btn btn-success">${login}</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</div>
</div>