<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.domain.Interview" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/interviewClearList.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            let originalData = {}; // 초기 데이터를 저장할 객체

            // 면담완료/면담예정 탭 클릭 시 상태 변경
            $(".tab-button").click(function () {
                var tabType = $(this).text(); // 버튼 텍스트에 따라 면담 완료 또는 면담 예정 선택

                if (tabType === "면담예정") {
                    // 면담 예정 탭 클릭
                    $.ajax({
                        url: "<%= request.getContextPath() %>/interview/result",
                        method: "GET",
                        data: {action: "getUpcomingInterviews"},
                        success: function (response) {
                            $(".interview-grid").empty(); // 기존 면담 리스트 삭제
                            if (response && response.interviewList) {
                                // 면담 예정 목록을 화면에 출력
                                response.interviewList.forEach(function (interview) {
                                    $(".interview-grid").append(`
                                        <div class="interview-box" data-id="${interview.interviewId}">
                                            <h3>${interview.studentName} 학생</h3>
                                            <p>${interview.requestedDate.toLocalDate()}</p>
                                            <p>${interview.requestedDate.toLocalTime()}</p>
                                        </div>
                                    `);
                                });
                            } else {
                                $(".interview-grid").append("<div class='no-data'>면담 예정이 없습니다.</div>");
                            }
                        },
                        error: function () {
                            alert("데이터를 불러오는 중 문제가 발생했습니다.");
                        }
                    });
                } else if (tabType === "면담완료") {
                    // 면담 완료 탭 클릭
                    $.ajax({
                        url: "<%= request.getContextPath() %>/interview/result",
                        method: "GET",
                        data: {action: "getCompletedInterviews"},
                        success: function (response) {
                            $(".interview-grid").empty(); // 기존 면담 리스트 삭제
                            if (response && response.interviewList) {
                                // 면담 완료 목록을 화면에 출력
                                response.interviewList.forEach(function (interview) {
                                    $(".interview-grid").append(`
                                        <div class="interview-box" data-id="${interview.interviewId}">
                                            <h3>${interview.studentName} 학생</h3>
                                            <p>${interview.requestedDate.toLocalDate()}</p>
                                            <p>${interview.requestedDate.toLocalTime()}</p>
                                        </div>
                                    `);
                                });
                            } else {
                                $(".interview-grid").append("<div class='no-data'>완료된 면담이 없습니다.</div>");
                            }
                        },
                        error: function () {
                            alert("데이터를 불러오는 중 문제가 발생했습니다.");
                        }
                    });
                }
            });

            // 메시지 출력
            <% if (request.getSession().getAttribute("successMessage") != null) { %>
            alert('<%= request.getSession().getAttribute("successMessage") %>');
            <% request.getSession().removeAttribute("successMessage"); %>
            <% } %>
            <% if (request.getSession().getAttribute("errorMessage") != null) { %>
            alert('<%= request.getSession().getAttribute("errorMessage") %>');
            <% request.getSession().removeAttribute("errorMessage"); %>
            <% } %>

            // 결과 저장
            $(document).on("click", ".save-button", function (event) {
                event.preventDefault();

                const currentData = {
                    interviewId: $("#interviewId").val(),
                    interviewTopic: $("#title").val(),
                    interviewSummary: $("#summary").val(),
                    reviewOfInterview: $("#feedback").val(),
                    reviewRating: $("#rating-input").val()
                };

                $.ajax({
                    url: "<%= request.getContextPath() %>/interview/result",
                    method: "POST",
                    data: {
                        action: "save", // 항상 save로 요청
                        interviewId: currentData.interviewId,
                        title: currentData.interviewTopic,
                        summary: currentData.interviewSummary,
                        feedback: currentData.reviewOfInterview,
                        rating: currentData.reviewRating
                    },
                    success: function (response) {
                        alert("면담 결과가 성공적으로 처리되었습니다.");
                        location.reload(); // 페이지를 새로고침
                    },
                    error: function () {
                        alert("데이터를 저장하는 중 오류가 발생했습니다.");
                    }
                });
            });

            // 화살표 버튼 클릭 시 동적 박스 추가/삭제
            $(".arrow-button").click(function () {
                if ($(".result-management").css("display") === "none") {
                    $(".result-management").css("display", "block");
                    $(".main-content").css("width", "40%");
                    $(this).text("◀");
                } else {
                    $(".result-management").css("display", "none");
                    $(".main-content").css("width", "63.5%");
                    $(this).text("▶");
                }
            });

            // 수정 버튼 클릭 시 폼 활성화
            $(".edit-button").click(function () {
                $("#title").prop("disabled", false);
                $("#summary").prop("disabled", false);
                $("#feedback").prop("disabled", false);
                $("#rating-input").prop("disabled", false);
                $(".rating").removeClass("disabled");
            });

            // 별점 클릭 이벤트
            $(".star").click(function () {
                var rating = $(this).data("value");
                $(".star").each(function () {
                    if ($(this).data("value") <= rating) {
                        $(this).addClass("selected");
                    } else {
                        $(this).removeClass("selected");
                    }
                });
                $("#rating-input").val(rating);
            });

            // 박스 클릭 시 데이터 불러오기 및 화살표 버튼 효과 호출
            $(".interview-box").click(function () {
                var interviewId = $(this).data("id");

                $.ajax({
                    url: "<%=request.getContextPath()%>/interview/result",
                    method: "GET",
                    data: {action: "getSavedData", interviewId: interviewId},
                    success: function (response) {
                        if (response.error) {
                            alert("데이터를 불러오는 중 문제가 발생했습니다.");
                        } else {
                            $("#interviewId").val(response.interviewId || "");
                            $("#title").val(response.interviewTopic || "");
                            $("#summary").val(response.interviewSummary || "");
                            $("#feedback").val(response.reviewOfInterview || "");
                            $(".star").each(function () {
                                if ($(this).data("value") <= (response.reviewRating || 0)) {
                                    $(this).addClass("selected");
                                } else {
                                    $(this).removeClass("selected");
                                }
                            });
                            $("#rating-input").val(response.reviewRating || 0);

                            // 폼 상태를 제어
                            if (response.interviewSummary) {
                                // 데이터가 있으면 폼 비활성화
                                $("#title").prop("disabled", true);
                                $("#summary").prop("disabled", true);
                                $("#feedback").prop("disabled", true);
                                $("#rating-input").prop("disabled", true);
                                $(".rating").addClass("disabled");
                            } else {
                                // 데이터가 없으면 폼 활성화
                                $("#title").prop("disabled", false);
                                $("#summary").prop("disabled", false);
                                $("#feedback").prop("disabled", false);
                                $("#rating-input").prop("disabled", false);
                                $(".rating").removeClass("disabled");
                            }
                        }
                    },
                    error: function () {
                        alert("데이터를 불러오는 중 문제가 발생했습니다.");
                    }
                });
            });
        });

    </script>
