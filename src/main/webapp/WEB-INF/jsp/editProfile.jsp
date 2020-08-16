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
<title>Profile</title>
<jsp:include page="header.jsp"/>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title"><fmt:message key="common.profile"/></h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <form role="form" action="<c:url value="/do/edit/profile"/>" method="post" c>
                <div class=" col-md-9 col-lg-9 ">
                    <table class="table table-user-information">
                        <tbody>
                        <tr>
                            <td><fmt:message key="name"/></td>
                            <td>
                                <label>
                                    <input type="text" class="form-control" placeholder="<fmt:message key="name"/>"
                                           value="${loggedUser.name}" name="name">
                                </label>
                                <c:if test="${requestScope.empty_name !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.emptyName"/></p>
                                </c:if>
                                <c:if test="${requestScope.wrong_name !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.wrongName"/></p>
                                </c:if></td>
                        </tr>
                        <tr>
                            <td><fmt:message key="surname"/></td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="<fmt:message key="surname"/>"
                                       value="${loggedUser.surname}" name="surname">
                            </label>
                                <c:if test="${requestScope.empty_surname !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.emptySurname"/></p>
                                </c:if>
                                <c:if test="${requestScope.wrong_surname !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.wrongSurname"/></p>
                                </c:if></td>
                        </tr>

                        <tr>
                            <td><fmt:message key="login"/></td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="<fmt:message key="login"/>"
                                       value="${loggedUser.login}" name="login">
                            </label>
                                <c:if test="${requestScope.empty_login !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.emptyLogin"/></p>
                                </c:if>
                                <c:if test="${requestScope.wrong_login !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.wrongLogin"/></p>
                                </c:if></td>
                        </tr>

                        <tr>
                            <td><fmt:message key="common.email"/></td>
                            <td><p>${loggedUser.email}</p></td>
                        </tr>

                        <tr>
                            <td><fmt:message key="phone"/></td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="<fmt:message key="phone"/>"
                                       value="${loggedUser.phone}" name="phone">
                            </label>
                                <c:if test="${requestScope.empty_phone_number !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.emptyPhone"/></p>
                                </c:if>
                                <c:if test="${requestScope.wrong_phone_number !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.wrongPhone"/></p>
                                </c:if></td>
                        </tr>
                        <tr>

                            <td><fmt:message key="gender"/></td>
                            <td><select name="DropGenre" id="DropGenre" class="input-xlarge">
                                <option value="10.0"><fmt:message key="user.male"/></option>
                                <option value="11.0"><fmt:message key="user.female"/></option>
                            </select></td>
                        </tr>
                        <tr>
                            <td><fmt:message key="country"/></td>
                            <td><label>
                                <input type="text" class="form-control" placeholder="<fmt:message key="country"/>"
                                       value="${loggedUser.country}" name="country">
                            </label>
                                <c:if test="${requestScope.empty_country !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.emptyCountry"/></p>
                                </c:if>
                                <c:if test="${requestScope.wrong_country !=null}">
                                    <p class="text-danger" style="font-size: 14px; margin:1px"><fmt:message
                                            key="error.wrongCountry"/></p>
                                </c:if></td>
                        </tr>

                        <tr>
                            <td><fmt:message key="cash"/></td>
                            <td><p>${loggedUser.cash}</p></td>
                        </tr>

                        </tbody>
                    </table>

                    <button value="submit" type="submit" class="btn btn-primary"><fmt:message
                            key="save.button"/></button>
                </div>
            </form>
        </div>
    </div>
</div>


