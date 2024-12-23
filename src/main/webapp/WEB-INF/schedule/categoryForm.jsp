<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>일정 추가</title>
    <link rel="stylesheet" href="<c:url value='/css/categoryForm.css'/>">
    <script src="<c:url value='/js/categoryForm.js'/>"></script>
</head>
<body>
<div class="category-form-modal">
    <div class="category-form-modal-content">
        <form id="category-form">
            <input type="text" id="category-name" name="categoryName" placeholder="카테고리 이름을 입력하세요" required>
            <div class="colorPick">
                <label for="category-color">카테고리 테마 색상</label>
                <input type="color" id="category-color" name="categoryColor" value="#ffffff" required>
            </div>
            <button type="submit">추가</button>
        </form>
    </div>
</div>
</body>
</html>