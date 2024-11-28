package controller.user;

import controller.Controller;
import model.manager.LogoutManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet implements Controller {
	
	private LogoutManager logoutManager;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 // 로그아웃 처리
        logoutManager = new LogoutManager();
        logoutManager.logout(request.getSession());

        // 로그아웃 후 이동할 URL 반환
        return "login.jsp";
    }

    // doGet 호출 시 execute 실행
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // execute 메서드를 호출하여 반환된 URL로 리다이렉트
            String view = execute(request, response);
            response.sendRedirect(view);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "로그아웃 처리 중 오류 발생");
        }
    }
}
