
package controller.user;

import controller.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.ProfessorDAO;
import model.domain.Professor;
import model.manager.ProfessorSignupManager;

<<<<<<< HEAD
@WebServlet("/signup/professors")
=======
@WebServlet("/signup/professor")
>>>>>>> 442fcd4b9ce0b0d9d452df219ae786f538f98d71
public class ProfessorSignupController extends HttpServlet implements Controller {

    private ProfessorSignupManager professorSignupManager;

    public ProfessorSignupController() {
        professorSignupManager = new ProfessorSignupManager();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 요청 데이터 추출
            Integer professorId = Integer.parseInt(request.getParameter("professorId"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String dept = request.getParameter("dept");
            String office = request.getParameter("professorOffice");

            // Professor 객체 생성
            Professor professor = new Professor();
            professor.setProfessorId(professorId);
            professor.setName(name);
            professor.setEmail(email);
            professor.setPassword(password);
            professor.setPhone(phone);
            professor.setDept(dept);
            professor.setProfessorOffice(office);
            professor.setDeleted('N'); // 기본값 설정

            // 교수 회원가입 비즈니스 로직 호출
            boolean result = professorSignupManager.registerProfessor(professor);

            // 결과에 따른 이동 URL 반환
            if (result) {
                return "home.jsp"; // 회원가입 성공
            } else {
                request.setAttribute("errorMessage", "교수 회원가입 중 문제가 발생했습니다.");
                return "signup.jsp"; // 실패 시 회원가입 페이지로 이동
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("교수 회원가입 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // execute 메서드 호출 후 반환된 URL로 이동
            String view = execute(request, response);
            if (view.equals("home.jsp")) {
                response.sendRedirect(view);
            } else {
                request.getRequestDispatcher(view).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "회원가입 처리 중 오류가 발생했습니다.");
        }
    }
}