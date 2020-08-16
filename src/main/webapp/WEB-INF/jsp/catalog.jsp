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
<c:set var="localeCode" value="${pageContext.response.locale}"/>

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
<html>
<title><fmt:message key="catalog.button"/></title>
<body>
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
                                    src="<c:url value="/img/${game.id}"/>"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="<c:url value="/img/${game.id}"/>"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="<c:url value="/img/${game.id}"/>"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="<c:url value="/img/${game.id}"/>" alt="Image"
                                    style="max-width:100%;"></a></div>
                        </div>
                    </div>

                    <div class="item">
                        <div class="row">
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="<c:url value="/img/${game.id}"/>"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="<c:url value="/img/${game.id}"/>"
                                    alt="Image" style="max-width:100%;"></a></div>
                            <div class="col-md-3"><a href="<c:url value="/do/game?id=${game.id}"/>"
                                                     class="thumbnail"><img
                                    src="<c:url value="/img/${game.id}"/>"
                                    alt="Image" style="max-width:100%;"></a></div>
                        </div>
                    </div>

                </div>
                <a data-slide="prev" href="#Carousel" class="left carousel-control">‹</a>
                <a data-slide="next" href="#Carousel" class="right carousel-control">›</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
