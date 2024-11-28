<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="<c:url value='/favicon.ico' />"
	type="image/x-icon">
<title>일정 관리</title>
<link rel="stylesheet" href="<c:url value='/css/calendar.css'/>">
</head>
<body>
	<jsp:include page="/WEB-INF/header.jsp">
		<jsp:param name="currentPage" value="schedule" />
	</jsp:include>

	<div class="container">
		<div class="calendar-container">
			<div class="calendar-header">
				<h2 id="currentMonth">2024년 9월</h2>
				<div class="currentDay">
					<button class="month-nav prev">&lt;</button>
					<button class="today-btn">오늘</button>
					<button class="month-nav next">&gt;</button>
				</div>
			</div>

			<!-- Calendar Grid -->
			<div class="calendar-grid">
				<div class="day-name">일</div>
				<div class="day-name">월</div>
				<div class="day-name">화</div>
				<div class="day-name">수</div>
				<div class="day-name">목</div>
				<div class="day-name">금</div>
				<div class="day-name">토</div>

				<!-- JavaScript로 채워질 부분 -->
			</div>


			<button class="add-schedule-btn">+</button>
		</div>
		<div class="today-info">오늘은 일정이 없습니다</div>
	</div>
	<script src="<c:url value='/js/calendar.js'/>"></script>

</body>
</html>
