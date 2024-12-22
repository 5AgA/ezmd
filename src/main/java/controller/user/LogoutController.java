package controller.user;

import controller.Controller;
import model.manager.user.LogoutManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet implements Controller {
	
	private LogoutManager logoutManager;
	
	 @Override
	    public void init() throws ServletException {
	        super.init();
	        logoutManager = new LogoutManager();
	    }

	    @Override
	    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        HttpSession session = request.getSession(false);
	        // LogoutManager를 통한 로그아웃 처리
	        logoutManager.logout(session);

	        // 로그아웃 후 이동할 페이지 (로그인 페이지)
	        return "/ezmd/login/form";
	    }

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            String view = execute(request, response);
	            response.sendRedirect(view);
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "로그아웃 처리 중 오류 발생");
	        }
	    }
}
