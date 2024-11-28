<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon">
    <link rel="stylesheet" href="<c:url value='/css/userForm.css' />">
</head>
<body>

    <!-- 상단 바 -->
    <div class="header-bar"></div>

    <!-- 회원가입 폼 -->
    <div class="registerForm" >
	    <form action="<c:url value='/user/register' />" method="post" onsubmit="return combineEmail();">
	        <!-- 회원가입 박스 -->
	        <div class="register-box">
	            <div class="register-title">회원가입을 해주세요</div>
	
	            <!-- 이름 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="name">이름</label>
	                <input type="text" id="name" name="name" placeholder="이름을 입력하세요" class="input-field">
	            </div>
	
	            <!-- 사용자 유형 선택 -->
	            <div class="input-group">
	                <label class="input-label">User Type</label>
	                <div class="radio-group">
	                    <label><input type="radio" name="userType" value="1" checked onchange="toggleUserTypeFields();"> 학생</label>
	                    <label><input type="radio" name="userType" value="2" onchange="toggleUserTypeFields();"> 교수</label>
	                </div>
	            </div>
	
	            <!-- 이메일 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="emailLocalPart">이메일</label>
	                <div class="email-group">
	                    <input type="text" id="emailLocalPart" name="emailLocalPart" placeholder="아이디를 입력하세요" class="input-field-email">
	                    <span class="email-domain">@dongduk.ac.kr</span>
	                    <!-- 숨겨진 전체 이메일 필드 -->
	                    <input type="hidden" id="email" name="email">
	                </div>
	                <button type="button" class="duplicate-check-button">중복확인</button>
	            </div>
	
	            <!-- 전화번호 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="phone">전화번호</label>
	                <input type="text" id="phone" name="phone" placeholder="010-0000-0000" class="input-field-phone">
	                <button type="button" class="request-auth-button">인증요청</button>
	                <input type="text" id="authCode" name="authCode" placeholder="인증번호" class="input-field-authcode">
	                <button type="button" class="auth-button">인증</button>
	            </div>
	
	            <!-- 비밀번호 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="password">비밀번호</label>
	                <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" class="input-field">
	            </div>
	
	            <!-- 비밀번호 확인 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="confirmPassword">비밀번호 확인</label>
	                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호를 다시 입력하세요" class="input-field">
	            </div>
	
	            <!-- 학과 정보 입력 필드 -->
	            <div class="input-group">
	                <label class="input-label" for="department">학과 정보</label>
	                <select id="department" name="department" class="input-field">
	                    <option value="">학과를 선택하세요</option>
	                    <option value="컴퓨터공학과">컴퓨터공학과</option>
	                    <option value="전자공학과">전자공학과</option>
	                    <!-- 추가 학과 옵션 -->
	                </select>
	            </div>
	
	            <!-- 학생 전용 필드 -->
	            <div id="studentFields">
	                <!-- 학번 입력 필드 -->
	                <div class="input-group">
	                    <label class="input-label" for="studentId">학번</label>
	                    <input type="text" id="studentId" name="studentId" placeholder="학번을 입력하세요" class="input-field">
	                </div>
	                <!-- 학년 입력 필드 -->
	                <div class="input-group">
	                    <label class="input-label" for="grade">학년</label>
	                    <input type="number" id="grade" name="grade" placeholder="학년을 입력하세요" class="input-field">
	                </div>
	            </div>
	
	            <!-- 교수 전용 필드 -->
	            <div id="professorFields" style="display:none;">
	                <!-- 교수 번호 입력 필드 -->
	                <div class="input-group">
	                    <label class="input-label" for="professorId">교수 번호</label>
	                    <input type="text" id="professorId" name="professorId" placeholder="교수 번호를 입력하세요" class="input-field">
	                </div>
	                <!-- 연구실 위치 입력 필드 -->
	                <div class="input-group">
	                    <label class="input-label" for="professorOffice">연구실 위치</label>
	                    <input type="text" id="professorOffice" name="professorOffice" placeholder="연구실 위치를 입력하세요" class="input-field">
	                </div>
	            </div>
	
	            <!-- 가입하기 버튼 -->
	            <div class="button-group">
	                <button type="submit" class="register-button">가입하기</button>
	            </div>
	        </div>
	    </form>
	</div>
	
	<!-- 자바스크립트 함수 추가 -->
    <script type="text/javascript">
        function combineEmail() {
            var emailLocalPart = document.getElementById("emailLocalPart").value.trim();
            var emailDomain = "@dongduk.ac.kr";
            if (emailLocalPart === "") {
                alert("이메일을 입력해주세요.");
                return false;
            }
            document.getElementById("email").value = emailLocalPart + emailDomain;
            return true;
        }

        function toggleUserTypeFields() {
            var userType = document.querySelector('input[name="userType"]:checked').value;
            if (userType === "1") { // 학생
                document.getElementById("studentFields").style.display = "block";
                document.getElementById("professorFields").style.display = "none";
            } else if (userType === "2") { // 교수
                document.getElementById("studentFields").style.display = "none";
                document.getElementById("professorFields").style.display = "block";
            }
        }

        window.onload = function() {
            toggleUserTypeFields();
        };
    </script>
