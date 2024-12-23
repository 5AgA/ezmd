<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/header.css'/>">

<%
    // 세션에서 userType 가져오기
     HttpSession curSession = request.getSession(false);
        if (curSession == null || curSession.getAttribute("user") == null || curSession.getAttribute("userType") == null) {
            response.sendRedirect("/login/form");
            return;
        }
        String userType = (String) curSession.getAttribute("userType");
%>

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

		<%
          if ("Student".equalsIgnoreCase(userType)) {
        %>
			<li class="<%= "meeting".equals(currentPage) ? "active" : "" %>">
				<a href="<c:url value='/interview'/>">면담 관리</a>
			</li>

		<%
            } else if ("Professor".equalsIgnoreCase(userType)) {
        %>
        <li class="<%= "meeting".equals(currentPage) ? "active" : "" %>">
                <a href="<c:url value='/interview-check'/>">면담 관리</a>
            </li>
        <%
            }
        %>

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

</script>
