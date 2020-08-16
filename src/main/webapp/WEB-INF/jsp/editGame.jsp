<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 17.07.2020
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<%--@elvariable id="role" type="epam.andrew.gameShop.entity.Role"--%>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="genres" type="java.util.List"--%>
<%--@elvariable id="games" type="java.util.List"--%>
<%--@elvariable id="publishers" type="java.util.List"--%>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>

<head>
    <title><fmt:message key="editGame"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<hr>
<div class="container bootstrap snippet">
    <div class="row">
        <div class="col-sm-10"><h1><fmt:message key="editGame"/></h1></div>
    </div>
    <div class="row">
        <div class="col-sm-3">

            <div class="text-center">
                <input type="file" class="text-center center-block file-upload" id="image" name="image">
            </div>
            <c:if test="${image.equals('true')}">
                <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message key="error.image"/>}</p>
            </c:if>
            </hr><br>

            <div class="col-9 p-0">
                <img src="<c:url value="/img/${game.id}"/>" style="height: 120px">
            </div>

        </div>
        <div class="col-sm-9">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#"><fmt:message key="common.files"/></a></li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="home">
                    <hr>
                    <form class="form" action="<c:url value="/do/editGame"/>" id="editGame" method="post" c>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="gameName"><h4><fmt:message key="name"/></h4></label>
                                <input type="text" class="form-control" name="gameName" id="gameName"
                                       placeholder="Game name" value="${game.name}">
                                <c:if test="${emptyName.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.emptyName"/></p>
                                </c:if>
                                <c:if test="${wrongName.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.wrongName"/></p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="genre"><h4><fmt:message key="common.genre"/></h4></label>
                                <select name="genre" id="genre" class="input-xlarge">
                                    <c:forEach items="${genres}" var="genre">
                                        <option value="${genre.id}"
                                                <c:if test="${game.genre.equals(genre)}">selected</c:if>
                                        >${genre.getInfo(pageContext.response.locale)}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="publisher"><h4><fmt:message key="publisher"/></h4></label>
                                <select name="publisher" id="publisher" class="input-xlarge">
                                    <c:forEach items="${publishers}" var="publisher">
                                        <option value="${publisher.id}"
                                                <c:if test="${publisher.equals(publisher)}">selected</c:if>
                                        >${publisher.getName(pageContext.response.locale)}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="amount"><h4><fmt:message key="amount"/></h4></label>
                                <input type="text" class="form-control" name="amount" id="amount"
                                       placeholder="<c:url value="${game.amount}"/>"
                                       title="input amount">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="price"><h4><fmt:message key="price"/></h4></label>
                                <input type="text" class="form-control" name="price" id="price"
                                       value="${game.price.amount.intValue()}">
                                <c:if test="${moneyError.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.money"/></p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-4">
                                <label class="col-md-4 control-label" for="description"><h4><fmt:message
                                        key="common.description"/></h4></label>
                                <textarea class="form-control" id="description" name="description"
                                          style=" resize: none; width: 250px; height:200px;"
                                          placeholder="description" title="input description.">
                                    <c:if test="${locale.equals('en')}">${game.enDescription}</c:if>
                                     <c:if test="${locale.equals('ru')}">${game.ruDescription}</c:if>
                                </textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="lang"><h4><fmt:message key="descriptionLang"/></h4></label>
                                <select name="lang" id="lang" class="input-xlarge">
                                    <c:if test="${pageContext.response.locale.toString().equals('en_EN')}">
                                        <option selected name="descriptionLanguage" value="en">English</option>
                                    </c:if>
                                    <option value="en">English</option>
                                    <c:if test="${pageContext.response.locale.toString().equals('ru_RU')}">
                                        <option selected name="descriptionLanguage" value="ru">Русский</option>
                                    </c:if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-12">
                                <br>
                                <button class="btn btn-lg btn-success" type="submit"><i
                                        class="glyphicon glyphicon-ok-sign"></i><fmt:message key="save.button"/>
                                </button>
                                <button class="btn btn-lg"><a href="<c:url value="/do/home"/>"><i
                                        class="glyphicon glyphicon-repeat"></i><fmt:message
                                        key="home.button"/></a></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>