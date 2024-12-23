<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>면담 수락/거절</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/interview-check.css">
</head>
<body>
<!-- 헤더 include -->
<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="meeting" />
</jsp:include>

<div class="page-wrapper">
    <div class="main-wrapper">
        <!-- 메인 컨텐츠 -->
        <div class="content-wrapper">
            <!-- 좌측 섹션 -->
            <div class="left-section">
                <div class="list-title">신청 대기 명단</div>
                <div class="list-container">
                    <c:forEach var="interview" items="${pendingList}">
                        <div class="list-item">
                            <div class="student-name">${interview.studentName}&nbsp;(${interview.studentMajor}, ${interview.interviewCategory})
                                <br>    ${fn:replace(interview.requestedDate, 'T', ' ')}

                            </div>
                            <div class="button-group">
                                <button class="accept-button">수락</button>
                                <button class="reject-button">거절</button>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${empty pendingList}">
                        <span>신청 대기 명단이 없습니다.</span>
                    </c:if>
                </div>
            </div>

            <!-- 우측 섹션 -->
            <div class="right-section">
                <div class="right-list-title">신청 확정 명단</div>
                <div class="right-list-container">
                    <c:forEach var="interview" items="${approvedList}">
                        <div class="list-item">
                            <div class="student-name">${interview.studentName}&nbsp;(${interview.studentMajor}, ${interview.interviewCategory})
                                <br>    ${fn:replace(interview.requestedDate, 'T', ' ')}

                            </div>
                            <div class="button-group">
                                <button class="accept-button">수락</button>
                                <button class="reject-button">거절</button>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${empty approvedList}">
                        <span>확정된 면담 일정이 없습니다.</span>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 자바스크립트는 페이지 맨 아래에서 실행 -->
<script>
    window.onload = function() {
        if (!sessionStorage.getItem("formSubmitted")) {
            document.getElementById("getPendingInterviewForm").submit();
            sessionStorage.setItem("formSubmitted", "true");
        }
    };
</script>
</body>
</html>