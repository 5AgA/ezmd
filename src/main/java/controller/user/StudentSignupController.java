
package controller.user;

import controller.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.StudentDAO;
import model.domain.Student;
import model.manager.StudentSignupManager;

<<<<<<< HEAD
@WebServlet("/signup/students")
=======
@WebServlet("/signup/student")
>>>>>>> 442fcd4b9ce0b0d9d452df219ae786f538f98d71
public class StudentSignupController extends HttpServlet implements Controller {
	

    private StudentSignupManager studentSignupManager;

    public StudentSignupController() {
        studentSignupManager = new StudentSignupManager();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 요청 데이터 추출
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
            student.setDeleted('N'); // 기본값 설정

            // 회원가입 비즈니스 로직 호출
            boolean result = studentSignupManager.registerStudent(student);

            // 성공 여부에 따라 이동할 URL 반환
            if (result) {
                return "home.jsp"; // 성공 시 홈 화면으로 이동
            } else {
                request.setAttribute("errorMessage", "학생 회원가입 중 문제가 발생했습니다.");
                return "signup.jsp"; // 실패 시 회원가입 페이지로 이동
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("회원가입 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // execute 메서드 호출 후 반환된 URL로 이동
            String view = execute(request, response);
            if (view.equals("home.jsp")) {
                response.sendRedirect(view);
            } else {
                request.getRequestDispatcher(view).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "학생 회원가입 처리 중 오류가 발생했습니다.");
        }
    }
	
}
