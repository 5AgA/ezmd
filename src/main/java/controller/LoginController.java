package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 폼에서 전달된 로그인 정보 가져오기
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 간단한 로그인 로직 예시 (실제 환경에서는 DB를 조회해야 합니다)
        if ("test@dongduk.ac.kr".equals(email) && "password123".equals(password)) {
            // 로그인 성공
            request.setAttribute("message", "로그인 성공!");
            return "/home";
        } else {
            // 로그인 실패
            request.setAttribute("message", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "/login/form";
        }
    }
}
