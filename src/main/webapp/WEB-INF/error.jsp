<!-- error.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>페이지를 찾을 수 없습니다</title>
    <link rel="stylesheet" href="<c:url value='/css/error.css' />">
</head>
<body>
    <div class="error-container">
        <h1>404 - 페이지를 찾을 수 없습니다</h1>
        <p>죄송합니다. 요청하신 페이지가 존재하지 않습니다.</p>
        <a href="<c:url value='/' />">홈으로 돌아가기</a>
    </div>
</body>
</html>
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}
.error-container {
    text-align: center;
    background-color: #fff;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
.error-container h1 {
    font-size: 48px;
    margin-bottom: 20px;
    color: #b0173e;
}
.error-container p {
    font-size: 18px;
    margin-bottom: 30px;
}
.error-container a {
    text-decoration: none;
    color: #fff;
    background-color: #b0173e;
    padding: 10px 20px;
    border-radius: 4px;
    transition: background-color 0.3s;
}
.error-container a:hover {
    background-color: #8e1231;
}
</style>