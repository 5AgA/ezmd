<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reservation-submit.css">
    <title>면담 신청 제출</title>
</head>
<body>
<!-- 헤더 -->
<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="meeting"/>
</jsp:include>

<!-- 사이드 메뉴 -->
<jsp:include page="/WEB-INF/sideMenu.jsp">
    <jsp:param name="currentMenu" value="interviewResrv"/>
</jsp:include>

<%
    // 전달된 파라미터 추출
    String professorId = request.getParameter("professorId");
    String professorName = request.getParameter("professorName");
%>

<div class="app-wrapper">
    <!-- Main Content -->
    <div class="main-content">
        <!-- 폼을 통해 라디오 선택 값(interviewCategory)과 textarea 내용(interviewNote)을 전송 -->
        <form action="<%=request.getContextPath()%>/api/interview" method="post">
            <input type="hidden" name="action" value="create"/>
            <!-- 날짜/시간 합쳐서 ISO_LOCAL_DATE_TIME 형태로 변환 (예: "2024-12-20T11:00") -->
            <input type="hidden" name="requestedDate" value="${param.selectedDate}T${param.selectedTime}"/>
            <!-- 교수 ID, 학생 ID 등 필요한 값 -->
            <input type="hidden" name="professorId" value="<%= professorId%>">
            <input type="hidden" name="studentId" value="${userId}"/>


            <div class="left-aligned-wrapper">
                <div class="title">상담 주제 기입</div>
                <div class="radio-group">
                    <label>
                        <input type="radio" name="interviewCategory" value="학업"> 학업
                    </label>
                    <label>
                        <input type="radio" name="interviewCategory" value="진로"> 진로
                    </label>
                    <label>
                        <input type="radio" name="interviewCategory" value="취업"> 취업
                    </label>
                    <label>
                        <input type="radio" name="interviewCategory" value="휴학"> 휴학
                    </label>
                    <label>
                        <input type="radio" name="interviewCategory" value="기타"> 기타
                    </label>
                </div>
            </div>
            <textarea class="text-area" name="interviewNote" placeholder="상담 내용을 입력해주세요."></textarea>
            <div class="notice">아래 예약 정보가 맞는지 확인해주세요.</div>
            <div class="reservation-details">
                <div class="professor-info"><%= professorName%> 교수님</div>
                <div>${param.selectedDate}</div>
                <div>${param.selectedTime}</div>
            </div>
            <div class="reserve-button-wrapper">
                <button type="submit" class="reserve-button-styled">예약 하기</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
