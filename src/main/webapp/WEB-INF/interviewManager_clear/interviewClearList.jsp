<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.domain.Interview" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css"
          href="<%= request.getContextPath() %>/css/interviewClearList.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            let originalData = {}; // 초기 데이터를 저장할 객체

            var tabType = new URLSearchParams(window.location.search).get('tabType');  // URL에서 tabType 값 추출
            if (!tabType) {
                tabType = "completed";  // 기본값은 "completed"
            }

            // 탭 버튼 초기화
            $(".tab-button").removeClass("selected");

            // tabType 값에 맞는 버튼에 selected 클래스 추가
            if (tabType === "notCompleted") {
                $(".tab-button:contains('면담예정')").addClass("selected");
            } else {
                $(".tab-button:contains('면담완료')").addClass("selected");
            }

            // 면담완료/면담예정 탭 클릭 시 상태 변경
            $(".tab-button").click(function () {
                var tabType = $(this).text(); // 버튼 텍스트에 따라 면담 완료 또는 면담 예정 선택

                // tabType 값을 hidden 필드에 저장
                $("#tabType").val(tabType === "면담예정" ? "notCompleted" : "completed");

                // 새로 고침을 유도하면서 tabType을 URL에 전달
                var currentUrl = window.location.href.split('?')[0];  // 기존 URL에서 쿼리 스트링을 제외한 부분만 가져오기
                window.location.href = currentUrl + "?tabType=" + $("#tabType").val();

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
            <input type="hidden" id="tabType" name="tabType" value="completed"/>
            <button class="tab-button selected">면담완료</button>
            <button class="tab-button">면담예정</button>
        </div>


        <!-- 면담 완료 리스트 -->
        <div class="interview-grid">
            <%
                List<Interview> interviewList = new ArrayList<>();
                String tabType = request.getParameter("tabType");
                if (tabType == null) {
                    tabType = "completed";
                }

                System.out.println("tabType: " + tabType);

                if ("completed".equals(tabType)) {
                    interviewList = (List<Interview>) request.getAttribute("interviewList");
                } else if ("notCompleted".equals(tabType)) {
                    interviewList = (List<Interview>) request.getAttribute("notCompletedInterviewList");
                }

                System.out.println("interviewList: " + interviewList);

                String viewType = (String) request.getAttribute("viewType"); // professor 또는 student
                if (interviewList != null && !interviewList.isEmpty()) {
                    for (Interview interview : interviewList) {
            %>
            <div class="interview-box" data-id="<%=interview.getInterviewId()%>">
                <h3>
                    <%
                        if ("professor".equalsIgnoreCase(viewType)) {
                            out.print(interview.getProfessorName() + " 교수님");
                            if (interview.getInterviewStatus() != null)
                                out.print("(" + interview.getInterviewStatus() +")");
                        } else {
                            out.print(interview.getStudentName() + " 학생");
                        }
                    %>
                </h3>
                <p><%=interview.getRequestedDate().toLocalDate()%>
                </p>
                <p><%=interview.getRequestedDate().toLocalTime()%>
                </p>
            </div>
            <%
                }
            } else {
            %>
            <div class="no-data">
                완료된 면담이 없습니다.
            </div>
            <%
                }
            %>
        </div>
    </main>

    <!-- 오른쪽 화살표 버튼 -->
    <c:if test="${viewType == 'student'}">
        <!-- 교수 계정인 경우 화살표 버튼과 결과 관리 섹션을 렌더링하지 않습니다 -->
    </c:if>
    <c:if test="${viewType == 'professor'}">
        <div class="arrow-container">
            <button class="arrow-button">▶</button>
        </div>


        <div class="result-management">
            <div class="form-container">
                <form method="post" action="<%= request.getContextPath() %>/interview/result">
                    <input type="hidden" id="interviewId" name="interviewId" value=""/> <!-- hidden 필드 -->
                    <label for="title">면담 주제</label>
                    <input type="text" id="title" name="title" required>

                    <label for="summary">면담 요약</label>
                    <textarea id="summary" name="summary" required></textarea>

                    <label for="rating">별점</label>
                    <div id="rating">
                        <span class="star" data-value="1">★</span>
                        <span class="star" data-value="2">★</span>
                        <span class="star" data-value="3">★</span>
                        <span class="star" data-value="4">★</span>
                        <span class="star" data-value="5">★</span>
                        <input type="hidden" id="rating-input" name="rating" value="3">
                    </div>

                    <label for="feedback">상담 후기</label>
                    <textarea id="feedback" name="feedback" required></textarea>

                    <div class="button-wrapper">
                        <button type="button" class="edit-button">수정</button>
                        <button type="submit" class="save-button">저장</button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
