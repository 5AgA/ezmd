<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ez면담</title>
    <link rel="stylesheet" href="<c:url value='/css/index.css' />">
</head>
<body>
    <div class="container">
        <div class="content">
            <div class="images">
                <img src="<c:url value='/images/book-icon.svg'/>" alt="Book" class="icon1">
                <img src="<c:url value='/images/calendar-icon.svg'/>" alt="Calendar" class="icon2">
                <img src="<c:url value='/images/chat-icon.svg'/>" alt="Chat" class="icon3">
            </div>
            <p class="description">교수님과 부담 없는 면담으로,<br>더 알찬 학교 생활</p>
            <h1 class="title">EZ 면담</h1>
            
        </div>
        <div class="bottom">
	    	<button class="button" onclick="window.location.href='<%= request.getContextPath() %>/user/login/form'"><b>사용하기</b></button>
        </div>
    </div>
</body>
</html>
