<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>일정 수정</title>
    <link rel="stylesheet" href="<c:url value='/css/editSchedule.css'/>">
</head>
<body>
<div class="editSchedule-modal" id="editSchedule-modal">
    <div class="modal-content">
        <form id="editSchedule-form">

            <input type="text" id="edit-title" name="scheduleTitle" placeholder="스케줄 이름을 입력하세요" required>

            <div class="edit-date">
                <label for="edit-sdate">시작일</label>&nbsp;&nbsp;
                <input type="datetime-local" id="edit-sdate" name="startDate" required><br><br>
                <label for="edit-edate">종료일</label>&nbsp;&nbsp;
                <input type="datetime-local" id="edit-edate" name="endDate" required><br>
            </div>

            <div class="edit-place">
                <img src="<c:url value='/images/place-icon.svg'/>"/>
                <input type="text" id="edit-place" name="schedulePlace" placeholder="장소를 입력하세요"><br>
            </div>

            <div class="edit-category">
                <p>일정 종류</p>
            </div>

            <div class="edit-memo">
                <label for="edit-memo">메모</label><br>
                <textarea id="edit-memo" name="scheduleMemo" placeholder="메모를 입력하세요"></textarea><br>
            </div>

            <div class="edit-button">
                <input type="button" id="delete" value="삭제"/>
                <button type="submit">수정</button>
            </div>
        </form>
    </div>
</div>
<script src="<c:url value='/js/editSchedule.js'/>"></script>
</body>
</html>
