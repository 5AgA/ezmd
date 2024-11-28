<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value='/css/mypage.css' />">
</head>
<body>
	
	<!-- 헤더 -->
	<jsp:include page="/WEB-INF/header.jsp">
	    <jsp:param name="currentPage" value="mypage" />
	</jsp:include>	

    <div class="container">
        <!-- 프로필 정보 -->
        <div class="profile-section">
            <img src="<c:url value='/images/profile-icon.png'/>" alt="Profile Image" class="profile-img">
            <h2 class="username">김동</h2>
        </div>

        <!-- 개인정보 입력 폼 -->
        <form class="user-info-form">
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="text" id="email" value="20210808@dongduk.ac.kr" readonly>
            </div>
            <div class="form-group">
                <label for="student-id">학번</label>
                <input type="text" id="student-id" value="20210808" readonly>
            </div>
            <div class="form-group">
                <label for="birthdate">생년월일</label>
                <input type="text" id="birthdate" value="2000-00-00" readonly>
            </div>
            <div class="form-group">
                <label for="major">학과</label>
                <input type="text" id="major" value="컴퓨터학과" readonly>
            </div>
            <div class="form-group">
                <label for="double-major">복수전공/부전공</label>
                <input type="text" id="double-major" value="정보통계학과" readonly>
            </div>
            <div class="form-group">
                <label for="phone">Phone</label>
                <input type="text" id="phone" value="010-0000-9999" readonly>
            </div>
        </form>

        <!-- 내 정보 저장 및 비밀번호 변경 -->
        <div class="action-buttons">
            <button type="button" onclick="saveInfo()">내 정보 저장</button>
            <button type="button" onclick="changePassword()">비밀번호 변경</button>
        </div>
    </div>

    <script>
        function saveInfo() {
            alert('정보가 저장되었습니다.');
        }

        function changePassword() {
            alert('비밀번호 변경 페이지로 이동합니다.');
        }
    </script>

</body>

</html>
    