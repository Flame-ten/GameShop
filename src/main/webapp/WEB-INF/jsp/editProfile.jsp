<%--
  Created by IntelliJ IDEA.
  User: Me
  Date: 19.07.2020
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="localeCode" value="${pageContext.response.locale}"/>

<fmt:bundle basename="language">
    <fmt:message key="editProfile.button" var="editProfile"/>
    <fmt:message key="common.profile" var="profile"/>
    <fmt:message key="common.files" var="files"/>
    <fmt:message key="name" var="userName"/>
    <fmt:message key="error.emptyName" var="empty_name"/>
    <fmt:message key="error.wrongName" var="wrong_name"/>
    <fmt:message key="surname" var="userSur"/>
    <fmt:message key="error.wrongSurname" var="wrong_sur"/>
    <fmt:message key="error.emptySurname" var="empty_sur"/>
    <fmt:message key="login" var="userLogin"/>
    <fmt:message key="error.emptyLogin" var="empty_login"/>
    <fmt:message key="error.wrongLogin" var="wrong_login"/>
    <fmt:message key="common.email" var="userEmail"/>
    <fmt:message key="error.emptyPhone" var="empty_phone"/>
    <fmt:message key="error.wrongPhone" var="wrong_phone"/>
    <fmt:message key="phone" var="userPhone"/>
    <fmt:message key="gender" var="userGender"/>
    <fmt:message key="user.male" var="male"/>
    <fmt:message key="user.female" var="female"/>
    <fmt:message key="error.emptyCountry" var="empty_country"/>
    <fmt:message key="error.wrongCountry" var="wrong_country"/>
    <fmt:message key="country" var="user_country"/>
    <fmt:message key="cash" var="user_cash"/>
    <fmt:message key="save.button" var="save"/>
</fmt:bundle>

<%--@elvariable id="loggedUser" type="epam.andrew.gameShop.entity.User"--%>
<%--@elvariable id="user" type="epam.andrew.gameShop.entity.User"--%>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<style>
    body {
        background: url(https://images8.alphacoders.com/922/thumb-1920-922129.png) no-repeat fixed center;
        background-size: cover;
    }
</style>
<title>${editProfile}</title>
<jsp:include page="header.jsp"/>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">${profile}</h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <form role="form" action="<c:url value="/do/editProfile"/>" method="post" c>
                <div class=" col-md-9 col-lg-9 ">
                    <table class="table table-user-information">
                        <tbody>
                        <tr>
                            <td>${userName}</td>
                            <td>
                                <label>
                                    <input type="text" class="form-control" placeholder="${userName}"
                                           value="${loggedUser.name}" name="name">
                                </label>
                                <c:if test="${emptyName.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_name}</p>
                                </c:if>
                                <c:if test="${wrongName.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_name}</p>
                                </c:if></td>
                        </tr>
                        <tr>
                            <td>${userSur}</td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="${userSur}"
                                       value="${loggedUser.surname}" name="surname">
                            </label>
                                <c:if test="${emptySurname.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_sur}></p>
                                </c:if>
                                <c:if test="${wrongSurname.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_sur}</p>
                                </c:if></td>
                        </tr>

                        <tr>
                            <td>${userLogin}</td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="${userLogin}"
                                       value="${loggedUser.login}" name="login">
                            </label>
                                <c:if test="${emptyLogin.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_login}</p>
                                </c:if>
                                <c:if test="${wrongLogin.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_login}</p>
                                </c:if></td>
                        </tr>

                        <tr>
                            <td>${userEmail}</td>
                            <td><p>${loggedUser.email}</p></td>
                        </tr>

                        <tr>
                            <td>${userPhone}</td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="${userPhone}"
                                       value="${loggedUser.phone}" name="phone">
                            </label>
                                <c:if test="${emptyPhone.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_phone}</p>
                                </c:if>
                                <c:if test="${wrongPhone.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_phone}</p>
                                </c:if></td>
                        </tr>
                        <tr>

                            <td>${userGender}</td>
                            <td><select name="DropGenre" id="DropGenre" class="input-xlarge">
                                <option value="10.0">${male}</option>
                                <option value="11.0">${female}</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td>${user_country}</td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="${user_country}"
                                       value="${loggedUser.country}" name="country">
                            </label>
                                <c:if test="${emptyCountry.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${empty_country}</p>
                                </c:if>
                                <c:if test="${wrongCountry.equals('true')}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px">${wrong_country}</p>
                                </c:if></td>
                        </tr>

                        <tr>
                            <td>${user_cash}</td>
                            <td><p>${loggedUser.cash}</p></td>
                        </tr>

                        </tbody>
                    </table>

                    <button value="submit" type="submit" class="btn btn-primary">${save}</button>
                </div>
            </form>
        </div>
    </div>
</div>