</head>
<body>
<!-- 헤더 -->
<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="meeting"/>
</jsp:include>

<div class="container">
    <!-- 왼쪽 사이드 메뉴 -->
    <jsp:include page="/WEB-INF/sideMenu.jsp">
        <jsp:param name="currentMenu" value="interviewResult"/>
    </jsp:include>

    <!-- 주요 컨텐츠 -->
    <main class="main-content">
        <!-- 상단 탭 -->
        <div class="tabs">
            <button class="tab-button selected">면담완료</button>
            <button class="tab-button">면담예정</button>
        </div>

        <!-- 면담 완료 리스트 -->
        <div class="interview-grid">
            <%
                List<Interview> completedInterviews = (List<Interview>) request.getAttribute("interviewList");
                List<Interview> notCompletedInterviews = (List<Interview>) request.getAttribute("notCompletedInterviews");

                if (completedInterviews != null && !completedInterviews.isEmpty()) {
                    for (Interview interview : completedInterviews) {
            %>
            <div class="interview-box" data-id="<%= interview.getInterviewId() %>">
                <h3><%= interview.getStudentName() %> 학생</h3>
                <p><%= interview.getRequestedDate() %></p>
            </div>
            <%
                    }
                }
            %>
        </div>
    </main>
</div>
</body>
</html>
