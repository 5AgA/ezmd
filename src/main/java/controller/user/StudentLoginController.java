package controller.user;

import model.domain.Student;
import model.manager.StudentLoginManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login/students")
public class StudentLoginController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//로그인 매니저에서 처리
		StudentLoginManager studentLoginManager = new StudentLoginManager();
		Object user = null;
		try {
			user = studentLoginManager.authenticate(email, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("학생 로그인 중 오류발생");
			e.printStackTrace();
		}
		
		if(user != null) {
			//로그인 성공; 세션에 사용자 정보를 저장
			request.getSession().setAttribute("user", user);
			response.sendRedirect("home.jsp");
		} else {
			//로그인 실패
			request.setAttribute("errorMessage", "Invalid credentials");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
}
