<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 11.08.2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<%--@elvariable id="loggedUser" type="epam.andrew.gameShop.entity.User"--%>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title><fmt:message key="password.button"/></title>
<jsp:include page="header.jsp"/>
<form class="form-horizontal" action="<c:url value="/do/password"/>" method="post" c>
    <input hidden name="userId" value="${loggedUser.id}">
    <input hidden name="changePasswordForm" value="true">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <h1><fmt:message key="password.button"/></h1>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-sm-offset-3">
                <p class="text-center"></p>
                <form method="post" id="passwordForm">
                    <input type="password" class="input-lg form-control" name="repeatPassword" id="password2"
                           placeholder="<fmt:message key="oldPassword"/>" autocomplete="off" value="${password}">
                    <c:if test="${wrongOldPassword.equals('true')}">
                        <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message key="oldPassword"/></p>
                    </c:if>
                    <div class="row">
                    </div>
                    <input type="password" class="input-lg form-control" name="newPassword" id="newPassword"
                           placeholder="<fmt:message key="setPassword"/>" autocomplete="off">
                    <c:if test="${wrongPassword.equals('true')}">
                        <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                key="error.wrongPassword"/></p>
                    </c:if>
                    <c:if test="${emptyPassword.equals('true')}">
                        <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                key="error.emptyPassword"/></p>
                    </c:if>
                    <div class="row">
                    </div>
                    <input type="password" class="input-lg form-control" name="repeatPassword" id="password2"
                           placeholder="<fmt:message key="repeatPassword"/>" autocomplete="off" value="${password}">
                    <c:if test="${differentPassword.equals('true')}">
                        <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                key="differentPassword"/></p>
                    </c:if>
                    <div class="row">
                    </div>
                    <input type="submit" class="col-xs-12 btn btn-primary btn-load btn-lg"
                           data-loading-text="Changing Password..." value="<fmt:message key="password.button"/>">
                </form>
            </div>
        </div>
    </div>
    </div>
</form>