<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="cart.button" var="cartButton"/>
    <fmt:message key="common.cart" var="cartLogo"/>
    <fmt:message key="amount" var="amo_unt"/>
    <fmt:message key="error.neededAmount" var="neededAmount"/>
    <fmt:message key="error.amount" var="errorAmount"/>
    <fmt:message key="delete.button" var="delete"/>
    <fmt:message key="clear.button" var="clear"/>
    <fmt:message key="total" var="total"/>
    <fmt:message key="buy.button" var="buy"/>
</fmt:bundle>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>


<%--@elvariable id="games" type="java.util.List"--%>
<%--@elvariable id="genres" type="epam.andrew.gameShop.entity.Genre"--%>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="errorMap" type="java.util.Map;"--%>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title>${cartLogo}</title>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-xs-8">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <div class="panel-title">
                        <div class="row">
                            <div class="col-xs-6">
                                <h5><span class="glyphicon glyphicon-shopping-cart"></span>${cartLogo}</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="<c:url value="/do/recountCart"/>" method="post" c>
                    <div class="panel-body">
                        <c:forEach items="${cart.gamesInTransaction}" var="game" varStatus="gameRow">
                        <div class="row">
                            <div class="col-xs-2"><img class="img-responsive" src="<c:url value="/img/${game.id}"/>">
                            </div>
                            <div class="col-xs-4">
                                <h4 class="product-name"><strong>${game.name}</strong></h4>
                            </div>
                            <div class="col-xs-6">
                                <div class="col-xs-6 text-right">
                                    <fmt:formatNumber var="price" type="currency" currencyCode="KZT"
                                                      maxFractionDigits="0"
                                                      value="${game.price.amount}"/>
                                    <h6><strong>${price}</strong></h6>
                                </div>
                                <div class="col-xs-4">
                                    <input type="text" class="form-control input-sm" min="1" max="9999"
                                           value="${game.amount}"
                                           name="item${gameRow.index}" onchange="this.form.submit()">
                                    <c:if test="${errorMap.get(gameRow.index).equals('true')}">
                                        <p class="text-danger"
                                           style="height: 20px;font-size: 12px;">${errorAmount}</p>
                                    </c:if>
                                    <c:if test="${errorMap.get(gameRow.index).equals('gameAmountError')}">
                                        <p class="text-danger"
                                           style="height: 20px;font-size: 12px;">${neededAmount}:
                                                ${amount}</p>
                                    </c:if>
                                </div>
                                <div class="col-xs-2">
                                    <a type="button" class="btn btn-link btn-xs"
                                       href="<c:url value="/do/gameInTransaction/delete?game=${gameRow.index}"/>">
                                        <span class="glyphicon glyphicon-trash">${delete}</span>
                                    </a>
                                </div>
                            </div>
                        </div>

                        <hr>
                        <hr>
                        <div class="row">
                            <div class="text-center">
                                <div class="col-xs-3">
                                    <a type="button" class="btn btn-default btn-sm btn-block"
                                       href="<c:url value="/do/clearCart"/>">
                                            ${clear}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="row text-center">
                            <div class="col-xs-9">
                                <fmt:formatNumber var="formattedCartPrice" type="currency" currencyCode="KZT"
                                                  maxFractionDigits="0"
                                                  value="${cart.price.amount}"/>
                                <h4 class="text-right">${total}<strong>${formattedCartPrice}</strong>
                                </h4>
                            </div>
                            <div class="col-xs-3">
                                <a type="button" class="btn btn-success btn-block" href="<c:url value="/do/buyCart"/>">
                                        ${buy}
                                </a>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
