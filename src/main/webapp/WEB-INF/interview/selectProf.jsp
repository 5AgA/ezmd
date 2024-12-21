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
                			 <div>
                        <button class="department-button">
                            국사학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">김지은</li>
                            <li class="professor-name">안정이</li>
                            <li class="professor-name">김종섭</li>
                        </ul>
                    </div>
                     <div>
                        <button class="department-button">
                            데이터사이언스학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">권범</li>
                            <li class="professor-name">김태완</li>
                            <li class="professor-name">문혜영</li>
                        </ul>
                    </div>
                     <div>
                        <button class="department-button">
                            모델학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">김지영</li>
                            <li class="professor-name">박시은</li>
                            <li class="professor-name">박종철</li>
                        </ul>
                    </div>
                     <div>
                        <button class="department-button">
                            일본어학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">김영민</li>
                            <li class="professor-name">김유영</li>
                            <li class="professor-name">사와다노부에</li>
                        </ul>
                    </div>
                    <div>
                        <button class="department-button">
                            컴퓨터학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">노현아</li>
                            <li class="professor-name">박소희</li>
                            <li class="professor-name">박창섭</li>
                        </ul>
                    </div>
                    <div>
                        <button class="department-button">
                            응용화학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">성지하</li>
                            <li class="professor-name">김민영</li>
                            <li class="professor-name">이노아</li>
                        </ul>
                    </div>
                    <div>
                        <button class="department-button">
                            중어중문학과
                            <span class="triangle">▶</span>
                        </button>
                        <ul class="professor-names hidden">
                            <li class="professor-name">주영현</li>
                            <li class="professor-name">유일한</li>
                            <li class="professor-name">부지영</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Name Search -->
            <div class="name-search-wrapper">
                <h2 class="name-search-title">이름 검색</h2>
                <div class="search-box">
                    <input type="text" class="search-input" placeholder="검색어를 입력하세요">
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
    
    <script>
        // Toggle department professors
        $(document).on("click", ".department-button", function () {
            const $professorList = $(this).next(".professor-names");
            $professorList.toggleClass("hidden");
            const $triangle = $(this).find(".triangle");
            $triangle.text($professorList.hasClass("hidden") ? "▶" : "▼");
        });

        // Highlight selected professor
        $(document).on("click", ".professor-name", function () {
            $(".professor-name").removeClass("selected");
            $(this).addClass("selected");
        });

        // Set hidden input values and submit form
        $(".select-button").on("click", function (e) {
            e.preventDefault(); // 기본 제출 동작 방지

            const $selectedProfessor = $(".professor-name.selected");
            const professorName = $selectedProfessor.text();
            const professorId ="30000010";

            if (!professorName || !professorId) {
                alert("선택된 교수가 없습니다.");
                return;
            }

            // 숨겨진 input 필드에 값 설정
            $("#professorId").val(professorId);
            $("#professorName").val(professorName);

            // 폼 제출
            $("#professorForm").submit();
        });
    </script>
</body>
</html>
