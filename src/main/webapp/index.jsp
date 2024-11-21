<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ez면담</title>
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>
    <div class="container">
        <div class="content">
            <div class="images">
                <img src="/images/book-icon.png" alt="Book" class="icon">
                <img src="/images/calendar-icon.png" alt="Calendar" class="icon">
                <img src="/images/chat-icon.png" alt="Chat" class="icon">
            </div>
            <p class="description">교수님과 부담 없는 면담으로,<br>더 알찬 학교 생활</p>
            <h1 class="title">EZ 면담</h1>
            <button class="button" onclick="window.location.href='<%= request.getContextPath() %>/user/login/form'">사용하기</button>
            
        </div>
    </div>
</body>
</html>
