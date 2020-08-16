<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<%@ taglib prefix="my" tagdir="/WEB-INF/jsp" %>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="gameTransaction" type="epam.andrew.gameShop.entity.GameInTransaction"--%>
<%--@elvariable id="transactions" type="java.util.List"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="transaction" type="epam.andrew.gameShop.entity.Transaction"--%>
<%--@elvariable id="role" type="epam.andrew.gameShop.entity.Role"--%>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<c:set var="localeCode" value="${pageContext.response.locale}"/>
<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<jsp:include page="header.jsp"/>
<form class="form-signin">
    <div class="container">
        <div class="row">
            <div class="col-md-7 col-md-offset-2">

                <div class="panel panel-default">

                    <div class="panel panel-primary">

                        <div class="text-center">
                            <h3 style="color:#2C3E50"><fmt:message key="transactionDetail.button"/></h3>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${user.role.getName(pageContext.response.locale)}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>


                            <div class="panel-body">
                                <table class="table table-striped table-condensed">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th class="text-center" width="115px"><fmt:message key="id"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="user.button"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="common.game"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="amount"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="common.payment"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${transaction.gameInTransactionList}" var="transaction"
                                               varStatus="transactionRow">
                                        <tr>
                                            <td>
                                                <img style="height: 60px; margin-top: 15px"
                                                     src="<c:url value="/img/${game.id}"/>">
                                            </td>
                                            <td class="text-center" width="150px">${gameTransaction.id}</td>
                                            <td class="text-center" width="150px">${gameTransaction.userName}</td>
                                            <td class="text-center" width="150px">${gameTransaction.game.name}</td>
                                            <td class="text-center" width="150px">${gameTransaction.amount}</td>
                                            <fmt:formatNumber var="formattedGamePrice" type="currency"
                                                              currencyCode="KZT"
                                                              maxFractionDigits="0"
                                                              value="${game.price.amount}"/>
                                            <td class="text-center" width="150px">${formattedGamePrice}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="text-center">
                                    <fmt:formatNumber var="formattedCartPrice" type="currency" currencyCode="KZT"
                                                      maxFractionDigits="0"
                                                      value="${transaction.price.amount}"/>
                                    <h4><label style="color:#E74C3C"><fmt:message
                                            key="total"/></label>${formattedCartPrice}</h4></div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="row justify-content-center" style="margin-top: 30px">
    <div class="col-12 p-0">
        <my:pagination url="/do/gameManage" pagesCount="${pagesCount}"/>
    </div>
</div>