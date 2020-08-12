<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 11.08.2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title>Change password</title>
<jsp:include page="header.jsp"/>
<form class="form-horizontal" action="<c:url value="/d/edit/password"/>" method="post">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <h1>Change password</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-sm-offset-3">
                <p class="text-center"></p>
                <form method="post" id="passwordForm">
                    <input type="password" class="input-lg form-control" name="password1" id="password1"
                           placeholder="New password" autocomplete="off">
                    <div class="row">
                    </div>
                    <input type="password" class="input-lg form-control" name="password2" id="password2"
                           placeholder="Repeat password" autocomplete="off">
                    <div class="row">
                    </div>
                    <input type="submit" class="col-xs-12 btn btn-primary btn-load btn-lg"
                           data-loading-text="Changing Password..." value="Change Password">
                </form>
            </div>
        </div>
    </div>