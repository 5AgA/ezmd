<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>교수 회원가입</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
    <div class="header-bar"></div>
    
    <div class="registerForm">
        <form name="form" id="professorRegisterForm" action="<c:url value='/signup/professor' />" method="post">
            <div class="register-box">
                <div class="register-title">교수 회원가입</div>

                <!-- CSRF 토큰 -->
                <input type="hidden" name="csrfToken" value="${csrfToken}">


                <!-- 이름 -->
                <div class="input-group">
                    <label class="input-label" for="name">이름</label>
                    <input type="text" id="name" name="name" placeholder="이름을 입력하세요" class="input-field" required>
                </div>

                <!-- 이메일 -->
                <div class="input-group email-group">
                    <label class="input-label" for="email">이메일</label>
                    <input type="email" id="email" name="email" placeholder="example@dongduk.ac.kr" class="input-field" required>
                	<button type="button" class="duplicate-check-button" onclick="checkEmail()" disabled>중복확인</button>
                    <span id="emailError" style="color:red;"></span>
                    <span id="duplicateMessage" style="color:red;"></span>
                    <span id="loadingMessage" style="color:blue; display:none;"></span>
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

				<!-- 교수 번호 -->
                <div class="input-group">
                    <label class="input-label" for="professorId">교수 번호</label>
                    <input type="text" id="professorId" name="professorId" placeholder="교수 번호를 입력하세요" class="input-field" required>
                </div>
                
                <!-- 학과 -->
                <div class="input-group">
                    <label class="input-label" for="dept">학과</label>
                    <input type="text" id="dept" name="dept" placeholder="학과를 입력하세요" class="input-field" required>
                </div>

                <!-- 연구실 위치 -->
                <div class="input-group">
                    <label class="input-label" for="professorOffice">연구실 위치</label>
                    <input type="text" id="professorOffice" name="professorOffice" placeholder="연구실 위치를 입력하세요" class="input-field" required>
                </div>

                <!-- 가입하기 버튼 -->
                <div class="button-group">
                    <button type="submit" class="register-button">가입하기</button>
                </div>
            </div>
        </form>
    </div>
    <script>
        // 이메일 정규식: @dongduk.ac.kr 도메인 확인
        const emailRegex = /^[^\s@]+@dongduk\.ac\.kr$/;

        // 이메일 중복 확인 버튼 및 관련 요소 가져오기
        const emailInput = document.getElementById('email');
        const duplicateCheckBtn = document.querySelector('.duplicate-check-button');
        const emailError = document.getElementById('emailError');
        const duplicateMessage = document.getElementById('duplicateMessage');
        const loadingMessage = document.getElementById('loadingMessage');

        // 폼 요소 가져오기
        const professorRegisterForm = document.getElementById('professorRegisterForm');

        // 이메일 입력 시 중복 확인 버튼 활성화 및 에러 메시지 초기화
        emailInput.addEventListener('input', function() {
            const email = this.value.trim();
            console.log('입력된 이메일: ',email);
            emailError.textContent = '';
            duplicateMessage.textContent = '';
            loadingMessage.style.display = 'none';

            // 이전에 추가된 valid/invalid 클래스 제거
            duplicateCheckBtn.classList.remove('valid', 'invalid');

            if (emailRegex.test(email)) {
                console.log('이메일이 정규식과 일치합니다. 버튼 활성화.');
                duplicateCheckBtn.disabled = false;
            } else {
                console.log('이메일이 정규식과 일치하지 않습니다. 버튼 비활성화.');
                duplicateCheckBtn.disabled = true;
                if (email.length > 0) {
                    emailError.textContent = '유효한 @dongduk.ac.kr 이메일을 입력해주세요.';
                }
            }
        });

        // 폼 제출 시 검증
        professorRegisterForm.addEventListener('submit', function(event) {
            let valid = true;
            const name = this.name.value.trim();
            const email = this.email.value.trim();
            const password = this.password.value;
            const confirmPassword = this['confirm-password'].value;
            const professorId = this.professorId.value.trim();
            const dept = this.dept.value.trim();
            const professorOffice = this.professorOffice.value.trim();

            // 이름 검증
            if (name === "") {
                alert("이름을 입력해주세요.");
                this.name.focus();
                valid = false;
            }

            // 이메일 검증
            if (!emailRegex.test(email)) {
                alert("이메일 형식이 올바르지 않습니다.");
                this.email.focus();
                valid = false;
            }

            // 비밀번호 검증
            if (password.length < 8) {
                alert("비밀번호는 최소 8자 이상이어야 합니다.");
                this.password.focus();
                valid = false;
            }

            if (password !== confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                this['confirm-password'].focus();
                valid = false;
            }

            // 교수 번호 검증
            if (professorId === "") {
                alert("교수 번호를 입력해주세요.");
                this.professorId.focus();
                valid = false;
            }

            // 학과 검증
            if (dept === "") {
                alert("학과를 입력해주세요.");
                this.dept.focus();
                valid = false;
            }

            // 연구실 위치 검증
            if (professorOffice === "") {
                alert("연구실 위치를 입력해주세요.");
                this.professorOffice.focus();
                valid = false;
            }

            // 이메일 중복 확인 여부 검증
            if (duplicateMessage.textContent === '이미 사용 중인 이메일입니다.') {
                alert('이미 사용 중인 이메일입니다. 다른 이메일을 사용해주세요.');
                this.email.focus();
                valid = false;
            }

            if (duplicateMessage.textContent === '') {
                alert('이메일 중복 확인을 해주세요.');
                valid = false;
            }

            if (!valid) {
                event.preventDefault();
            }
        });

        // 이메일 중복 확인 함수
        function checkEmail() {
            const email = this.value.textContent;
            emailError.textContent = '';
            duplicateMessage.textContent = '';
            loadingMessage.textContent = ' 확인 중...';
            loadingMessage.style.display = 'inline';

            // 이메일 형식 검증
            if (!emailRegex.test(email)) {
                emailError.textContent = '유효한 @dongduk.ac.kr 이메일을 입력해주세요.';
                loadingMessage.style.display = 'none';
                return;
            }

            // AJAX 요청으로 중복 확인 처리
            fetch('<c:url value="/checkEmail" />', {
                method: 'POST',
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'email=' + encodeURIComponent(email)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('네트워크 응답에 문제가 있습니다.');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(`중복 확인 응답: ${JSON.stringify(data)}`); // 디버깅 로그
                    if (data.exists){
                        duplicateMessage.style.color = 'red';
                        duplicateMessage.textContent = data.message || '이미 사용 중인 이메일입니다.';
                        duplicateCheckBtn.classList.remove('valid');
                        duplicateCheckBtn.classList.add('invalid');
                    } else{
                        duplicateMessage.style.color = 'green';
                        duplicateMessage.textContent = '사용 가능한 이메일입니다.';
                        duplicateCheckBtn.classList.remove('invalid');
                        duplicateCheckBtn.classList.add('valid');
                    }
                    loadingMessage.style.display = 'none';
                })
                .catch(error =>{
                    console.error('Error:', error);
                    alert('중복 확인 중 오류가 발생했습니다.');
                    loadingMessage.style.display = 'none';
                });
        }
    </script>
</body>
</html>
