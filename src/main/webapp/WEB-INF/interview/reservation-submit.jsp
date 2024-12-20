<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reservation-submit.css"><title>면담 신청 제출</title>
</head>
<body>
<!-- 헤더 -->
 <jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="currentPage" value="reservation" />
	</jsp:include>


<div class="app-wrapper">


  <!-- Sidebar -->
  <div class="sidebar-wrapper">
    <div class="menu-item">면담 신청</div>
    <div class="menu-item">면담 관리</div>
  </div>

  <!-- Main Content -->
  <div class="main-content">
    <!-- 폼을 통해 라디오 선택 값(topic)과 textarea 내용(consultationContent)을 전송 -->
    <form action="<%=request.getContextPath()%>/submitInterview.do" method="post">
      <div class="left-aligned-wrapper">
        <div class="title">상담 주제 기입</div>
        <div class="radio-group">
          <label>
            <input type="radio" name="topic" value="academic"> 학업
          </label>
          <label>
            <input type="radio" name="topic" value="career"> 진로
          </label>
          <label>
            <input type="radio" name="topic" value="employment"> 취업
          </label>
          <label>
            <input type="radio" name="topic" value="rest"> 휴학
          </label>
          <label>
            <input type="radio" name="topic" value="other"> 기타
          </label>
        </div>
      </div>
      <textarea class="text-area" name="consultationContent" placeholder="상담 내용을 입력해주세요."></textarea>
      <div class="notice">아래 예약 정보가 맞는지 확인해주세요.</div>
      <div class="reservation-details">
        <div class="professor-info">박창섭 교수님</div>
        <div>9월 20일</div>
        <div>11:00</div>
      </div>
      <div class="reserve-button-wrapper">
        <button type="submit" class="reserve-button-styled">예약 하기</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>