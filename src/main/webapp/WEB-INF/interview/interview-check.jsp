<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>면담 수락/거절</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/interview-check.css">
</head>
<body>
  <!-- 헤더 include -->
  <jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="meeting" />
  </jsp:include>

<div class="page-wrapper">
  <!-- 두 번째 라인 네비게이션 바 -->
  <div class="sub-nav">
    <ul class="sub-menu">
      <li class="sub-menu-item">면담완료</li>
      <li class="sub-menu-item active">면담예정</li>
    </ul>
  </div>

  <div class="main-wrapper">
    <!-- Sidebar -->
    <div class="sidebar-wrapper">
      <div class="side-menu-item">면담 신청</div>
      <div class="side-menu-item">면담 관리</div>
    </div>

    <!-- 메인 컨텐츠 -->
    <form id="getPendingInterviewForm" action="<%=request.getContextPath()%>/api/interview" method="get">
      <input type="hidden" name="action" value="getByProfessorIdAndStatus" />
      <input type="hidden" name="professorId" value="30000010" />
    </form>
    <div class="content-wrapper">
      <!-- 좌측 섹션 -->
      <div class="left-section">
        <div class="list-title">신청 대기 명단</div>
        <div class="list-container">
        <c:forEach var="interview" items="${interviews}">
          <div class="list-item">
            <div class="student-name">${interview.studentName}(${interview.studentMajor}, ${interview.interviewCategory})
            <br/><br/>    ${fn:replace(interview.requestedDate, 'T', ' ')}

</div>
            <div class="button-group">
              <button class="accept-button">수락</button>
              <button class="reject-button">거절</button>
            </div>
          </div>
          </c:forEach>
<c:if test="${empty interviews}">
  <span>신청 대기 명단이 없습니다.</span>
</c:if>
        </div>
      </div>

      <!-- 우측 섹션 -->
      <div class="right-section">
        <div class="right-list-title">신청 확정 명단</div>
        <div class="right-list-container">
          <div class="confirmed-item">이연(컴퓨터학, 20210193)<br/><br/>24-12-25 15:00</div>
          <div class="confirmed-item">임승연(컴퓨터학, 20210808)<br/><br/>24-12-25 15:00</div>
          <div class="confirmed-item">이세미(컴퓨터학, 20210670)<br/><br/>24-12-25 15:00</div>
          <div class="confirmed-item">오은아(컴퓨터학, 20220783)<br/><br/>24-12-25 15:00</div>
          <div class="confirmed-item">아무개(컴퓨터학, 20220000)<br/><br/>24-12-25 15:00</div>
          <div class="confirmed-item">아무개(컴퓨터학, 20220000)<br/><br/>24-12-25 15:00</div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 자바스크립트는 페이지 맨 아래에서 실행 -->
<script>
window.onload = function() {
    if (!sessionStorage.getItem("formSubmitted")) {
        document.getElementById("getPendingInterviewForm").submit();
        sessionStorage.setItem("formSubmitted", "true");
    }
};
</script>
</body>
</html>
