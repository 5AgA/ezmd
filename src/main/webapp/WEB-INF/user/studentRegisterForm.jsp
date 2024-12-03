<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 회원가입</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
    <div class="header-bar"></div>
   
    <div class="registerForm">
        <form action="<c:url value='/signup/student' />" method="post">
            <div class="register-box">
                <div class="register-title">학생 회원가입</div>

                <!-- 이름 -->
                <div class="input-group">
                    <label class="input-label" for="name">이름</label>
                    <input type="text" id="name" name="name" placeholder="이름을 입력하세요" class="input-field" required>
                </div>

                <!-- 이메일 -->
                <div class="input-group email-group">
                    <label class="input-label" for="email">이메일</label>
                    <input type="email" id="email" name="email" placeholder="example@dongduk.ac.kr" class="input-field" required>
                	<button type="button" class="duplicate-check-button" onclick="checkEmail()">중복확인</button>
                    <span id="emailError" style="color:red;"></span>
                    <span id="duplicateMessage" style="color:red;"></span>
                </div>

                <!-- 비밀번호 -->
                <div class="input-group">
                    <div class="password-wrapper">
                        <label class="input-label" for="password">비밀번호</label>
                        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" class="input-field" required>
                    </div>
                    <div class="password-wrapper">
                        <label class="input-label" for="confirm-password">비밀번호 확인</label>
                        <input type="password" id="confirm-password" name="confirm-password" placeholder="비밀번호 확인" class="input-field" required>
                    </div>
				</div>

				<!-- 학번 -->
                <div class="input-group">
                    <label class="input-label" for="studentId">학번</label>
                    <input type="text" id="studentId" name="studentId" placeholder="학번을 입력하세요" class="input-field" required>
                </div>
                
                <!-- 학과 -->
                <div class="input-group">
                    <label class="input-label" for="dept">학과</label>
                    <input type="text" id="dept" name="dept" placeholder="학과를 입력하세요" class="input-field" required>
                </div>

                <!-- 학년 -->
                <div class="input-group">
                    <label class="input-label" for="grade">학년</label>
                    <input type="number" id="grade" name="grade" placeholder="학년을 입력하세요" class="input-field" required>
                </div>

                <!-- 가입하기 버튼 -->
                <div class="button-group">
                    <button type="submit" class="register-button">가입하기</button>
                </div>
            </div>
        </form>
    </div>
    <script>
        function checkEmail() {
            const email = document.getElementById('email').value.trim();
            const emailError = document.getElementById('emailError');
            const duplicateMessage = document.getElementById('duplicateMessage');
            emailError.textContent = '';
            duplicateMessage.textContent = '';

            // 정규식 검증: 기본 형식 + @dongduk.ac.kr 도메인 확인
            const emailRegex = /^[^\s@]+@dongduk\.ac\.kr$/;

            if (!email){
                emailError.textContent = '이메일을 입력해주세요.';
                return;
            }

            if (!emailRegex.test(email)){
                emailError.textContent = '유효한 @dongduk.ac.kr 이메일을 입력해주세요.';
                return;
            }

            //AJAX 요청으로 중복 확인 처리
            fetch('<%= request.getContextPath() %>/checkEmail',{
                method: 'POST',
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'email=' + encodeURIComponent(email)
            })
            .then(response => response.json())
            .then(data => {
                if (data.exists){
                    duplicateMessage.style.color = 'red';
                    duplicateMessage.textContent = '이미 사용 중인 이메일입니다.';
                } else{
                    duplicateMessage.style.color = 'green';
                    duplicateMessage.textContent = '사용 가능한 이메일입니다.';
                }
            })
            .catch(error =>{
                console.error('Error:', error);
                alert('중복 확인 중 오류가 발생했습니다.');
            });
        }
    </script>
</body>
</html>
