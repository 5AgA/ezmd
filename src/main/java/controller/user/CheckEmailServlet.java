package controller.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import model.dao.StudentDAO;
import model.dao.ProfessorDAO;

@WebServlet("/checkEmail")
public class CheckEmailServlet extends HttpServlet {

    private StudentDAO studentDAO;
    private ProfessorDAO professorDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
        professorDAO = new ProfessorDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean exists = false;
        String message = null;

        try {
            //메일 존재 여부 확인
            if (professorDAO.findUserByEmail(email)) {
            	exists = true;
                message = "이미 사용 중인 이메일입니다.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            exists = true;
            message = "이메일 중복 확인 중 오류가 발생했습니다.";
        }


        // JSON 응답 준비
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 객체 생성
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("exists", exists);
        jsonResponse.put("message", message);

        // JSON으로 응답 전송
        Gson gson = new Gson();
        String json = gson.toJson(jsonResponse);
        response.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}