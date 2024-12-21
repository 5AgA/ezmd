<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reservation.css">
    <title>면담 예약</title>
    <script src="<c:url value='/js/reservation.js'/>"></script>
</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="currentPage" value="meeting"/>
</jsp:include>

<%
    // 전달된 파라미터 추출
    String professorId = request.getParameter("professorId");
    String professorName = request.getParameter("professorName");
%>

<div class="reservation-page-wrapper">
    <div class="sidebar-wrapper">
        <div class="menu-item">면담 신청</div>
        <div class="menu-item">면담 관리</div>
    </div>
    <div class="main-content">
        <div class="header-wrapper">
            <div class="profile-wrapper">
                <div class="profile-image"></div>
                <span class="professor-name"><%= professorName %>  교수님</span>
            </div>
            <button class="review-button">면담 후기</button>
        </div>
        <div class="sub-content">
            <form action="<%=request.getContextPath()%>/interview-submit" method="post">
                <input type="hidden" name="selectedDate" id="selectedDate" value="">
                <input type="hidden" name="selectedTime" id="selectedTime" value="">
                <input type="hidden" name="professorId" id="professorId" value="<%= professorId %>">
                <div class="reservation-section">
                    <div class="calendar-wrapper">
                        <h3 class="calendar-title">면담 날짜 선택</h3>
                        <div class="calendar-header">
                            <button type="button"><</button>
                            <span class="year-month">2024년 12월</span>
                            <button type="button">></button>
                        </div>
                        <table class="calendar-table">
                            <thead>
                            <tr>
                                <th>일</th>
                                <th>월</th>
                                <th>화</th>
                                <th>수</th>
                                <th>목</th>
                                <th>금</th>
                                <th>토</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>1</td>
                                <td>2</td>
                                <td>3</td>
                                <td>4</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>7</td>
                                <td>8</td>
                                <td>9</td>
                                <td>10</td>
                                <td>11</td>
                                <td>12</td>
                            </tr>
                            <tr>
                                <td>13</td>
                                <td>14</td>
                                <td>15</td>
                                <td>16</td>
                                <td>17</td>
                                <td>18</td>
                                <td>19</td>
                            </tr>
                            <tr>
                                <td>20</td>
                                <td>21</td>
                                <td>22</td>
                                <td>23</td>
                                <td>24</td>
                                <td>25</td>
                                <td>26</td>
                            </tr>
                            <tr>
                                <td>27</td>
                                <td>28</td>
                                <td>29</td>
                                <td>30</td>
                                <td>31</td>
                                <td></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="time-selector-wrapper">
                        <h3 class="time-selector-title">면담 시간 선택</h3>
                        <div class="time-grid">
                            <button type="button" class="time-button">09:00</button>
                            <button type="button" class="time-button">09:30</button>
                            <button type="button" class="time-button">10:00</button>
                            <button type="button" class="time-button">10:30</button>
                            <button type="button" class="time-button">11:00</button>
                            <button type="button" class="time-button">11:30</button>
                            <button type="button" class="time-button">12:00</button>
                            <button type="button" class="time-button">12:30</button>
                            <button type="button" class="time-button">13:00</button>
                            <button type="button" class="time-button">13:30</button>
                            <button type="button" class="time-button">14:00</button>
                            <button type="button" class="time-button">14:30</button>
                            <button type="button" class="time-button">15:00</button>
                            <button type="button" class="time-button">15:30</button>
                            <button type="button" class="time-button">16:00</button>
                            <button type="button" class="time-button">16:30</button>
                            <button type="button" class="time-button">17:00</button>
                            <button type="button" class="time-button">17:30</button>
                            <button type="button" class="time-button">18:00</button>
                        </div>
                    </div>

                    <div class="reserve-button-wrapper">
                        <button class="reserve-button-styled" type="submit">예약 진행하기

                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
