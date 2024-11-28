
package controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.StudentDAO;
import model.domain.Student;
import model.manager.StudentSignupManager;

@WebServlet("/signup/students")
public class StudentSignupController extends HttpServlet {
	
	private StudentSignupManager studentSignupManager;
	
	public StudentSignupController() {
		studentSignupManager = new StudentSignupManager();
	}
	
	// 학생 회원가입
	public void signupStudent(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		try {
			Integer studentId = Integer.parseInt(request.getParameter("studentId"));
			String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String dept = request.getParameter("dept");
            Integer grade = Integer.parseInt(request.getParameter("grade"));

            // Student 객체 생성
            Student student = new Student();
            
            student.setStudentId(studentId);
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            student.setPhone(phone);
            student.setDept(dept);
            student.setGrade(grade);
            student.setDeleted('N'); 
            
         // 회원가입 비즈니스 로직 호출
            boolean result = studentSignupManager.registerStudent(student);
		//api 응답 생성
           if(result) {
        	   response.sendRedirect("home.jsp");
           } else {
        	   request.setAttribute("errorMessage", "학생 회원가입 중 문제가 발생했습니다.");
           }
		
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "회원가입 중 오류가 발생했습니다.");
		}
		
	}
	
}
