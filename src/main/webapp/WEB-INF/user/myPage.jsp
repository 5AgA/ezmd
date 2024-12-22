<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage.css">
</head>
<body>

	<jsp:include page="/WEB-INF/header.jsp">
		<jsp:param name="currentPage" value="mypage" />
	</jsp:include>

    <div class="container">
    	<div class="profile-box">
    		<!-- 성공 메시지 -->
			<c:if test="${not empty sessionScope.successMessage}">
			    <script>
			        alert('<c:out value="${sessionScope.successMessage}" />');
			    </script>
			    <!-- 성공 메시지 후 세션에서 제거 -->
			    <c:remove var="successMessage" scope="session" />
			</c:if>
			
			<!-- 오류 메시지 -->
			<c:if test="${not empty sessionScope.errorMessage}">
			    <script>
			        alert('<c:out value="${sessionScope.errorMessage}" />');
			    </script>
			    <!-- 오류 메시지 후 세션에서 제거 -->
			    <c:remove var="errorMessage" scope="session" />
			</c:if>

    	
    	
	        <!-- 프로필 정보 -->
	        <div class="profile-section">
	            <img src="<c:url value='/images/profile-icon.png'/>" alt="Profile Image" class="profile-img">
	            <h2 class="username">
	            	<c:choose>
	            		<c:when test="${sessionScope.user ne null }">
	            			${sessionScope.user.name }
	           			</c:when>
	           			<c:otherwise>
	           				로그인 정보 없음
	         			</c:otherwise>
	       			</c:choose>
	            </h2>
	        </div>

			<c:choose>
				<c:when test="${sessionScope.user ne null && sessionScope.userType eq 'professor' }">
					<!-- 교수 정보 폼 -->
					<form class="user-info-form" action="<c:url value='/myPage/update'/>" method="post" id="infoForm">
						<div class="form-group">
							<label for="professorId">교수 번호</label>
							<input type="text" id="professorId" name="professorId" value="${sessionScope.user.professorId}" readonly>
	           			</div>
	                    <!-- email -->
	                    <div class="form-group">
	                        <label for="email">이메일</label>
	                        <input type="text" id="email" name="email" value="${sessionScope.user.email}" readonly>
	                    </div>
	                    <!-- dept -->
	                    <div class="form-group">
	                        <label for="dept">학과</label>
	                        <input type="text" id="dept" name="dept" value="${sessionScope.user.dept}">
	                    </div>
	                    <!-- professorOffice -->
	                    <div class="form-group">
	                        <label for="professorOffice">연구실 위치</label>
	                        <input type="text" id="professorOffice" name="professorOffice" value="${sessionScope.user.professorOffice}" readonly>
	                    </div>
	        
	        			<div class="action-buttons">
	                        <button type="button" onclick="saveInfo()">내 정보 저장</button>
	                        <button type="button" onclick="changePassword()">비밀번호 변경</button>
	                    </div>
	                </form>
	            </c:when>
	            <c:when test="${sessionScope.user ne null && sessionScope.userType eq 'student'}">
	                <!-- 학생 정보 폼 -->
	                <form class="user-info-form" action="<c:url value='/myPage/update'/>" method="post" id="infoForm">
	                    <!-- studentId -->
	                    <div class="form-group">
	                        <label for="studentId">학번</label>
	                        <input type="text" id="studentId" name="studentId" value="${sessionScope.user.studentId}" readonly>
	                    </div>
	                    <!-- email -->
	                    <div class="form-group">
	                        <label for="email">이메일</label>
	                        <input type="text" id="email" name="email" value="${sessionScope.user.email}" readonly>
	                    </div>
	                    <!-- dept -->
	                    <div class="form-group">
	                        <label for="dept">학과</label>
	                        <input type="text" id="dept" name="dept" value="${sessionScope.user.dept}">
	                    </div>
	                    <!-- grade: 수정 가능 (1~4, 그리고 5학년 이상) -->
	                    <div class="form-group">
	                        <label for="grade">학년</label>
	                        <select id="grade" name="grade">
	                            <option value="1" <c:if test="${sessionScope.user.grade == 1}">selected</c:if>>1학년</option>
	                            <option value="2" <c:if test="${sessionScope.user.grade == 2}">selected</c:if>>2학년</option>
	                            <option value="3" <c:if test="${sessionScope.user.grade == 3}">selected</c:if>>3학년</option>
	                            <option value="4" <c:if test="${sessionScope.user.grade == 4}">selected</c:if>>4학년</option>
	                            <option value="5" <c:if test="${sessionScope.user.grade >= 5}">selected</c:if>>5학년 이상</option>
	                        </select>
	                    </div>
	
	                    <div class="action-buttons">
	                        <button type="button" onclick="saveInfo()">내 정보 저장</button>
	                        <button type="button" onclick="changePassword()">비밀번호 변경</button>
	                    </div>
	                </form>
	            </c:when>
	            <c:otherwise>
	                <!-- 로그인 안된 경우 -->
	                <p>로그인이 필요합니다.</p>
	            </c:otherwise>
	        </c:choose>
        </div>
    </div>
    <script>
        function saveInfo() {
        	if (confirm('저장하시겠습니까?')) {
                // 현재 표시된 폼을 선택
                const form = document.querySelector('.user-info-form');
                if (form) {
                    form.submit();
                }
            }
           	/* document.getElementById('infoForm').submit();
            alert('정보가 저장되었습니다.'); */
        }

        function changePassword() {
        	window.location.href = '<c:url value="/myPage/update/password" />';
        }
    </script>

</body>

</html>
    