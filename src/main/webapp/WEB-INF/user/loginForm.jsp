<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value='/css/userForm.css' />">
</head>
<body>

    <!-- 상단 바 -->
    <div class="header-bar"></div>

    <!-- 로그인 폼 -->
    <div class="loginForm">
	    <form action="<c:url value='/login' />" method="post">
	        <!-- 로그인 박스 -->
	        <div class="login-box">
	            <div class="login-title">로그인을 해주세요</div>
	
	            <!-- 이메일 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="email">이메일</label>
	                <input type="email" id="email" name="email" placeholder="you@example.com" class="input-field">
	            </div>
	
	            <!-- 비밀번호 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="password">비밀번호</label>
	                <input type="password" id="password" name="password" placeholder="8~15 자리의 비밀번호를 입력하세요" class="input-field">
	            </div>
	
	            <!-- 로그인 버튼 -->
	            <div class="button-group">
	                <button type="submit" class="login-button">로그인</button>
	            </div>
	
			    <!-- 비밀번호 찾기 -->
			    <div class="forgot-password"> 
			    	<a href="findPassword.jsp" class="find-password-link">비밀번호를 잊어버리셨나요?</a>
			    </div>
			    <!-- 회원가입 -->
			    <div class="sign-up">
			    	계정이 없으신가요? &nbsp;&nbsp;
			    	<input type="button" value="회원가입" class="sign-up-link" onclick="window.location.href='<%= request.getContextPath() %>/register/form'">

			    </div>
	        </div>
	    </form>
    </div>

</body>
</html>
