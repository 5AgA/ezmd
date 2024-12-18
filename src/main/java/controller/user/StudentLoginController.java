package controller.user;

import controller.Controller;

import model.domain.Student;
import model.manager.StudentLoginManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("userType", "student");
            return "redirect:/home";
        } else {
            // 로그인 실패
            request.setAttribute("errorMessage", "Invalid credentials");
            return "login.jsp";
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
                request.getRequestDispatcher(view).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "학생 로그인 처리 중 오류가 발생했습니다.");
        }
    }
	
}