<<<<<<< HEAD
=======
</head>
<body>

    <!-- 상단 바 -->
    <div class="header-bar"></div>

    <!-- 회원가입 폼 -->
    <form action="<c:url value='/user/login' />" method="post" class="registerForm" onsubmit="return combineEmail();">
        <!-- 회원가입 박스 -->
        <div class="register-box">
            <div class="register-title">회원가입을 해주세요</div>

            <!-- 이름 입력 필드 -->
            <div class="input-group">
                <label class="input-label" for="name">이름</label>
                <input type="text" id="name" name="name" placeholder="이름을 입력하세요" class="input-field">
            </div>

            <!-- 사용자 유형 선택 -->
            <div class="input-group">
                <label class="input-label">User Type</label>
                <div class="radio-group">
                    <label><input type="radio" name="userType" value="1" checked onchange="toggleUserTypeFields();"> 학생</label>
                    <label><input type="radio" name="userType" value="2" onchange="toggleUserTypeFields();"> 교수</label>
                </div>
            </div>

            <!-- 이메일 입력 필드 -->
            <div class="input-group">
                <label class="input-label" for="emailLocalPart">이메일</label>
                <div class="email-group">
                    <input type="text" id="emailLocalPart" name="emailLocalPart" placeholder="아이디를 입력하세요" class="input-field-email">
                    <span class="email-domain">@dongduk.ac.kr</span>
                    <!-- 숨겨진 전체 이메일 필드 -->
                    <input type="hidden" id="email" name="email">
                </div>
                <button type="button" class="duplicate-check-button">중복확인</button>
            </div>

            <!-- 전화번호 입력 필드 -->
            <div class="input-group">
                <label class="input-label" for="phone">전화번호</label>
                <input type="text" id="phone" name="phone" placeholder="010-0000-0000" class="input-field-phone">
                <button type="button" class="request-auth-button">인증요청</button>
                <input type="text" id="authCode" name="authCode" placeholder="인증번호" class="input-field-authcode">
                <button type="button" class="auth-button">인증</button>
            </div>

            <!-- 비밀번호 입력 필드 -->
            <div class="input-group">
                <label class="input-label" for="password">비밀번호</label>
                <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" class="input-field">
            </div>

            <!-- 비밀번호 확인 입력 필드 -->
            <div class="input-group">
                <label class="input-label" for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호를 다시 입력하세요" class="input-field">
            </div>

            <!-- 학과 정보 입력 필드 -->
            <div class="input-group">
                <label class="input-label" for="department">학과 정보</label>
                <select id="department" name="department" class="input-field">
                    <option value="">학과를 선택하세요</option>
                    <option value="컴퓨터공학과">컴퓨터공학과</option>
                    <option value="전자공학과">전자공학과</option>
                    <!-- 추가 학과 옵션 -->
                </select>
            </div>

            <!-- 학생 전용 필드 -->
            <div id="studentFields">
                <!-- 학번 입력 필드 -->
                <div class="input-group">
                    <label class="input-label" for="studentId">학번</label>
                    <input type="text" id="studentId" name="studentId" placeholder="학번을 입력하세요" class="input-field">
                </div>
                <!-- 학년 입력 필드 -->
                <div class="input-group">
                    <label class="input-label" for="grade">학년</label>
                    <input type="number" id="grade" name="grade" placeholder="학년을 입력하세요" class="input-field">
                </div>
            </div>

            <!-- 교수 전용 필드 -->
            <div id="professorFields" style="display:none;">
                <!-- 교수 번호 입력 필드 -->
                <div class="input-group">
                    <label class="input-label" for="professorId">교수 번호</label>
                    <input type="text" id="professorId" name="professorId" placeholder="교수 번호를 입력하세요" class="input-field">
                </div>
                <!-- 연구실 위치 입력 필드 -->
                <div class="input-group">
                    <label class="input-label" for="professorOffice">연구실 위치</label>
                    <input type="text" id="professorOffice" name="professorOffice" placeholder="연구실 위치를 입력하세요" class="input-field">
                </div>
            </div>

            <!-- 가입하기 버튼 -->
            <div class="button-group">
                <button type="submit" class="register-button">가입하기</button>
            </div>
        </div>
    </form>

>>>>>>> 240672737dc995d3e33800ccaf89d3ce185bb295
</body>
</html>
