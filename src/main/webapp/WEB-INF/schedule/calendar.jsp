<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<link rel="stylesheet" href="<c:url value='/css/calendar.css'/>">
</head>
		<div class="calendar-header">
			<h2 id="currentMonth">2024년 12월</h2>
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
<script src="<c:url value='/js/calendar.js'/>"></script>
