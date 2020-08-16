<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="url" required="true" %>
<%@attribute name="pagesCount" required="true" %>
<%@attribute name="attributeName" required="false" %>
<%@attribute name="attributeValue" required="false" %>


<%--@elvariable id="page" type="java.lang.Integer"--%>
<%--@elvariable id="pageSize" type="java.lang.Integer"--%>
<c:set var="leftmostPage" value="${page - 2}"/>
<c:set var="rightmostPage" value="${page + 2}"/>
<c:if test="${leftmostPage <= 0}">
    <c:set var="leftmostPage" value="1"/>
</c:if>
<c:if test="${rightmostPage > pagesCount}">
    <c:set var="rightmostPage" value="${pagesCount}"/>
</c:if>
<div class="row justify-content-center ">

    <div class="col-7">
        <div class="row justify-content-end ">
            <div class="col-4">
                <c:if test="${pagesCount>1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">

                            <c:if test="${page!=1}">
                                <li class="page-item">
                                    <a class="page-link" aria-label="Previous" href=
                                    <c:url value="${url}">
                                        <c:param name="page" value="${page-1}"/>
                                        <c:param name="pageSize" value="${pageSize}"/>
                                    </c:url>>
                                        <span aria-hidden="true">&laquo;</span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                </li>
                            </c:if>

                            <c:forEach begin="${leftmostPage}" end="${rightmostPage}" varStatus="loop">

                                <li class="<c:if test="${!(loop.current == page)}">page-item</c:if>
                           <c:if test="${(loop.current == page)}">page-item active</c:if>">
                                    <a class="page-link"
                                       href=
                                       <c:url value="${url}">
                                           <c:param name="page" value="${loop.current}"/>
                                           <c:param name="pageSize" value="${pageSize}"/>
                                       </c:url>
                                    >${loop.current}
                                        <c:if test="${loop.current == page}">
                                            <span class="sr-only">(current)</span>
                                        </c:if>
                                    </a>
                                </li>

                            </c:forEach>

                            <c:if test="${page!=pagesCount}">
                                <li class="page-item">
                                    <a class="page-link" aria-label="Next" href=
                                    <c:url value="${url}">
                                        <c:param name="page" value="${page+1}"/>
                                        <c:param name="pageSize" value="${pageSize}"/>
                                    </c:url>
                                    >
                                        <span aria-hidden="true">&raquo;</span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>

    <div class="col">
        <div class="row justify-content-center">

            <div class="col-2" style="padding-left: 0px">
                <form name="form" method="get">
                    <select class="custom-select" style="width: 70px; margin-left: 1px" name="pageSize"
                            onchange="this.form.submit()" title="${size}">
                        <option value="3" <c:if test="${pageSize==3}"> selected </c:if>>3</option>
                        <option value="6" <c:if test="${pageSize==6}"> selected </c:if>>6</option>
                        <option value="9" <c:if test="${pageSize==9}"> selected </c:if>>9</option>
                        <option value="18" <c:if test="${pageSize==18}"> selected </c:if>>18</option>
                        <option value="36" <c:if test="${pageSize==36}"> selected </c:if>>36</option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</div>


