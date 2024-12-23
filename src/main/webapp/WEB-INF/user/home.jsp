<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <jsp:param name="currentPage" value="home"/>
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

            </div>
        </div>

        <c:if test="${userType == 'student'}">
        <div class="pending-meetings">
            <h3>승인 대기 중인 면담</h3>
            <ul>
                <c:forEach var="interview" items="${pendingList}">
                    <li>${fn:replace(interview.requestedDate, 'T', ' ')} ${interview.professorName} 교수님</li>
                </c:forEach>
                <c:if test="${empty pendingList}">
                    <span>신청한 면담이 없습니다.</span>
                </c:if>
            </ul>
        </div>

        <div class="scheduled-meetings">
            <h3>예정된 면담</h3>
            <ul>
                <c:forEach var="interview" items="${approvedList}">
                    <li>${fn:replace(interview.requestedDate, 'T', ' ')} ${interview.professorName} 교수님</li>
                </c:forEach>
                <c:if test="${empty approvedList}">
                    <span>예정된 면담이 없습니다.</span>
                </c:if>
            </ul>
        </div>
        </c:if>

        <!-- 교수일 경우 면담 정보 출력 -->
        <c:if test="${userType == 'professor'}">
            <div class="pending-meetings">
                <h3>승인 대기 중인 면담</h3>
                <ul>
                    <c:forEach var="interview" items="${pendingList}">
                        <li>${fn:replace(interview.requestedDate, 'T', ' ')} ${interview.studentName}</li>
                    </c:forEach>
                    <c:if test="${empty pendingList}">
                        <span>신청한 면담이 없습니다.</span>
                    </c:if>
                </ul>
            </div>

            <div class="scheduled-meetings">
                <h3>예정된 면담</h3>
                <ul>
                    <c:forEach var="interview" items="${approvedList}">
                        <li>${fn:replace(interview.requestedDate, 'T', ' ')} ${interview.studentName}</li>
                    </c:forEach>
                    <c:if test="${empty approvedList}">
                        <span>예정된 면담이 없습니다.</span>
                    </c:if>
                </ul>
            </div>
        </c:if>

    </div>
    <div class="calendar-container">
        <jsp:include page="../schedule/calendar.jsp"></jsp:include>
    </div>
</div>
</body>
</html>
