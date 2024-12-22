<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/selectProf.css">
    <title>교수 선택</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="meeting" />
</jsp:include>

    <div class="global-wrapper">
        <!-- Sidebar -->
        <div class="sidebar-wrapper">
            <div class="menu-item">면담 신청</div>
            <div class="menu-item">면담 관리</div>
        </div>

        <!-- Main Content -->
        <div class="container">
            <!-- Professor List -->
            <div class="professor-list-wrapper">
                <h3 class="professor-title">교수 목록</h3>
                <div class="departments">

                </div>
            </div>

            <!-- Name Search -->
            <div class="name-search-wrapper">
                <h2 class="name-search-title">이름 검색</h2>
                <div class="search-box">
                    <input type="text" class="search-input" placeholder="검색어를 입력하세요">
                </div>
                <div class="searched-box">
                </div>
            </div>
        </div>

        <!-- Fixed Select Button -->
        <button class="select-button">교수 선택</button>
        
        <!-- Form for Submission -->
        <form id="professorForm" action="<%=request.getContextPath()%>/interview" method="post">
            <input type="hidden" name="professorId" id="professorId" value="">
            <input type="hidden" name="professorName" id="professorName" value="">
            <button type="submit" class="select-button">교수 선택</button>
        </form>
    </div>
    
    <script src="<c:url value="/js/selectProf.js" />"></script>
</body>
</html>
