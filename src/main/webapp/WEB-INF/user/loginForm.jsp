<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String signupSuccessParam = request.getParameter("signupSuccess");
   boolean signupSuccess = "true".equals(signupSuccessParam);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value='/css/userForm.css' />">
    <script>
    	function updateFormAction(){
    		const studentRadio = document.getElementById('studentRadio');
    		const form = document.getElementById('loginForm');

    		if (studentRadio.checked){
    			form.action= '<c:url value="/login/student" />';
    		}else{
    			form.action = '<c:url value="/login/professor" />';
    		}
    	}
/* 
		// 알림 메시지 표시 함수
		function showAlert() {
			const params = new URLSearchParams(window.location.search);
			const alertMessage = params.get("alert");
			if (alertMessage) {
				alert("로그인이 필요한 서비스입니다. 로그인을 해주세요.");
			}
		} 
 */
		window.onload = function(){
			updateFormAction();
			showAlert()
		}


    </script>
</head>

<body>

    <!-- 상단 바 -->
    <div class="header-bar"></div>

    <!-- 로그인 폼 -->
    <div class="loginForm">
	    <form id="loginForm" method="post">
	        <!-- 로그인 박스 -->
	        <div class="login-box">
	            <div class="login-title">로그인을 해주세요</div>
	
				<!-- 사용자 타입 선택 -->
				<div class="input-group">
					<label class="input-label">사용자 유형</label>
					<div class="radio-group">
						<label>
							<input type="radio" id="studentRadio" name="userType" value="student" checked onclick="updateFormAction()"> 학생
						</label>
						<label>
                            <input type="radio" id="professorRadio" name="userType" value="professor" onclick="updateFormAction()"> 교수
                        </label>
					</div>
				</div>

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
			    	<div class="find-password-link">비밀번호를 잊어버리셨나요? 관리자에게 문의주세요</div>
			    </div>
			    <!-- 회원가입 -->
			    <div class="sign-up">
			    	계정이 없으신가요? &nbsp;&nbsp;
			    	<input type="button" value="회원가입" class="sign-up-link" onclick="window.location.href='<%= request.getContextPath() %>/register/form'">
			    </div>

				<!-- 에러 메시지 표시 via alert -->
				<c:if test="${not empty errorMessage}">
				    <script>
				        // 메시지에 포함될 수 있는 특수 문자를 안전하게 처리하기 위해 JSTL의 fn:escapeXml을 사용
				        var errorMessage = '<c:out value="${errorMessage}" />';
				        alert(errorMessage);
				    </script>
				</c:if>

	        </div>
	    </form>
    </div>

</body>
</html>
