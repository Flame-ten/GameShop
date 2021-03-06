<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="newGame" var="newGame"/>
    <fmt:message key="error.image" var="errorImage"/>
    <fmt:message key="common.files" var="files"/>
    <fmt:message key="name" var="gameName"/>
    <fmt:message key="error.emptyName" var="emptyName"/>
    <fmt:message key="error.wrongName" var="wrongName"/>
    <fmt:message key="common.genre" var="gen_re"/>
    <fmt:message key="publisher" var="publi_sher"/>
    <fmt:message key="amount" var="amo_unt"/>
    <fmt:message key="price" var="pri_ce"/>
    <fmt:message key="error.money" var="errorMoney"/>
    <fmt:message key="common.description" var="gameDescription"/>
    <fmt:message key="descriptionLang" var="descriptionLang"/>
    <fmt:message key="setDescrLang" var="setLang"/>
    <fmt:message key="save.button" var="save"/>
    <fmt:message key="home.button" var="home"/>
</fmt:bundle>
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
    <title>${newGame}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<hr>
<div class="container bootstrap snippet">
    <div class="row">
        <div class="col-sm-10"><h1>${newGame}</h1></div>
    </div>
    <div class="row">
        <div class="col-sm-3">

            <div class="text-center">
                <input type="file" class="text-center center-block file-upload" id="image" name="image">
            </div>
            <c:if test="${imageError.equals('true')}">
                <p class="text-danger" style="font-size: 14px; margin:1px">${errorImage}</p>
            </c:if>
            </hr><br>
        </div>

        <div class="col-sm-9">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#">${files}</a></li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="home">
                    <hr>
                    <form class="form" action="<c:url value="/do/addGame"/>" id="addGame" method="post" c>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="gameName"><h4>${gameName}</h4></label>
                                <input type="text" class="form-control" name="gameName" id="gameName"
                                       placeholder="Game name" value="${name}">
                                <c:if test="${emptyName.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${emptyName}</p>
                                </c:if>
                                <c:if test="${wrongName.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrongName}</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="genre"><h4>${gen_re}</h4></label>
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
                                <label for="publisher"><h4>${publi_sher}</h4></label>
                                <select name="publisher" id="publisher" class="input-xlarge">
                                    <c:forEach items="${publishers}" var="publisher">
                                        <option value="${publisher.id}"
                                                <c:if test="${publisher.equals(publisher)}">selected</c:if>
                                        >${publisher.getName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="amount"><h4>${amo_unt}</h4></label>
                                <input type="text" class="form-control" name="amount" id="amount" placeholder="0"
                                       title="input amount">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="price"><h4>${pri_ce}</h4></label>
                                <input type="text" class="form-control" name="price" id="price" value="0.00">
                                <c:if test="${money.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${errorMoney}}</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-4">
                                <label class="col-md-4 control-label" for="description"><h4>${gameDescription}</h4>
                                </label>
                                <textarea class="form-control" id="description" name="description"
                                          style=" resize: none; width: 250px; height:200px;"
                                          placeholder="description" title="input description.">
                                </textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="lang"><h4>${descriptionLang}</h4></label>
                                <select name="lang" id="lang" class="input-xlarge">
                                    <option selected name="descriptionLanguage"
                                            value="${descriptionLang}">
                                        ${setLang}
                                    </option>
                                    <option value="en">English</option>
                                    <option value="ru">Русский</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-12">
                                <br>
                                <button class="btn btn-lg btn-success" type="submit"><i
                                        class="glyphicon glyphicon-ok-sign"></i>${save}
                                </button>

                            </div>
                        </div>
                    </form>
                    <a class="btn btn-lg" href="<c:url value="/do/home"/>"><i
                            class="glyphicon glyphicon-repeat"></i>${home}</a>
                </div>
            </div>
        </div>
    </div>
</div>