<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value='/css/register.css' />">
</head>
<body>
	<!-- 상단 바 -->
    <div class="header-bar"></div>

    <!-- 회원가입 선택 폼 -->
    <div class="register-page">
        <div class="register-box1">
            <div class="register-title">회원가입</div>

            <!-- 회원가입 설명 -->
            <div class="register-description">
                가입할 유형을 선택해주세요.
            </div>

            <!-- 회원가입 버튼 -->
            <ul class="button-group">
        		<li>
                	<button type="button" class="register-page-btn" onclick="window.location.href='<%= request.getContextPath() %>/register/form/stud'">
                	    학생 회원가입
                	</button>
                </li>
                <li>
	                <button type="button" class="register-page-btn" onclick="window.location.href='<%= request.getContextPath() %>/register/form/prof'">
	                    교수 회원가입
	                </button>
	        	</li>
            </ul>
        </div>
    </div>
    
</body>
</html>
