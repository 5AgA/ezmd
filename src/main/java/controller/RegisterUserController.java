package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 공통 필드 가져오기
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        // 사용자 유형에 따라 처리 분기
        if ("1".equals(userType)) { // 학생
            String studentId = request.getParameter("studentId");
            String grade = request.getParameter("grade");

            // DB 저장 로직 (예시)
            System.out.println("학생 가입: " + name + ", " + email + ", " + studentId + ", " + grade);

            // 성공 메시지 설정
            request.setAttribute("message", "학생 회원가입 성공! " + name + "님, 환영합니다.");
        } else if ("2".equals(userType)) { // 교수
            String professorId = request.getParameter("professorId");
            String professorOffice = request.getParameter("professorOffice");

            // DB 저장 로직 (예시)
            System.out.println("교수 가입: " + name + ", " + email + ", " + professorId + ", " + professorOffice);

            // 성공 메시지 설정
            request.setAttribute("message", "교수 회원가입 성공! " + name + "님, 환영합니다.");
        }

        // 성공 페이지로 이동
        return "/user/registerSuccess.jsp";
    }
}
