<!-- header.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="menu">
	<nav class="navbar">
		<ul>
			<li class="${currentPage == 'home' ? 'active' : ''}"><a
				href="<c:url value='/home'/>"> <img
					src="<c:url value='/images/home-icon.svg'/>">
			</a></li>
			<li class="${currentPage == 'schedule' ? 'active' : ''}"><a
				href="<c:url value='/schedule'/>">일정 관리</a></li>
			<li class="${currentPage == 'meeting' ? 'active' : ''}"><a
				href="<c:url value='/meeting'/>">면담 관리</a></li>
			<li class="${currentPage == 'mypage' ? 'active' : ''}"><a
				href="<c:url value='/mypage'/>">마이페이지</a></li>
		</ul>
	</nav>
	<div class="submenu">
		<div class="logout">
			<a href="<c:url value='/logout'/>">로그아웃</a>
		</div>	
		<div class="notification">
			<a href="<c:url value='/notification'/>"> <img
				src="<c:url value='/images/noti-icon.svg'/>">
			</a>
		</div>
	</div>
</div>

<style>
/* 예시 CSS */
.menu {
	background-color: #8b2942;
	color: #fff;
	width: 100%;
	height: 150px;
	display: flex;
	
}

.navbar {
	display: flex;
	justify-content: flex-start;
}

.navbar ul {
	list-style: none;
	padding-top: 85px;
	padding-left: 150px;
	margin: 0;
	display: flex;
	gap: 200px;
}

.navbar li {
	font-size: 20px;
	font-weight: bolder;
	height: 40px;
}

a {
	text-decoration: none;
	color: white;
}

.navbar li.active a {
	border: 1px solid black;
}

.submenu {
	margin-left: auto;
}

.logout {
	font-size: 13px;
	margin-top: 30px;
	margin-right: 80px;
}

.notification {
	margin-top: 42px;
	margin-left: 30px;
}
</style>
