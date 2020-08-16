<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="homePage" var="home"/>
</fmt:bundle>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title>${home}</title>
<jsp:include page="header.jsp"/>
<div id="myCarousel" class="carousel slide" data-interval="false">

    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">

        <div class="item active">
            <img src="https://coverfiles.alphacoders.com/573/57388.jpg" style="width:100%" class="img-responsive">
            <div class="container">
                <div class="carousel-caption">
                    <h1>World of Warcraft</h1>
                    </p>
                </div>
            </div>
        </div>

        <div class="item">
            <img src="https://coverfiles.alphacoders.com/378/37850.png" class="img-responsive">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Witcher III</h1>
                </div>
            </div>
        </div>

        <div class="item">
            <img src="https://coverfiles.alphacoders.com/372/37218.jpg" class="img-responsive">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Rainbow 6</h1>
                </div>
            </div>
        </div>
    </div>

    <a class="right carousel-control" href="#myCarousel" data-slide="next">
        <span class="icon-next"></span>
    </a>
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
        <span class="icon-prev"></span>
    </a>

</div>
