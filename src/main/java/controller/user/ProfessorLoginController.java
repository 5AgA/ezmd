
package controller.user;

import controller.Controller;
import model.domain.Professor;
import model.manager.user.ProfessorLoginManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login/professor")
public class ProfessorLoginController extends HttpServlet implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 로그인 매니저에서 처리
        ProfessorLoginManager professorLoginManager = new ProfessorLoginManager();
        Professor user;

        try {
            user = professorLoginManager.authenticate(email, password);
        } catch (SQLException e) {
            System.out.println("교수님 로그인 중 오류 발생");
            e.printStackTrace();
            throw new ServletException("로그인 처리 중 오류 발생", e);
        }

        if (user != null) {
            // 로그인 성공; 세션에 사용자 정보를 저장
        	HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userType", "professor");
            session.setAttribute("userId", user.getProfessorId());

            // 로그인 성공 시 홈으로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/home");
            return null;  // 리다이렉트 후 더 이상 실행되지 않도록 null 반환
        } else {
            // 로그인 실패 시 로그인 페이지로 이동하며 에러 메시지 설정
            request.setAttribute("errorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "form";
        }
   }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		  try {
	            // execute 메서드를 호출하여 반환된 URL로 리다이렉트 또는 포워드
	            String view = execute(request, response);
	            if (view!=null && view.startsWith("redirect:")) {
                    response.sendRedirect(view.substring("redirect:".length()));
	            } else if(view!=null){
	                request.getRequestDispatcher(view).forward(request, response);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "로그인 처리 중 오류 발생");
	        }
	}
	
}