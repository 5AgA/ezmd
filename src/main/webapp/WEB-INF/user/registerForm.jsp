<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value='/css/userForm.css' />">
</head>
<body>
	<!-- 상단 바 -->
    <div class="header-bar"></div>

    <!-- 회원가입 선택 폼 -->
    <div class="registerForm">
        <div class="register-box">
            <div class="register-title">회원가입</div>

            <!-- 회원가입 설명 -->
            <div class="register-description">
                가입할 유형을 선택해주세요.
            </div>

            <!-- 회원가입 버튼 -->
            <div class="button-group">
                <button type="button" class="register-button" onclick="window.location.href='<%= request.getContextPath() %>/register/form/stud'">
                    학생 회원가입
                </button>
                <button type="button" class="register-button" onclick="window.location.href='<%= request.getContextPath() %>/register/form/prof'">
                    교수 회원가입
                </button>
            </div>
        </div>
    </div>
    
</body>
</html>
