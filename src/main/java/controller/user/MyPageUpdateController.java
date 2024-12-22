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
import model.dao.ProfessorDAO;
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
            return "redirect:/login/form";
        }

        // 교수 또는 학생 구분
        if (user instanceof Professor) {
            Professor prof = (Professor) user;
            
            // 업데이트할 학과 정보 가져오기
            String dept = request.getParameter("dept");
            if (dept != null && !dept.trim().isEmpty()) {
                prof.setDept(dept.trim());
            }
            // DAO를 통해 교수 정보 업데이트
            ProfessorDAO professorDAO = new ProfessorDAO();
            professorDAO.updateProfessor(prof);

        } else if (user instanceof Student) {
            Student stu = (Student) user;
            String dept = request.getParameter("dept");
            if (dept != null && !dept.trim().isEmpty()) {
                stu.setDept(dept.trim());
            }
            // 학년 변경 가능
            String gradeStr = request.getParameter("grade");
            int grade = Integer.parseInt(gradeStr);
            stu.setGrade(grade);
            // DAO 업데이트 로직 호출
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.updateStudent(stu);
        }

        // 업데이트 후 메시지 표시용 또는 마이페이지 재로드
        return "redirect:/mypage";
    }
}

