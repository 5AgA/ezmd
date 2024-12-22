<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/sideMenu.css'/>">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div class="sidebar">
    <c:set var="currentMenu" value="${menuName}" />
    <%
        String currentMenu = request.getParameter("currentMenu");
    %>
    <ul>
        <li class="<%= "interviewResrv".equals(currentMenu) ? "active" : "" %>">
            <a href="<c:url value='/interview'/>">면담 신청</a>
        </li>
        <li class="<%= "interviewResult".equals(currentMenu) ? "active" : "" %>">
            <a href="<c:url value='/interview/result'/>">면담 관리</a>
        </li>
    </ul>

</div>
</body>
</html>
