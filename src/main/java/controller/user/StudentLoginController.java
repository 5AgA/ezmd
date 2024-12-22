package controller.user;

import controller.Controller;

import model.domain.Student;
import model.manager.user.StudentLoginManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/login/student")
public class StudentLoginController extends HttpServlet implements Controller {

	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 로그인 매니저에서 처리
        StudentLoginManager studentLoginManager = new StudentLoginManager();
        Student user;

        try {
            user = (Student) studentLoginManager.authenticate(email, password);
        } catch (SQLException e) {
            System.out.println("학생 로그인 중 오류 발생");
            e.printStackTrace();
            throw new ServletException("학생 로그인 처리 중 오류가 발생했습니다.", e);
        }

        // 로그인 결과에 따라 이동할 URL 반환
        if (user != null) {
            // 로그인 성공; 세션에 사용자 정보를 저장
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userType", "student");

            System.out.println("User saved in session: " + user);
            System.out.println("UserType saved in session: student");

            // 로그인 성공 시 홈으로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/home");
            return null;  // 리다이렉트 후 더 이상 실행되지 않도록 null 반환
        } else {
            // 로그인 실패
            request.setAttribute("errorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "form";
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // execute 메서드 호출 후 반환된 URL로 이동
            String view = execute(request, response);
            if (view != null && view.startsWith("redirect:")) {
                response.sendRedirect(view.substring("redirect:".length()));  // 리다이렉트 처리
            } else if (view != null) {
                request.getRequestDispatcher(view).forward(request, response);  // 포워딩 처리
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "학생 로그인 처리 중 오류가 발생했습니다.");
        }
    }
}
