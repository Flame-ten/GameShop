<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<fmt:requestEncoding value="UTF-8"/>

<%--@elvariable id="cart" type="src\main\java\epam\andrew\gameShop\entity\Transaction"--%>
<%--@elvariable id="role" type="epam.andrew.gameShop.entity.Role"--%>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="language" type="java.util.Locale"--%>
<%--@elvariable id="items" type="java.util.List"--%>
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
            <a class="navbar-brand" href="home.jsp">Game Shop</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="<c:url value="/do/home"/>"><fmt:message key="home.button"/></a></li>
                <li class="active"><a href="<c:url value="/do/catalog"/>"> <fmt:message key="catalog.button"/></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="user.button"/><b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/do/showRegister"/>"><fmt:message key="register.button"/></a></li>
                        <c:if test="${loggedUser.role.name.equals('USER') || loggedUser.role.name.equals('ADMIN')}">
                            <li><a href="<c:url value="/do/userProfile?id=${user.id}"/>"><fmt:message
                                    key="profile.button"/></a></li>
                            <li><a href="<c:url value="/do/manage/changePassword?id=${user.id}"/>"><fmt:message
                                    key="password.button"/></a></li>
                            <li><a href="<c:url value="/do/logout"/>"><fmt:message key="logout.button"/></a></li>
                        </c:if>
                    </ul>
                </li>
                <li><a href="<c:url value="/do/cart"/>"> <fmt:message key="cart.button"/> </a></li>
                <c:if test="${loggedUser.role.name.equals('ADMIN')}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="common.admin"/><b
                                class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="manageUsers.jsp"><fmt:message key="common.users"/></a></li>
                            <li><a href="manageTransactions.jsp"><fmt:message key="transactions.button"/></a></li>
                            <li><a href="managePublishers.jsp"><fmt:message key="publishers.button"/></a></li>
                            <li><a href="addPublisher.jsp"><fmt:message key="addPublisher"/></a></li>
                        </ul>
                    </li>
                </c:if>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Lang<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/do/language?language=ru"/>">Ru</a></li>
                        <li><a href="<c:url value="/do/language?language=en"/>">Eng</a></li>
                    </ul>
                </li>
            </ul>
            <c:if test="${!(loggedUser.role.name == 'USER'  || loggedUser.role.name == 'ADMIN')}">
                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right" action="<c:url value="/do/login"/>" method="post">
                        <div class="form-group">
                            <label>
                                <input type="text" placeholder="Email" class="form-control">
                            </label>
                        </div>
                        <div class="form-group">
                            <label>
                                <input type="password" placeholder="Password" class="form-control">
                            </label>
                        </div>
                        <button type="submit" class="btn btn-success"><fmt:message key="login.button"/></button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</div>
</div>