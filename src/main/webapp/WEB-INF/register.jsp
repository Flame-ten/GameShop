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
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>


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
<title>Registration</title>
<body>
<form class="form-horizontal" action="<c:url value="/do/register"/>" method="post">

    <fieldset>

        <legend>User Registration</legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerLogin"><fmt:message key="login"/></label>
            <div class="col-md-4">
                <input id="registerLogin" name="Login" type="text" placeholder="your login"
                       class="form-control input-md" required="">
                <span class="help-block">Set Login</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerPassword"><fmt:message key="common.password"/></label>
            <div class="col-md-4">
                <input id="registerPassword" name="Password" type="text" placeholder="your password"
                       class="form-control input-md" required="">
                <span class="help-block">Set Password</span>
                <c:if test="${requestScope.wrong_password !=null}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                            key="error.wrongPassword"/></p>
                </c:if>
                <c:if test="${requestScope.empty_password!=null}">
                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                            key="error.emptyPassword"/></p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerName"><fmt:message key="name"/></label>
            <div class="col-md-4">
                <input id="registerName" name="Name" type="text" placeholder="your name" class="form-control input-md"
                       required="">
                <span class="help-block">Set Name</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerSurname"><fmt:message key="surname"/></label>
            <div class="col-md-4">
                <input id="registerSurname" name="Surname" type="text" placeholder="your surname"
                       class="form-control input-md" required="">
                <span class="help-block">Set Surname</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerEmail"><fmt:message key="email"/></label>
            <div class="col-md-4">
                <input id="registerEmail" name="Email" type="text" placeholder="your email"
                       class="form-control input-md" required="">
                <span class="help-block">Set Email</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerPhone"><fmt:message key="phone"/></label>
            <div class="col-md-4">
                <input id="registerPhone" name="Phone" type="text" placeholder="your number"
                       class="form-control input-md" required="">
                <span class="help-block">Set Phone</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="registerCountry"><fmt:message key="country"/></label>
            <div class="col-md-4">
                <input id="registerCountry" name="Country" type="text" placeholder="your country"
                       class="form-control input-md" required="">
                <span class="help-block">Set Country</span>
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
                <div class="radio">
                    <label for="radios-1">
                        <input type="radio" name="radios" id="radios-1" value="0">
                        Female
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="singlebutton"></label>
            <div class="col-md-4">
                <button id="singlebutton" name="singlebutton" class="btn btn-primary"><fmt:message
                        key="register.button"/></button>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>