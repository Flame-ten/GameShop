<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="cash.button" var="set_cash"/>
    <fmt:message key="cash" var="user_cash"/>
    <fmt:message key="error.emptyBalance" var="empty_balance"/>
</fmt:bundle>

<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://mileyfrost.com/wp-content/uploads/2018/06/156689-download-white-gradient-background-2447x1468-1080p.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>

<%@ include file="header.jsp" %>
<html>
<title>${set_cash}</title>
<body>
<fieldset>
    <legend>${set_cash}</legend>

    <div class="form-group">
        <label class="col-md-4 control-label">${user.login}</label>
        <div class="col-md-4">
            <form role="form" action="<c:url value="/do/editBalance"/>" method="post" c>
                <input hidden name="userId" value="${user.id}">
                <div class="form-group">
                    <div class="row">


                        <div class="col-4 p-0">
                            <input type="text" class="form-control" placeholder="<fmt:message key="cash"/>"
                                   name="balance"
                                   value="${user.cash.amount.intValue()}">
                            <c:if test="${emptyBalance.equals('true')}">
                                <p class="text-danger" style="font-size: 14px; margin:1px">${empty_balance}</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="singlebutton"></label>
                    <div class="col-md-4">
                        <button id="singlebutton" name="singlebutton" class="btn btn-primary">${set_cash}</button>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 p-0">
                        <fmt:formatNumber var="formattedBalance" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0" value="${user.cash.amount}"/>
                        <p class="text-secondary" style="font-size: 17px; margin:1px">${user_cash}
                            : ${formattedBalance}</p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</fieldset>
</body>
</html>
