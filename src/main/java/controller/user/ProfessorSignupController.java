
package controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.ProfessorDAO;
import model.domain.Professor;
import model.manager.ProfessorSignupManager;

@WebServlet("/signup/professors")
public class ProfessorSignupController extends HttpServlet {
	
	private ProfessorSignupManager professorSignupManager;
	
	public ProfessorSignupController() {
		professorSignupManager = new ProfessorSignupManager();
	}

	
	//교수 회원가입
	 public void signupProfessor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//요청 데이터 추출
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
            
            //교수 회원가입 비즈니스 로직 호출
            boolean result = professorSignupManager.registerProfessor(professor);
            
            //응답작성
            if(result) {
            	response.sendRedirect("home.jsp");
			} else {
				request.setAttribute("errorMessage", "교수 회원가입 중 문제가 발생했습니다.");
				request.getRequestDispatcher("signup.jsp").forward(request, response);// 실패 시 에러 메시지 전달
            }
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "교수 회원가입 중 오류가 발생했습니다.");
		}
	}
}
