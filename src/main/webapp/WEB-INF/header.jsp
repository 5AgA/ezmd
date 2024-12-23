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
		
	</div>
</div>
<!-- 배경 블러 -->
<div id="background" class="blur-background hidden"></div>

</script>
