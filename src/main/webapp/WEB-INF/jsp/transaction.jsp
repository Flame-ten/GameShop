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
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<%@ taglib prefix="my" tagdir="/WEB-INF/jsp" %>
<%--@elvariable id="game" type="src\main\java\epam\andrew\gameShop\entity\Game"--%>
<%--@elvariable id="transactions" type="java.util.List"--%>
<%--@elvariable id="statuses" type="java.util.List"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
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
                        <div class="col-sm-8"><h2><b><fmt:message key="transactions.button"/></b></h2></div>
                    </div>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <th><fmt:message key="user.button"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><fmt:message key="price"/></th>
                        <th><fmt:message key="common.status"/></th>
                        <th><fmt:message key="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${transactions}" var="transaction">
                        <fmt:formatNumber var="price" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0" value="${game.price.amount}"/>
                        <tr>
                            <td>${transaction.id}</td>
                            <td>${user.login}</td>
                            <td>${transaction.date}</td>
                            <td>${transaction.time}</td>
                            <fmt:formatNumber var="price" type="currency" currencyCode="KZT"
                                              maxFractionDigits="0" value="${game.price.amount}"/>
                            <td>${price}</td>
                            <td>
                                <div class="form-group input-group" style="margin-bottom: 0px">
                                    <form action="<c:url value="/do/status"/>" method="post c"
                                          style="margin-bottom: 0px">
                                        <input hidden name="orderId" value="${transaction.id}">
                                        <select class="form-control custom-select" id="status" name="statusId"
                                                onchange="this.form.submit()" style="width: 150px">
                                            <c:forEach items="${statuses}" var="status">
                                                <option value="${status.id}"<c:if
                                                        test="${transaction.status.equals(status)}"> selected
                                                </c:if>>${status.getInfo(pageContext.response.locale)}</option>
                                            </c:forEach>
                                        </select>
                                    </form>
                                </div>
                            </td>
                            <td>
                                <a class="add" title="Detail"
                                   href="<c:url value="/do/transaction?transactionId=${transaction.id}"/>"
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
<div class="row justify-content-center" style="margin-top: 30px">
    <div class="col-12 p-0">
        <my:pagination url="/do/gameManage" pagesCount="${pagesCount}"/>
    </div>
</div>