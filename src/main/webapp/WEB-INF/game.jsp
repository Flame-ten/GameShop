<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<jsp:useBean id="Const" class="epam.andrew.gameShop.entity.Role"/>
<%--@elvariable id="types" type="java.util.List"--%>
<%--@elvariable id="products" type="java.util.List"--%>
<%--@elvariable id="role" type="epam.andrew.gameShop.entity.Role"--%>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<%--@elvariable id="items" type="java.util.List"--%>
<%--@elvariable id="loggedUser" type="epam.andrew.gameShop.entity.User"--%>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://mileyfrost.com/wp-content/uploads/2018/06/156689-download-white-gradient-background-2447x1468-1080p.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>
<div class="container-fluid">
    <div class="content-wrapper">
        <div class="item-container">
            <div class="container">
                <div class="col-md-12">
                    <div class="product col-md-3 service-image-left">

                        <img id="item-display" src="<c:url value="/img/${game.id}"/>" alt=""/>
                    </div>
                </div>

                <div class="col-md-7">
                    <div class="product-title">${game.name}</div>
                    <div class="product-desc">${game.name}</div>
                    <div class="product-rating"><i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i
                            class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star-o"></i>
                    </div>
                    <hr>
                    <div class="product-price">₸ ${game.price.amount.intValue()}</div>
                    <div class="product-stock">${game.amount}</div>
                    <hr>
                    <div class="btn-group cart">

                        <c:if test="${(loggedUser.role.name.equals('ADMIN'))
                                        || loggedUser.role.name.equals('ADMIN')}">
                            <c:if test="${!(requestScope.gameAmountError.equals('true'))}">
                                <form action="<c:url value="/d/addToCart"/>" method="post" style="margin: 0">
                                    <button type="button" class="btn btn-success">
                                        <fmt:message key="addToCart"/>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${gameAmountError.equals('true')}">
                                <p class="text-danger"><fmt:message key="error.amount"/></p>
                            </c:if>
                        </c:if>
                        <c:if test="${!((loggedUser.role.name.equals('USER')
                                        || loggedUser.role.name.equals('ADMIN')))}">
                            <p class="text-secondary" style=" margin-top: 10px"><fmt:message
                                    key="error.authorization"/></p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="col-md-12 product-info">
                <ul id="myTab" class="nav nav-tabs nav_tabs">

                    <li class="active"><a href="#service-one" data-toggle="tab"><fmt:message
                            key="common.description"/></a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="service-one">

                        x
                        <section class="container product-info">
                            <h3>${game.name}</h3>
                            <li>${game.description}</li>
                        </section>
                    </div>
                    <div>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </div>
</div>
</body>