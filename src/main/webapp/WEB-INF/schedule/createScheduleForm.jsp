<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>일정 추가</title>
    <link rel="stylesheet" href="<c:url value='/css/createSchedule.css'/>">
</head>
<body>
<div class="schedule-modal" id="schedule-modal">
    <div class="modal-content">
        <form id="schedule-form">

            <input type="text" id="title" name="scheduleTitle" placeholder="스케줄 이름을 입력하세요" required>

            <div class="date">
                <label for="sdate">시작일</label>&nbsp;&nbsp;
                <input type="datetime-local" id="sdate" name="startDate" required><br><br>
                <label for="edate">종료일</label>&nbsp;&nbsp;
                <input type="datetime-local" id="edate" name="endDate" required><br>
            </div>

            <div class="place">
                <img src="<c:url value='/images/place-icon.svg'/>"/>
                <input type="text" id="place" name="schedulePlace" placeholder="장소를 입력하세요"><br>
            </div>

            <div class="category">
                <p>일정 종류</p>
            </div>

            <div class="memo">
                <label for="memo">메모</label><br>
                <textarea id="memo" name="scheduleMemo" placeholder="메모를 입력하세요"></textarea><br>
            </div>

            <button type="submit">추가</button>
        </form>
    </div>
</div>
<script src="<c:url value='/js/createSchedule.js'/>"></script>
</body>
</html>
