<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/header.css'/>">

<div class="menu">
	<c:set var="currentPage" value="${pageName}" />
	<%
		String currentPage = request.getParameter("currentPage");
	%>
	<nav class="navbar">
		<ul>
			<li class="<%= "home".equals(currentPage) ? "active" : "" %>">
				<a href="<c:url value='/home'/>">
					<img src="<c:url value='/images/home-icon.svg'/>" alt="Home">
				</a>
			</li>
			<li class="<%= "schedule".equals(currentPage) ? "active" : "" %>">
				<a href="<c:url value='/schedule'/>">일정 관리</a>
			</li>
			<li class="<%= "meeting".equals(currentPage) ? "active" : "" %>">
				<a href="<c:url value='/interview'/>">면담 관리</a>
			</li>
			<li class="<%= "mypage".equals(currentPage) ? "active" : "" %>">
				<a href="<c:url value='/mypage'/>">마이페이지</a>
			</li>
		</ul>
	</nav>
	<div class="submenu">
		<div class="logout">
			<a href="<c:url value='/logout'/>">로그아웃</a>
		</div>	
		<div class="notification">
			<a href="javascript:void(0);" onclick="toggleNotificationPopup()">
				<img src="<c:url value='/images/noti-icon.svg'/>" alt="Notification">
			</a>
		</div>
	</div>
</div>
<!-- 배경 블러 -->
<div id="background" class="blur-background hidden"></div>

<!-- 알림 팝업 -->
<div id="notification-popup" class="popup hidden">
    <div class="popup-content">
        <h3>알림</h3>
        <c:choose>
            <%-- 데이터가 있는 경우 --%>
            <c:when test="${not empty notificationGroups}">
                <c:forEach var="group" items="${notificationGroups}">
                    <%-- 날짜 박스 --%>
                    <div class="date-box">
                        ${group.date}
                    </div>
                    <%-- 알림 내용들 --%>
                    <c:forEach var="notification" items="${group.notifications}">
                        <div class="notification-box">
                            ${notification.message}
                        </div>
                    </c:forEach>
                </c:forEach>
            </c:when>
            <%-- 데이터가 없는 경우 --%>
            <c:otherwise>
                <div class="date-box">
                    알림이 없습니다.
                </div>
            </c:otherwise>
        </c:choose>
        <button class="close-button"onclick="closeNotificationPopup()">닫기</button>
    </div>
</div>



<script>
function toggleNotificationPopup() {
	const popup = document.getElementById("notification-popup");
	popup.classList.toggle("hidden");
}

function closeNotificationPopup() {
	const popup = document.getElementById("notification-popup");
	popup.classList.add("hidden");
}
</script>
