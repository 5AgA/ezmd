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
            <label for="colorPicker">Choose a color:</label>
            <input type="color" id="colorPicker" name="categoryColor" value="#ff0000" required>
            <button type="submit">추가</button>
        </form>
    </div>
</div>
</body>
</html>