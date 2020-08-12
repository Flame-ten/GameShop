<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<%--@elvariable id="role" type="epam.andrew.gameShop.entity.Role"--%>
<%--@elvariable id="game" type="epam.andrew.gameShop.entity.Game"--%>
<%--@elvariable id="genres" type="java.util.List"--%>
<%--@elvariable id="games" type="java.util.List"--%>
<%--@elvariable id="genres" type="epam.andrew.gameShop.entity.Genre"--%>
<%--@elvariable id="genre" type="epam.andrew.gameShop.entity.Genre"--%>

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
    <title>New Game</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<hr>
<div class="container bootstrap snippet">
    <div class="row">
        <div class="col-sm-10"><h1>New Game</h1></div>
    </div>
    <div class="row">
        <div class="col-sm-3">

            <div class="text-center">
                <input type="file" class="text-center center-block file-upload">
            </div>
            </hr><br>

        </div>
        <div class="col-sm-9">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#home"><fmt:message key="common.files"/></a></li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="home">
                    <hr>
                    <form class="form" action="<c:url value="/d/manage/addGame"/>" method="post" id="registrationForm">
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="first_name"><h4><fmt:message key="name"/></h4></label>
                                <input type="text" class="form-control" name="first_name" id="first_name"
                                       placeholder="Game name" title="enter game name">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="first_name"><h4><fmt:message key="genre"/></h4></label>
                                <select name="DropGenre" id="DropGenre" class="input-xlarge">
                                    <option value="10.0">MMORPG</option>
                                    <option value="11.0">RPG</option>
                                    <option value="12.0">Shooter</option>
                                    <option value="12.0">Action</option>
                                    <option value="12.0">Strategy</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="first_name"><h4><fmt:message key="publisher"/></h4></label>
                                <select name="DropPublisher" id="DropPublisher" class="input-xlarge">
                                    <option value="10.0">CDPR</option>
                                    <option value="11.0">Blizzard</option>
                                    <option value="12.0">Ubisoft</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="phone"><h4><fmt:message key="amount"/></h4></label>
                                <input type="text" class="form-control" name="phone" id="phone" placeholder="0"
                                       title="input amount">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="mobile"><h4><fmt:message key="price"/></h4></label>
                                <input type="text" class="form-control" name="mobile" id="mobile" placeholder="0.00"
                                       title="input price">
                                <c:if test="${moneyError.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${moneyErrorMessage}</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-4">
                                <label class="col-md-4 control-label" for="textarea"><h4><fmt:message
                                        key="common.description"/></h4></label>
                                <textarea class="form-control" id="textarea" name="textarea"
                                          style=" resize: none; width: 250px; height:200px;"
                                          placeholder="Description EN" title="input description."></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-4">
                                <label class="col-md-4 control-label" for="textarea"><h4><fmt:message
                                        key="common.description"/></h4></label>
                                <textarea class="form-control" id="textareaRu" name="textarea"
                                          style=" resize: none; width: 250px; height:200px;"
                                          placeholder="Description RU" title="input description."></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12">
                                <br>
                                <button class="btn btn-lg btn-success" type="submit"><i
                                        class="glyphicon glyphicon-ok-sign"></i><fmt:message key="save.button"/>
                                </button>
                                <button class="btn btn-lg"><a href="home.jsp"><i class="glyphicon glyphicon-repeat"></i><fmt:message
                                        key="home.button"/></a></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

