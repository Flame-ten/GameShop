<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<%--@elvariable id="game" type="src\main\java\epam\andrew\gameShop\entity\Game"--%>
<%--@elvariable id="gameTransaction" type="epam.andrew.gameShop.entity.GameInTransaction"--%>
<%--@elvariable id="transactions" type="java.util.List"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="transaction" type="epam.andrew.gameShop.entity.Transaction"--%>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
    body {
        background: url(https://mileyfrost.com/wp-content/uploads/2018/06/156689-download-white-gradient-background-2447x1468-1080p.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>
<jsp:include page="header.jsp"/>
<section class="pro-box">
    <div class="form-inline p-3">
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-8"><h2><b>Transactions</b></h2></div>
                    </div>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th><fmt:message key="user.button"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><fmt:message key="price"/></th>
                        <th><fmt:message key="status"/></th>
                        <th><fmt:message key="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${transactions}" var="transactin">
                        <fmt:formatNumber var="price" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0" value="${game.price.amount}"/>
                        <tr>
                            <td>${transaction.id}</td>
                            <td>${user.id}</td>
                            <td>${transaction.date}</td>
                            <td>${transaction.time}</td>
                            <fmt:formatNumber var="price" type="currency" currencyCode="KZT"
                                              maxFractionDigits="0" value="${game.price.amount}"/>
                            <td>${price}</td>
                            <td>${transaction.status.info(pageContext.response.locale)}</td>
                            <td>
                                <a class="add" title="Detail"
                                   href="<c:url value="/d/transaction?transactionId=${transaction.id}"/>"
                                   data-toggle="tooltip"><i class="material-icons"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>
