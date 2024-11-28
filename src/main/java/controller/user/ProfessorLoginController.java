
package controller.user;

import model.domain.Professor;
import model.manager.ProfessorLoginManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login/professors")
public class ProfessorLoginController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//로그인 매니저에서 처리
		ProfessorLoginManager professorLoginManager = new ProfessorLoginManager();
		Object user = null;
		try {
			user = professorLoginManager.authenticate(email, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("교수님 로그인 중 오류발생");
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