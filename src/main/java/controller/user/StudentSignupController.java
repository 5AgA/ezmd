
package controller.user;


import controller.Controller;

import model.domain.Student;
import model.manager.user.StudentSignupManager;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/signup/student")
public class StudentSignupController extends HttpServlet implements Controller {

    private final StudentSignupManager studentSignupManager;

    public StudentSignupController() {
        studentSignupManager = new StudentSignupManager();
    }

    /**
     * GET 요청 처리 - 회원가입 폼 제공
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			/*
			 * // CSRF 토큰 생성 String csrfToken = java.util.UUID.randomUUID().toString();
			 * HttpSession session = request.getSession(); session.setAttribute("csrfToken",
			 * csrfToken); request.setAttribute("csrfToken", csrfToken);
			 */
            // 회원가입 폼으로 포워딩
            RequestDispatcher dispatcher = request.getRequestDispatcher("studentRegisterForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("회원가입 폼 로딩 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            // execute 메서드 호출 후 반환된 URL로 이동
            String view = execute(request, response);
            if (view.startsWith("redirect:")) {
                response.sendRedirect(view.substring("redirect:".length()));
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(view);
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "회원가입 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * 비즈니스 로직 수행 및 결과에 따른 뷰 결정
     * @param request 클라이언트 요청
     * @param response 클라이언트 응답
     * @return 이동할 뷰의 경로
     * @throws Exception
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 데이터 추출
        String studentIdStr = request.getParameter("studentId").trim();
        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password").trim();
        String dept = request.getParameter("dept").trim();
        String gradeStr = request.getParameter("grade").trim();

        // 서버 측 유효성 검사
        String errorMessage = null;
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || studentIdStr.isEmpty() ||
                dept.isEmpty() || gradeStr.isEmpty()) {
            errorMessage = "모든 필드를 올바르게 입력해주세요.";
        } else if (!password.equals(confirmPassword)) {
            errorMessage = "비밀번호가 일치하지 않습니다.";
        } else if (!email.matches("^[^\\s@]+@dongduk\\.ac\\.kr$")) {
            errorMessage = "유효한 @dongduk.ac.kr 이메일을 입력해주세요.";
        }

        // 이메일 중복 확인
        if (errorMessage == null) {
            try {
                if (studentSignupManager.checkEmailExists(email)) {
                    errorMessage = "이미 사용 중인 이메일입니다.";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage = "이메일 중복 확인 중 오류가 발생했습니다.";
            }
        }

        if (errorMessage != null) {
            // 에러 메시지를 요청에 저장하고 폼으로 다시 포워딩

            request.setAttribute("errorMessage", errorMessage);
            return "studentRegisterForm.jsp";
        }

        // Student 객체 생성
        Student student = new Student();
        try {
            int studentId = Integer.parseInt(studentIdStr);
          
            student.setStudentId(studentId);
        } catch (NumberFormatException e) {
            errorMessage = "학번은 숫자여야 합니다.";
            request.setAttribute("errorMessage", errorMessage);
            return "studentRegisterForm.jsp";
        }
        
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password); // 비밀번호는 Manager에서 해싱
        student.setDept(dept);
        student.setGrade(Integer.valueOf(gradeStr));
        student.setDeleted('N'); // 기본값 설정

        // 학생 회원가입 비즈니스 로직 호출
        boolean result = false;
        try {
            result = studentSignupManager.registerStudent(student);
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage = "학생 회원가입 중 오류가 발생했습니다.";
            request.setAttribute("errorMessage", errorMessage);
            return "studentRegisterForm.jsp";
        }

        if (result) {
            return "redirect:/login/form?signupSuccess=true";
        } else {
            request.setAttribute("errorMessage", "학생 회원가입 중 문제가 발생했습니다.");
            return "/register/form/stud"; // 실패 시 회원가입 페이지로 이동
        }
    }
}