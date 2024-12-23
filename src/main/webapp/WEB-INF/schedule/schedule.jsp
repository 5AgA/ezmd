<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/favicon.ico' />"
          type="image/x-icon">
    <title>일정 관리</title>
    <link rel="stylesheet" href="<c:url value='/css/calendar.css'/>">
</head>
<script>
    const uid = ${userId};
</script>
<body>
<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="schedule"/>
</jsp:include>

<div class="container">
    <div class="calendar-container">
        <jsp:include page="./calendar.jsp"/>
    </div>

    <button class="add-btn">+</button>
    <div class="action-buttons">
        <button id="schedule-modal-btn">스케줄</button>
        <br>
        <button id="category-modal-btn">카테고리</button>
    </div>

    <div class="today-info"></div>
</div>

<jsp:include page="./createScheduleForm.jsp"/>
<jsp:include page="./editScheduleForm.jsp"/>
<jsp:include page="./categoryForm.jsp"/>

</body>
</html>
