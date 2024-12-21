<!-- header.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="menu">
	<c:set var="currentPage" value="${pageName}" />
	<%
		String currentPage = request.getParameter("currentPage");
		System.out.println(currentPage);
	%>
	<nav class="navbar">
		<ul>
			<li class="<%= "home".equals(currentPage) ? "active" : "" %>"><a
				href="<c:url value='/home'/>"> <img
					src="<c:url value='/images/home-icon.svg'/>">
			</a></li>
			<li class="<%= "schedule".equals(currentPage) ? "active" : "" %>"><a
				href="<c:url value='/schedule'/>">일정 관리</a></li>
			<li class="<%= "meeting".equals(currentPage) ? "active" : "" %>"><a
				href="<c:url value='/select-prof'/>">면담 관리</a></li>
			<li class="<%= "mypage".equals(currentPage) ? "active" : "" %>"><a
				href="<c:url value='/myPage'/>">마이페이지</a></li>
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
.menu {
	background-color: #8b2942;
	color: #fff;
	width: 100%;
	height: 15vh;
	display: flex;
	flex-wrap: wrap;
	
}

.navbar {
	display: flex;
	justify-content: flex-start;
	height: 100%;
	flex-grow: 1;
	overflow: hidden;
}

.navbar ul {
	list-style: none;
	padding-top: 9vh;
	padding-left: 9vw;
	margin: 0;
	display: flex;
	flex-wrap: wrap;
	gap: 10vw;
	justify-content: flex-start;
	width: 100%;
}

.navbar li.active a{
	border-radius: 5px;
	background-color: #770421;
}

.navbar li {
	font-size: 1.3vw;
	font-weight: bolder;
	height: 1.8vw;
}

a {
	text-decoration: none;
	color: white;
	display: inline-block;
	padding: 0.3vw 0.5vw;
}

img {
	width: 1.8vw;
	margin: 3px;
	display: block;
	margin: 0 auto;
}

.submenu {
    display: flex; 
    flex-direction: column; 
    margin-left: auto;
    height: 100%; 
}

.logout {
    font-size: 0.9vw;
    margin-top: 30px;
    margin-right: 80px;
}

.notification {
    margin-top: auto;
    margin-bottom: 20px;
    margin-left: 30px;
}
</style>
