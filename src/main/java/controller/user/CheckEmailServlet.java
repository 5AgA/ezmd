package controller.user;

import java.io.IOException;
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
    private Gson gson;

    @Override
    public void init() throws ServletException {
        // DAO 초기화
        studentDAO = new StudentDAO();
        professorDAO = new ProfessorDAO();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 요청 인코딩 설정 (UTF-8)
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        boolean exists = false;

        // 이메일 형식 검증: @dongduk.ac.kr 도메인 확인
        if (email != null && email.matches("^[^\\s@]+@dongduk\\.ac\\.kr$")) {
            try {
                // 학생 테이블에서 이메일 존재 여부 확인
                if (studentDAO.findStudentByEmail(email) != null) {
                    exists = true;
                }

                // 교수 테이블에서 이메일 존재 여부 확인 (학생에 없을 경우)
                if (!exists && professorDAO.findProfessorByEmail(email) != null) {
                    exists = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 예외 발생 시, 중복된 것으로 처리하거나 별도의 로직을 추가할 수 있습니다.
                exists = true;
            }
        } else {
            // 유효하지 않은 이메일 형식인 경우, 중복된 것으로 간주하거나 에러 처리
            // 여기서는 간단히 중복된 것으로 간주
            exists = true;
        }

        // JSON 응답 생성
        EmailCheckResponse emailCheckResponse = new EmailCheckResponse(exists);
        String jsonResponse = gson.toJson(emailCheckResponse);

        // 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 전송
        response.getWriter().write(jsonResponse);
    }

    // GET 요청도 동일하게 처리하도록 doGet 오버라이드
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // 내부 클래스: JSON 응답 구조 정의
    private static class EmailCheckResponse {
        private boolean exists;

        public EmailCheckResponse(boolean exists) {
            this.exists = exists;
        }

        public boolean isExists() {
            return exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
    }
}
