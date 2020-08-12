<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 11.08.2020
  Time: 14:50
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
<title>New Publisher</title>
<body>
<form class="form-horizontal" action="<c:url value="/d/addPublisher"/>" method="post">

    <fieldset>

        <legend>New Publisher</legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addName"><fmt:message key="name"/></label>
            <div class="col-md-4">
                <input id="addName" name="Name" type="text" placeholder="Set name" class="form-control input-md"
                       required="">
                <span class="help-block">Set Name</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addEmail"><fmt:message key="email"/></label>
            <div class="col-md-4">
                <input id="addEmail" name="Email" type="text" placeholder="Set email" class="form-control input-md"
                       required="">
                <span class="help-block">Set Email</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addAddress"><fmt:message key="common.address"/></label>
            <div class="col-md-4">
                <input id="addAddress" name="Address" type="text" placeholder="Set address"
                       class="form-control input-md" required="">
                <span class="help-block">Set Address</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addCountry"><fmt:message key="country"/></label>
            <div class="col-md-4">
                <input id="addCountry" name="Country" type="text" placeholder="Set country"
                       class="form-control input-md" required="">
                <span class="help-block">Set Country</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="singlebutton"></label>
            <div class="col-md-4">
                <button id="singlebutton" name="singlebutton" class="btn btn-primary"><fmt:message
                        key="addPublisher"/></button>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>
