
package controller.user;

import controller.Controller;

import model.domain.Professor;
import model.manager.user.ProfessorSignupManager;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/signup/professor")
public class ProfessorSignupController extends HttpServlet implements Controller {

    private final ProfessorSignupManager professorSignupManager;

    public ProfessorSignupController() {
        professorSignupManager = new ProfessorSignupManager();
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("professorRegisterForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("회원가입 폼 로딩 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * POST 요청 처리 - 회원가입 데이터 처리
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // CSRF 토큰 검증
			/*
			 * String sessionCsrfToken = (String)
			 * request.getSession().getAttribute("csrfToken"); String formCsrfToken =
			 * request.getParameter("csrfToken");
			 * 
			 * // System.out.println("Session CSRF Token: " + sessionCsrfToken); //
			 * System.out.println("Form CSRF Token: " + formCsrfToken);
			 * 
			 * if (sessionCsrfToken == null || !sessionCsrfToken.equals(formCsrfToken)) {
			 * response.sendError(HttpServletResponse.SC_FORBIDDEN, "유효하지 않은 CSRF 토큰입니다.");
			 * return; }
			 * 
			 * // CSRF 토큰을 세션에서 제거하여 재사용 방지
			 * request.getSession().removeAttribute("csrfToken");
			 */
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
		/*
		 * String professorIdStr = request.getParameter("professorId").trim();
		 */        
    	String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password").trim();
        String dept = request.getParameter("dept").trim();
        String professorOffice = request.getParameter("professorOffice").trim();

        // 서버 측 유효성 검사
        String errorMessage = null;
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty()||
                dept.isEmpty() || professorOffice.isEmpty()) {
            errorMessage = "모든 필드를 올바르게 입력해주세요.";
        } else if (!password.equals(confirmPassword)) {
            errorMessage = "비밀번호가 일치하지 않습니다.";
        } else if (!email.matches("^[^\\s@]+@dongduk\\.ac\\.kr$")) {
            errorMessage = "유효한 @dongduk.ac.kr 이메일을 입력해주세요.";
        }

        // 이메일 중복 확인
        if (errorMessage == null) {
            try {
                if (professorSignupManager.checkEmailExists(email)) {
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
            return "professorRegisterForm.jsp";
        }

        // Professor 객체 생성
        Professor professor = new Professor();
		/*
		 * try { int professorId = Integer.parseInt(professorIdStr);
		 * professor.setProfessorId(professorId); } catch (NumberFormatException e) {
		 * errorMessage = "교수 ID는 숫자여야 합니다."; request.setAttribute("errorMessage",
		 * errorMessage); return "professorRegisterForm.jsp";
		 }*/
        professor.setName(name);
        professor.setEmail(email);
        professor.setPassword(password); // 비밀번호는 Manager에서 해싱
        professor.setDept(dept);
        professor.setProfessorOffice(professorOffice);
        professor.setDeleted('N'); // 기본값 설정

        // 교수 회원가입 비즈니스 로직 호출
        boolean result = false;
        try {
            result = professorSignupManager.registerProfessor(professor);
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage = "교수 회원가입 중 오류가 발생했습니다.";
            request.setAttribute("errorMessage", errorMessage);
            return "professorRegisterForm.jsp";
        }

        if (result) {
            return "redirect:/ezmd/login/form?signupSuccess=true"; // 회원가입 성공 후 로그인 페이지로 이동
        } else {
            request.setAttribute("errorMessage", "교수 회원가입 중 문제가 발생했습니다.");
            return "/ezmd/register/form/prof"; // 실패 시 회원가입 페이지로 이동
        }
    }
}