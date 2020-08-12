<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<%--@elvariable id="genres" type="java.util.List"--%>
<%--@elvariable id="games" type="java.util.List"--%>
<%--@elvariable id="genre" type="src\main\java\epam\andrew\gameShop\entity\Genre"--%>
<%--@elvariable id="game" type="src\main\java\epam\andrew\gameShop\entity\Game"--%>
<%--@elvariable id="locale" type="java.util.Locale"--%>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div id="Carousel" class="carousel slide">

                <ol class="carousel-indicators">
                    <li data-target="#Carousel" data-slide-to="0" class="active"></li>
                    <li data-target="#Carousel" data-slide-to="1"></li>
                </ol>


                <div class="carousel-inner">

                    <div class="item active">
                        <div class="row">
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://cdn.battlefy.com/helix/images/games/overwatch/uploads/box-1591431329141.jpg"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://systemrequirements.ru/wp-content/cache/thumb/78/db7725624d52178_250x0.jpg"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://cdn.ananasposter.ru/image/cache/catalog/poster/games/83/10883-250x250.jpg"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://www.digiseller.ru/preview/121677/p1_929031_ba46bacf.jpg" alt="Image"
                                    style="max-width:100%;"></a></div>
                        </div>
                    </div>

                    <div class="item">
                        <div class="row">
                            <div class="col-md-3"><a href="<c:url value="/d/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://avatars.mds.yandex.net/get-mpic/1909520/img_id7469460810494494921.jpeg/6hq"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/d/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://cdn.ananasposter.ru/image/cache/catalog/poster/games/83/10715-250x250.jpg"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/d/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://vignette.wikia.nocookie.net/assassinscreed/images/3/3b/ACIII-GMi.png/revision/latest/scale-to-width-down/340?cb=20150513123851&path-prefix=ru"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/d/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="https://cdn.webrazzi.com/uploads/2010/07/sc2_logo.jpg" alt="Image"
                                    style="max-width:100%;"></a></div>
                        </div>
                    </div>

                </div>
                <a data-slide="prev" href="#Carousel" class="left carousel-control">‹</a>
                <a data-slide="next" href="#Carousel" class="right carousel-control">›</a>
            </div>
        </div>
    </div>
</div>

<div class="col-9">
    <div class="row">


        <c:forEach items="${storageItems}" var="item" varStatus="itemRow">

            <div class="col-md-4">

                <div class="card mb-3 box-shadow" style="border-color: #343A40 ; border-width: 2px">
                    <div class="row justify-content-center" style="height: 255px">
                        <a href="<c:url value="/do/product?id=${item.id}"/>"><img
                                style="max-height: 240px; max-width: 290px; margin-top: 15px"
                                src="<c:url value="/img/${item.product.id}"/>"></a>
                    </div>
                    <div class="card-body" style="padding-top: 2px">

                        <div class="row" style="height:75px">
                            <a href="<c:url value="/do/product?id=${item.id}"/>"
                               class="card-link text-dark">
                                <p class="card-text" style="margin-right: 17px; margin-left: 17px">
                                        ${item.product.name}
                                </p>
                            </a>
                        </div>


                        <div class="row" style="height: 35px">
                            <a href="<c:url value="/do/product?id=${item.id}"/>"
                               class="card-link text-dark">
                                <p class="font-weight-bold" style="font-size: 20px; margin-right: 17px;
                                        color: #F16143; margin-left: 22px">
                                    ₸ ${item.product.price.amount.intValue()}
                                </p>
                            </a>
                        </div>

                        <div class="d-flex justify-content-between align-items-center">
                            <form action="<c:url value="/do/addToCart"/>" method="post" style="margin: 0px">
                                <input type="hidden" name="product" value="${item.product.id}">
                                <input type="hidden" name="amount" value="1">
                                <div class="btn-group">
                                    <a class="btn btn-dark btn-sm"
                                       href="<c:url value="/do/product?id=${item.id}"/>"
                                       role="button">
                                            ${view}
                                    </a>

                                    <c:if test="${(loggedUser.role.name.equals('user')
                                        || loggedUser.role.name.equals('admin'))}">
                                        <c:if test="${!(errorMap.get(itemRow.index).equals('itemAmountError'))}">
                                            <button class="btn  btn-dark btn-sm" value="submit" type="submit"
                                                    style="background-color: #F16143; border-color: #F16143;
                                                    color: #212529;">
                                                    ${add_to_cart}
                                            </button>
                                        </c:if>


                                    </c:if>
                                </div>
                            </form>

                            <c:if test="${!(errorMap.get(itemRow.index).equals('itemAmountError'))}">
                                <small class="text-muted">${in_stock} ${item.amount}</small>
                            </c:if>
                            <c:if test="${errorMap.get(itemRow.index).equals('itemAmountError')}">
                                <small class="text-danger">${not_available}</small>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>


    </div>
</div>
</div>

