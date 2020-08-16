<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 11.08.2020
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<%@ taglib prefix="my" tagdir="/WEB-INF/jsp" %>
<%--@elvariable id="publisherr" type="epam.andrew.gameShop.entity.Publisher"--%>
<%--@elvariable id="publishers" type="java.util.List"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="transaction" type="epam.andrew.gameShop.entity.Transaction"--%>
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
<body>
<section class="pro-box">
    <div class="form-inline p-3">
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-8"><h2><b><fmt:message key="publishers.button"/></b></h2></div>
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-info add-new"><i class="fa fa-plus"></i> Add New
                            </button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <th><fmt:message key="name"/></th>
                        <th><fmt:message key="common.email"/></th>
                        <th><fmt:message key="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${publishers}" var="publisher">
                        <tr>
                            <td>${publisher.id}</td>
                            <td>${publisher.name}</td>
                            <td>${user.email}</td>
                            <td>
                                <a class="delete" title="Delete"
                                   href="<c:url value="/do/deletePublisher?publisherId=${publisher.id}"/>"
                                > data-toggle="tooltip"><i class="material-icons"></i></a>
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