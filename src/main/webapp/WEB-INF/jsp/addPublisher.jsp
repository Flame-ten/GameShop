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

<fmt:bundle basename="language">
    <fmt:message key="addPublisher" var="addPublisher"/>
    <fmt:message key="setName" var="setName"/>
    <fmt:message key="common.files" var="files"/>
    <fmt:message key="name" var="publisherName"/>
    <fmt:message key="publisher" var="publi_sher"/>
    <fmt:message key="error.emptyName" var="emptyName"/>
    <fmt:message key="error.wrongName" var="wrongName"/>
    <fmt:message key="common.email" var="publisherEmail"/>
    <fmt:message key="setEmail" var="setEmail"/>
    <fmt:message key="common.address" var="publisherAddress"/>
    <fmt:message key="setAddress" var="setAddress"/>
    <fmt:message key="country" var="publisherCountry"/>
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

<jsp:include page="header.jsp"/>
<html>
<title>${addPublisher}</title>
<body>
<form class="form-horizontal" action="<c:url value="/do/addPublisher"/>" method="post" c>

    <fieldset>

        <legend>${publi_sher}</legend>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addName">${publisherName}</label>
            <div class="col-md-4">
                <input id="addName" name="name" type="text" placeholder="${setName}"
                       class="form-control input-md"
                       required="" value="${name}">
                <span class="help-block">${setName}</span>
                <c:if test="${emptyName.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${emptyName}</p>
                </c:if>
                <c:if test="${wrongName.equals('true')}">
                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrongName}</p>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addEmail">${publisherEmail}</label>
            <div class="col-md-4">
                <input id="addEmail" name="email" type="text" placeholder="${setEmail}"
                       class="form-control input-md"
                       required="" value="${email}">
                <span class="help-block">${setEmail}</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addAddress">${publisherAddress}</label>
            <div class="col-md-4">
                <input id="addAddress" name="Address" type="text" placeholder="${setAddress}"
                       class="form-control input-md" required="" value="${address}">
                <span class="help-block">${setAddress}</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="addCountry">${publisherCountry}</label>
            <div class="col-md-4">
                <input id="addCountry" name="Country" type="text" placeholder="${setCountry}"
                       class="form-control input-md" required="" value="${country}">
                <span class="help-block">${setCountry}</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="singlebutton"></label>
            <div class="col-md-4">
                <button id="singlebutton" name="singlebutton" class="btn btn-primary">${addPublisher}</button>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>
