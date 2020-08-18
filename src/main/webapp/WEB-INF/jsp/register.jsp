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

<fmt:bundle basename="language">
    <fmt:message key="common.register" var="registration"/>
    <fmt:message key="register.button" var="register"/>
    <fmt:message key="setLogin" var="setLogin"/>
    <fmt:message key="common.password" var="userPassword"/>
    <fmt:message key="error.emptyPassword" var="empty_password"/>
    <fmt:message key="error.wrongPassword" var="wrong_password"/>
    <fmt:message key="name" var="userName"/>
    <fmt:message key="error.emptyName" var="empty_name"/>
    <fmt:message key="error.wrongName" var="wrong_name"/>
    <fmt:message key="surname" var="userSur"/>
    <fmt:message key="error.wrongSurname" var="wrong_sur"/>
    <fmt:message key="error.emptySurname" var="empty_sur"/>
    <fmt:message key="login" var="userLogin"/>
    <fmt:message key="error.emptyLogin" var="empty_login"/>
    <fmt:message key="error.wrongLogin" var="wrong_login"/>
    <fmt:message key="common.email" var="userEmail"/>
    <fmt:message key="error.emptyPhone" var="empty_phone"/>
    <fmt:message key="error.wrongPhone" var="wrong_phone"/>
    <fmt:message key="error.emptyEmail" var="empty_email"/>
    <fmt:message key="error.wrongEmail" var="wrong_email"/>
    <fmt:message key="error.emailUsed" var="used_email"/>
    <fmt:message key="phone" var="userPhone"/>
    <fmt:message key="gender" var="userGender"/>
    <fmt:message key="user.male" var="male"/>
    <fmt:message key="user.female" var="female"/>
    <fmt:message key="error.emptyCountry" var="empty_country"/>
    <fmt:message key="error.wrongCountry" var="wrong_country"/>
    <fmt:message key="country" var="user_country"/>
    <fmt:message key="cash" var="user_cash"/>
    <fmt:message key="setPassword" var="setPassword"/>
    <fmt:message key="setName" var="setName"/>
    <fmt:message key="setSurname" var="setSurname"/>
    <fmt:message key="setEmail" var="setEmail"/>
    <fmt:message key="setPhone" var="setPhone"/>
    <fmt:message key="setLogin" var="setLogin"/>
    <fmt:message key="setCountry" var="setCountry"/>
</fmt:bundle>

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
<title>${registration}</title>
<body>
<form class="form-horizontal" action="<c:url value="/do/register"/>" method="post" c>

    <fieldset>

        <legend>${registration}</legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="login">${userLogin}</label>
            <div class="col-md-4">
                <input id="login" name="login" type="text" placeholder="${setLogin}"
                       class="form-control input-md" required="" value="${login}">
                <span class="help-block">${setLogin}</span>
                <c:if test="${emptyLogin.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_login}</p>
                </c:if>
                <c:if test="${wrongLogin.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_login}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="password">${userPassword}</label>
            <div class="col-md-4">
                <input id="password" name="password" type="text" placeholder="${setPassword}"
                       class="form-control input-md" required="" value="${password}">
                <span class="help-block">${setPassword}</span>
                <c:if test="${wrongPassword.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_password}</p>
                </c:if>
                <c:if test="${emptyPassword.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_password}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="name">${userName}</label>
            <div class="col-md-4">
                <input id="name" name="name" type="text" placeholder="${setName}"
                       class="form-control input-md"
                       required="" value="${name}">
                <span class="help-block">${setName}</span>
                <c:if test="${emptyName.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_name}</p>
                </c:if>
                <c:if test="${wrongName.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_name}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="surname">${userSur}</label>
            <div class="col-md-4">
                <input id="surname" name="surname" type="text" placeholder="${setSurname}"
                       class="form-control input-md" required="" value="${surname}">
                <span class="help-block">${setSurname}</span>
                <c:if test="${emptySurname.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_sur}</p>
                </c:if>
                <c:if test="${wrongSurame.equals('used')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_sur}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="email">${userEmail}</label>
            <div class="col-md-4">
                <input id="email" name="email" type="text" placeholder="${setEmail}"
                       class="form-control input-md" required="" value="${email}">
                <span class="help-block">${setEmail}</span>
                <c:if test="${wrongEmail.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_email}</p>
                </c:if>
                <c:if test="${emailUsed.equals('used')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${used_email}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="phone">${userPhone}</label>
            <div class="col-md-4">
                <input id="phone" name="phone" type="text" placeholder="${setPhone}"
                       class="form-control input-md" required="" value="${phone}">
                <span class="help-block">${setPhone}</span>
                <c:if test="${emptyPhone.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_phone}</p>
                </c:if>
                <c:if test="${wrongPhone.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_phone}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerCountry">${user_country}</label>
            <div class="col-md-4">
                <input id="registerCountry" name="country" type="text" placeholder="${setCountry}"
                       class="form-control input-md" required="" value="${country}">
                <span class="help-block">${setCountry}</span>
                <c:if test="${emptyCountry.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_country}</p>
                </c:if>
                <c:if test="${wrongCountry.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_country}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label">${userGender}</label>
            <div class="col-md-4">
                <div class="radio">
                    <label for="radios-0">
                        <input type="radio" name="radios" id="radios-0" value="1" checked="checked">
                        ${male}
                    </label>
                </div>
                <input type="radio" name="radios" id="radios-1" value="0">
                ${female}
                </label>
            </div>
        </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="register"></label>
            <div class="col-md-4">
                <button id="register" name="register" class="btn btn-primary">${register}</button>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>