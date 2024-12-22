<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage.css">
</head>
<body>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="currentPage" value="myPage" />
	</jsp:include>

    <div class="container">
        <h2>비밀번호 변경</h2>
        
        <!-- 성공 메시지 -->
		<c:if test="${not empty successMessage}">
		    <script>
		        alert('<c:out value="${successMessage}" />');
		    </script>
		</c:if>
		
		<!-- 오류 메시지 -->
		<c:if test="${not empty errorMessage}">
		    <script>
		        alert('<c:out value="${errorMessage}" />');
		    </script>
		</c:if>

        
        <form action="<c:url value='/myPage/update/info'/>" method="post">
            <div class="form-group">
                <label class="label1" for="currentPassword">현재 비밀번호</label>
                <input type="password" id="currentPassword" name="currentPassword" required>
            </div>
            <div class="form-group">
                <label class="label1" for="newPassword">새 비밀번호</label>
                <input type="password" id="newPassword" name="newPassword" required minlength="8" 
           title="비밀번호는 최소 8자 이상이어야 합니다.">
            </div>
            <div class="form-group">
                <label class="label1" for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="8" 
           title="비밀번호는 최소 8자 이상이어야 합니다.">
            </div>
            <div class="action-buttons">
                <button type="submit">비밀번호 변경</button>
                <button type="button" onclick="window.location.href='<c:url value='/ezmd/myPage' />'">취소</button>
            </div>
        </form>
    </div>
</body>
</html>
