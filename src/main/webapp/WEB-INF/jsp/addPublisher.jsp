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
<title><fmt:message key="addPublisher"/></title>
<body>
<form class="form-horizontal" action="<c:url value="/do/addPublisher"/>" method="post" c>

    <fieldset>

        <legend><fmt:message key="publisher"/></legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addName"><fmt:message key="name"/></label>
            <div class="col-md-4">
                <input id="addName" name="name" type="text" placeholder="<fmt:message key="setName"/>"
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
            <label class="col-md-4 control-label" for="addEmail"><fmt:message key="common.email"/></label>
            <div class="col-md-4">
                <input id="addEmail" name="email" type="text" placeholder="<fmt:message key="setEmail"/>"
                       class="form-control input-md"
                       required="" value="${email}">
                <span class="help-block"><fmt:message key="setEmail"/></span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addAddress"><fmt:message key="common.address"/></label>
            <div class="col-md-4">
                <input id="addAddress" name="Address" type="text" placeholder="<fmt:message key="setAddress"/>"
                       class="form-control input-md" required="" value="${address}">
                <span class="help-block">Set Address</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addCountry"><fmt:message key="country"/></label>
            <div class="col-md-4">
                <input id="addCountry" name="Country" type="text" placeholder="<fmt:message key="setCountry"/>"
                       class="form-control input-md" required="" value="${country}">
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
