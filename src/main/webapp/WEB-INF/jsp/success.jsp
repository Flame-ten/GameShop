<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 14.08.2020
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>

<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <div class="alert-group">
            <c:if test="${afterRegisterFlag.equals('true')}">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="success"/></strong>
                </div>
            </c:if>
            <c:if test="${afterProfileChangeFlag.equals('true')}">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="success"/></strong>
                </div>
            </c:if>
            <c:if test="${afterGameAddFlag.equals('true')}">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="success"/></strong>
                </div>
            </c:if>
            <c:if test="${afterGameEditFlag.equals('true')}">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="success"/></strong>
                </div>
            </c:if>
            <c:if test="${afterGameDeleteFlag.equals('true')}">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="success"/></strong>
                </div>
            </c:if>
            <<c:if test="${afterUserDeleteFlag.equals('true')}">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong><fmt:message key="success"/></strong>
            </div>
        </c:if>
            <c:if test="${userDeleteError.equals('true')}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="fail"/></strong>
                </div>
            </c:if>
            <c:if test="${errorBalance.equals('true')}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="fail"/></strong>
                </div>
            </c:if>
            <c:if test="${errorLogin.equals('true')}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="fail"/></strong>
                </div>
            </c:if>
            <c:if test="${notEnoughMoneyError.equals('true')}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="fail"/></strong>
                </div>
            </c:if>
            <c:if test="${successBuy.equals('true')}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong><fmt:message key="fail"/></strong>
                </div>
            </c:if>
        </div>
    </div>
</div>
