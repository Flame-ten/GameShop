<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<%--@elvariable id="game" type="src\main\java\epam\andrew\gameShop\entity\Game"--%>
<%--@elvariable id="gameTransaction" type="epam.andrew.gameShop.entity.GameInTransaction"--%>
<%--@elvariable id="transactions" type="java.util.List"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="transaction" type="epam.andrew.gameShop.entity.Transaction"--%>
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
                            <h3 style="color:#2C3E50">Transaction detail</h3>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${user.role.name(pageContext.response.locale)}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>


                            <div class="panel-body">
                                <table class="table table-striped table-condensed">
                                    <thead>
                                    <tr>
                                        <th class="text-center" width="115px">ID</th>
                                        <th class="text-center" width="115px"><fmt:message key="user.button"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="game"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="amount"/></th>
                                        <th class="text-center" width="115px"><fmt:message key="payment"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${transactions}" var="transaction">
                                        <fmt:formatNumber var="payment" type="currency" currencyCode="KZT"
                                                          maxFractionDigits="0" value="${game.price.amount}"/>
                                        <tr>

                                            <td class="text-center" width="150px">${gameTransaction.id}</td>
                                            <td class="text-center" width="150px">${gameTransaction.userName}</td>
                                            <td class="text-center" width="150px">${gameTransaction.game.name}</td>
                                            <td class="text-center" width="150px">${gameTransaction.amount}</td>
                                            <fmt:formatNumber var="formattedItemPrice" type="currency"
                                                              currencyCode="KZT"
                                                              maxFractionDigits="0"
                                                              value="${game.price.amount}"/>
                                            <td class="text-center" width="150px">${formattedItemPrice}</td>
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