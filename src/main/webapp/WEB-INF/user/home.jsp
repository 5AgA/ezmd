<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <title>홈</title>
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
</head>
<body>
    <jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="currentPage" value="home" />
	</jsp:include>

    <main class="main">
        <aside class="sidebar">
            <div class="profile">
                <img src="<%= request.getContextPath() %>/images/profile-icon.png" alt="Profile">
                <div class="profile-info">
                    <p>김동덕</p>
                    <p>동덕여자대학교</p>
                    <p>컴퓨터학과 22학번 3학년</p>
                </div>
            </div>

            <div class="pending-meetings">
                <h3>승인 대기 중인 면담</h3>
                <ul>
                    <li>9/27 15:00 박창섭 교수님</li>
                    <li>10/10 18:00 박수희 교수님</li>
                </ul>
            </div>

            <div class="scheduled-meetings">
                <h3>예정된 면담</h3>
                <ul>
                    <li>10/2 10:00 이완연 교수님</li>
                    <li>10/5 18:00 한혁 교수님</li>
                </ul>
            </div>
        </aside>

        <section class="calendar">
            <h2>2024년 9월</h2>
            <div class="calendar-controls">
                <button class="prev-month">&lt;</button>
                <button class="next-month">&gt;</button>
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
                    <!-- Example dates -->
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>5</td>
                        <td>6</td>
                        <td>7</td>
                        <td>8</td>
                        <td class="highlight">9</td>
                        <td>10</td>
                    </tr>
                    <!-- Add more rows as necessary -->
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>
