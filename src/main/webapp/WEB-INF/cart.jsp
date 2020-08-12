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
<fmt:setLocale value="${sessionScope.language}"/>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<%--@elvariable id="items" type="java.util.List"--%>
<%--@elvariable id="genres" type="src.main.java.com.epam.rodion.musicstore.entity.Genre"--%>
<%--@elvariable id="game" type="src.main.java.com.epam.rodion.musicstore.entity.Game"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="errorMap" type="java.util.Map;"--%>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<head>
    <jsp:include page="header.jsp"/>
</head>
<div class="container">
    <div class="row">
        <div class="col-xs-8">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <div class="panel-title">
                        <div class="row">
                            <div class="col-xs-6">
                                <h5><span class="glyphicon glyphicon-shopping-cart"></span> Shopping Cart</h5>
                            </div>
                            <div class="col-xs-6">
                                <button type="button" class="btn btn-primary btn-sm btn-block">
                                    <span class="glyphicon glyphicon-share-alt"></span> Continue shopping
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xs-2"><img class="img-responsive" src="http://placehold.it/100x70">
                        </div>
                        <div class="col-xs-4">
                            <h4 class="product-name"><strong>Product name</strong></h4><h4><small>Product
                            description</small></h4>
                        </div>
                        <div class="col-xs-6">
                            <div class="col-xs-6 text-right">
                                <h6><strong>25.00 <span class="text-muted">x</span></strong></h6>
                            </div>
                            <div class="col-xs-4">
                                <input type="text" class="form-control input-sm" value="1">
                            </div>
                            <div class="col-xs-2">
                                <button type="button" class="btn btn-link btn-xs">
                                    <span class="glyphicon glyphicon-trash"> </span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-xs-2"><img class="img-responsive" src="http://placehold.it/100x70">
                        </div>
                        <div class="col-xs-4">
                            <h4 class="product-name"><strong>Product name</strong></h4><h4><small>Product
                            description</small></h4>
                        </div>
                        <div class="col-xs-6">
                            <div class="col-xs-6 text-right">
                                <h6><strong>25.00 <span class="text-muted">x</span></strong></h6>
                            </div>
                            <div class="col-xs-4">
                                <input type="text" class="form-control input-sm" value="1">
                            </div>
                            <div class="col-xs-2">
                                <button type="button" class="btn btn-link btn-xs">
                                    <span class="glyphicon glyphicon-trash"> </span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="text-center">
                            <div class="col-xs-9">
                                <h6 class="text-right">Added items?</h6>
                            </div>
                            <div class="col-xs-3">
                                <button type="button" class="btn btn-default btn-sm btn-block">
                                    Update cart
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row text-center">
                        <div class="col-xs-9">
                            <h4 class="text-right">Total <strong>$50.00</strong></h4>
                        </div>
                        <div class="col-xs-3">
                            <button type="button" class="btn btn-success btn-block">
                                Checkout
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row justify-content-center">
<div class="col-8">
<c:choose>
    <c:when test="${empty cart.orderingItems}">
        <div class="row text-center justify-content-center" style=" margin-top: 30px; ">
            <div class="col-12">
                <img src="<c:url value="/images/forPageBody/cart.png"/>" height="60px" style="margin-top: 10px">
                <p class="text-secondary " style="font-size: 27px; padding: 1px; ">${empty_cart}</p>
            </div>
            <div class="col-12" style="height: 230px;">

            </div>
        </div>
    </c:when>
    <c:otherwise>
        <table class="table table-hover">

            <thead class="thead-dark">
            <tr>
                <th></th>
                <th>${name}</th>
                <th>${quantity}</th>
                <th>${price}</th>
                <th>${total}</th>
                <th></th>
            </tr>
            </thead>

            <form action="<c:url value="/do/cart/recount"/>" method="post">

                <tbody>
                <c:forEach items="${cart.orderingItems}" var="item" varStatus="itemRow">


                    <tr>
                        <td>
                            <img style="max-height: 60px; max-width: 60px; margin-top: 15px"
                                 src="<c:url value="/img/${item.product.id}"/>">
                        </td>
                        <td>${item.product.name} </td>
                        <td>
                            <div class="input-group">
                                <input class="form-control" type="number" min="1" max="9999" value="${item.amount}"
                                       style="width: 100px"
                                       name="item${itemRow.index}" onchange="this.form.submit()">
                                <c:if test="${errorMap.get(itemRow.index).equals('true')}">
                                    <p class="text-danger"
                                       style="height: 20px;font-size: 12px;">${incorrect_amount}</p>
                                </c:if>
                                <c:if test="${errorMap.get(itemRow.index).equals('itemAmountError')}">
                                    <p class="text-danger"
                                       style="height: 20px;font-size: 12px;">${needed_amount}: ${item.amount}</p>
                                </c:if>
                            </div>
                        </td>

                        <fmt:formatNumber var="formattedProductPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${item.product.price.amount}"/>
                        <td>${formattedProductPrice}</td>
                        <fmt:formatNumber var="formattedItemPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${item.price.amount}"/>
                        <td>${formattedItemPrice}</td>
                        <td>
                            <a class="btn btn-default"
                               href="<c:url value="/do/orderingItem/delete?item=${itemRow.index}"/>"
                            >${delete}
                            </a>
                        </td>


                    </tr>
                </c:forEach>
                </tbody>
            </form>
        </table>
        </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-8 p-0">
                <div class="row justify-content-end">
                    <div class="col-5 p-0">
                        <fmt:formatNumber var="formattedCartPrice" type="currency" currencyCode="KZT"
                                          maxFractionDigits="0"
                                          value="${cart.price.amount}"/>
                        <p class="text-dark font-weight-bold" style="font-size: 20px;">
                                ${total_price} ${formattedCartPrice}
                        </p>
                    </div>
                </div>
            </div>
        </div>


        <div class="row justify-content-center">
        <div class="col-8">
            <div class="row justify-content-end">
                <div class="col-3">
                    <div class="btn-group">
                        <a class="btn btn-dark btn-sm" href="<c:url value="/do/cart/clear"/>" role="button">
                                ${clear}
                        </a>
                        <a class="btn  btn-dark btn-sm" href="<c:url value="/do/cart/buy"/>" role="button"
                           style="background-color: #F16143; border-color: #F16143; color: #212529;">
                                ${buy}
                        </a>
                    </div>
                </div>
            </div>


        </div>

    </c:otherwise>
</c:choose>
</div>