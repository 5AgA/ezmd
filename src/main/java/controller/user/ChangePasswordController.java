package controller.user;
import controller.Controller;
import model.manager.user.ChangePasswordManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/mypage/update/info")
public class ChangePasswordController extends HttpServlet implements Controller {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChangePasswordManager changePasswordManager = new ChangePasswordManager();

        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || session.getAttribute("userType") == null) {
            response.sendRedirect("/login/form");
            return;
        }

        String userType = (String) session.getAttribute("userType");
        int userId;
        if ("Professor".equalsIgnoreCase(userType)) {
            userId = ((model.domain.Professor) session.getAttribute("user")).getProfessorId();
        } else if ("Student".equalsIgnoreCase(userType)) {
            userId = ((model.domain.Student) session.getAttribute("user")).getStudentId();
        } else {
            response.sendRedirect("/login/form");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 비밀번호 확인
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "새 비밀번호가 일치하지 않습니다.");
            RequestDispatcher rd = request.getRequestDispatcher("/mypage/update/password");
            rd.forward(request, response);
            return;
        }

        try {
            boolean isChanged = changePasswordManager.changePassword(userType, userId, currentPassword, newPassword);
            if (isChanged) {
                session.setAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
                response.sendRedirect("/mypage");
            } else {
                request.setAttribute("errorMessage", "현재 비밀번호가 일치하지 않거나 변경에 실패했습니다.");
                RequestDispatcher rd = request.getRequestDispatcher("/mypage/update/password");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "비밀번호 변경 중 오류가 발생했습니다.");
            RequestDispatcher rd = request.getRequestDispatcher("/mypage/update/password");
            rd.forward(request, response);
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 사용되지 않습니다.
        return null;
    }
}