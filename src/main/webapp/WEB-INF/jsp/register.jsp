<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>

<%@ include file="header.jsp" %>
<html>
<title><fmt:message key="common.register"/></title>
<body>
<form class="form-horizontal" action="<c:url value="/do/register"/>" method="post" c>

    <fieldset>

        <legend><fmt:message key="common.register"/></legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="login"><fmt:message key="login"/></label>
            <div class="col-md-4">
                <input id="login" name="login" type="text" placeholder="<fmt:message key="setLogin"/>"
                       class="form-control input-md" required="" value="${login}">
                <span class="help-block"><fmt:message key="setLogin"/></span>
                <c:if test="${emptyLogin.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message key="error.emptyLogin"/></p>
                </c:if>
                <c:if test="${wrongLogin.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message key="error.wrongLogin"/></p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="password"><fmt:message key="common.password"/></label>
            <div class="col-md-4">
                <input id="password" name="password" type="text" placeholder="<fmt:message key="setPassword"/>"
                       class="form-control input-md" required="" value="${password}">
                <span class="help-block"><fmt:message key="setPassword"/></span>
                <c:if test="${wrongPassword.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                            key="error.wrongPassword"/></p>
                </c:if>
                <c:if test="${emptyPassword.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                            key="error.emptyPassword"/></p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="name"><fmt:message key="name"/></label>
            <div class="col-md-4">
                <input id="name" name="name" type="text" placeholder="<fmt:message key="setName"/>"
                       class="form-control input-md"
                       required="" value="${name}">
                <span class="help-block"><fmt:message key="setName"/></span>
                <c:if test="${emptyName.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message key="error.emptyName"/></p>
                </c:if>
                <c:if test="${wrongName.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message key="error.wrongName"/></p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="surname"><fmt:message key="surname"/></label>
            <div class="col-md-4">
                <input id="surname" name="surname" type="text" placeholder="<fmt:message key="setSurname"/>"
                       class="form-control input-md" required="" value="${surname}">
                <span class="help-block"><fmt:message key="setSurname"/></span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="email"><fmt:message key="common.email"/></label>
            <div class="col-md-4">
                <input id="email" name="email" type="text" placeholder="<fmt:message key="setEmail"/>"
                       class="form-control input-md" required="" value="${email}">
                <span class="help-block"><fmt:message key="setEmail"/></span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="phone"><fmt:message key="phone"/></label>
            <div class="col-md-4">
                <input id="phone" name="phone" type="text" placeholder="<fmt:message key="setPhone"/>"
                       class="form-control input-md" required="" value="${phone}">
                <span class="help-block"><fmt:message key="setPhone"/></span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerCountry"><fmt:message key="country"/></label>
            <div class="col-md-4">
                <input id="registerCountry" name="country" type="text" placeholder="<fmt:message key="setCountry"/>"
                       class="form-control input-md" required="" value="${country}">
                <span class="help-block"><fmt:message key="setCountry"/></span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label"><fmt:message key="gender"/></label>
            <div class="col-md-4">
                <div class="radio">
                    <label for="radios-0">
                        <input type="radio" name="radios" id="radios-0" value="1" checked="checked">
                        Male
                    </label>
                </div>
                <input type="radio" name="radios" id="radios-1" value="0">
                Female
                </label>
            </div>
        </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="register"></label>
            <div class="col-md-4">
                <button id="register" name="register" class="btn btn-primary"><fmt:message
                        key="register.button"/></button>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>