package controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.dao.StudentDAO;
import model.domain.Professor;
import model.domain.Student;

@WebServlet("/mypage/update")
public class MyPageUpdateController extends HttpServlet implements Controller {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String view = execute(request, response);
            if (view.startsWith("redirect:")) {
                response.sendRedirect(view.substring("redirect:".length()));
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(view);
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "업데이트 중 오류 발생");
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
//        session.setAttribute("user", loggedInUser);
        Object user = session.getAttribute("user");

        if (user == null) {
            return "redirect:/ezmd/login/form";
        }

        // 교수 또는 학생 구분
        if (user instanceof Professor) {
            Professor prof = (Professor) user;
            // 교수는 변경 불가능 필드이므로 업데이트할 필요가 없다면 생략 가능
            // 하지만 여기서는 비밀번호 변경 시를 고려해서 password도 받을 수 있음.
            // 현재 JSP는 password 변경 폼이 없으니, password 변경 기능을 추가하면 form에 password 필드 추가 후 처리 가능.
            
            //prof.setPassword(request.getParameter("password")); // 예시
            
            // 필요하다면 DAO 업데이트 로직 호출
            // professorDAO.updateProfessor(prof);
            
        } else if (user instanceof Student) {
            Student stu = (Student) user;
            // 학년만 변경 가능
            String gradeStr = request.getParameter("grade");
            int grade = Integer.parseInt(gradeStr);
            stu.setGrade(grade);
            // DAO 업데이트 로직 호출
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.updateStudent(stu);
        }

        // 업데이트 후 메시지 표시용 또는 마이페이지 재로드
        return "redirect:/ezmd/mypage";
    }
}

