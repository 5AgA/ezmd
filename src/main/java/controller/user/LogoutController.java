package controller.user;

import model.manager.LogoutManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	
	private LogoutManager logoutManager;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        // 세션 가져오기
		logoutManager = new LogoutManager();
		logoutManager.logout(request.getSession());
		
        // 로그아웃 후 로그인 페이지로 리다이렉트
		response.sendRedirect("login.jsp");
		
	}
	
}
