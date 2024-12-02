<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 회원가입</title>
    <link rel="stylesheet" href="<c:url value='/css/userForm.css' />">
</head>
<body>
    <div class="header-bar"></div>
    <div class="registerForm">
        <form action="<c:url value='/signup/student' />" method="post">
            <div class="register-box">
                <div class="register-title">학생 회원가입</div>

                <!-- 학번 -->
                <div class="input-group">
                    <label for="studentId">학번</label>
                    <input type="text" id="studentId" name="studentId" placeholder="학번을 입력하세요" required>
                </div>

                <!-- 이름 -->
                <div class="input-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required>
                </div>

                <!-- 이메일 -->
                <div class="input-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email" placeholder="example@dongduk.ac.kr" required>
                </div>

                <!-- 비밀번호 -->
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
                </div>

                <!-- 전화번호 -->
                <div class="input-group">
                    <label for="phone">전화번호</label>
                    <input type="text" id="phone" name="phone" placeholder="010-0000-0000" required>
                </div>

                <!-- 학과 -->
                <div class="input-group">
                    <label for="dept">학과</label>
                    <input type="text" id="dept" name="dept" placeholder="학과를 입력하세요" required>
                </div>

                <!-- 학년 -->
                <div class="input-group">
                    <label for="grade">학년</label>
                    <input type="number" id="grade" name="grade" placeholder="학년을 입력하세요" required>
                </div>

                <!-- 가입하기 버튼 -->
                <div class="button-group">
                    <button type="submit" class="register-button">가입하기</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
