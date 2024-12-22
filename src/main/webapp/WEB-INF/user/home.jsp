<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <title>홈</title>
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="currentPage" value="home" />
	</jsp:include>

    <div class="main">
        <div class="sidebar">
            <div class="profile">
                <img src="<%= request.getContextPath() %>/images/profile-icon.png" alt="Profile">
                <div class="profile-info">
                    <c:if test="${not empty user}">
                        <p><b>${user.name}</b></p>
                        <p>${user.dept}</p>
                        <c:choose>
                            <c:when test="${userType == 'professor'}">
                                <p>교수 ID: ${user.professorId}</p>
                            </c:when>
                            <c:when test="${userType == 'student'}">
                                <p>학번: ${user.studentId}</p>
                            </c:when>
                            <c:otherwise>
                                <p>ID: N/A</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${empty user}">
                        <p><b>게스트</b></p>
                        <p>N/A</p>
                        <p>ID: N/A</p>
                    </c:if>

<%--                    <p><b>김동덕</b></p>--%>
<%--                    <p>동덕여자대학교</p>--%>
<%--                    <p>컴퓨터학과 22학번 3학년</p>--%>

                </div>
            </div>

            <div class="pending-meetings">
                <h3>승인 대기 중인 면담</h3>
                <ul>
                    <li>9/27 15:00 박창섭 교수님</li>
                    <li>10/10 18:00 박수희 교수님</li>
                </ul>
            </div>

            <div class="scheduled-meetings">
                <h3>예정된 면담</h3>
                <ul>
                    <li>10/2 10:00 이완연 교수님</li>
                    <li>10/5 18:00 한혁 교수님</li>
                </ul>
            </div>
        </div>
        <div class="calendar-container">
       		<jsp:include page="../schedule/calendar.jsp"></jsp:include>
        </div>
    </div>
</body>
</html>
