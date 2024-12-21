package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.ChangePasswordController;
import controller.user.LogoutController;
import controller.user.MyPageUpdateController;
import controller.user.ProfessorLoginController;
import controller.user.ProfessorSignupController;
import controller.user.StudentLoginController;
import controller.user.StudentSignupController;

import controller.ForwardController;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    // 각 요청 URI에 대한 Controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<>();

    public void initMapping() {
        // 기본 페이지 매핑
        mappings.put("/", new ForwardController("/index/index.jsp"));
        mappings.put("/home", new ForwardController("/user/home.jsp"));
        mappings.put("/myPage", new ForwardController("/user/myPage.jsp"));
        mappings.put("/myPage/update", new MyPageUpdateController());
        mappings.put("/myPage/update/password", new ForwardController("/user/changePassword.jsp"));
        mappings.put("/myPage/update/info", new ChangePasswordController());
        
        mappings.put("/schedule", new ForwardController("/schedule/schedule.jsp"));

        // 로그인 폼 및 요청 처리
        mappings.put("/login/form", new ForwardController("/user/loginForm.jsp"));
        mappings.put("/login/student", new StudentLoginController()); // 학생 로그인
        mappings.put("/login/professor", new ProfessorLoginController()); // 교수 로그인

        // 로그아웃 처리
        mappings.put("/logout", new LogoutController());

        // 회원가입 폼 및 요청 처리
        mappings.put("/register/form", new ForwardController("/user/registerForm.jsp")); // 회원가입 메인 폼
        mappings.put("/register/form/stud", new ForwardController("/user/studentRegisterForm.jsp")); // 학생 회원가입 폼
        mappings.put("/register/form/prof", new ForwardController("/user/professorRegisterForm.jsp")); // 교수 회원가입 폼
        mappings.put("/signup/student", new StudentSignupController()); // 학생 회원가입 처리
        mappings.put("/signup/professor", new ProfessorSignupController()); // 교수 회원가입 처리

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {
        // 주어진 URI에 대응되는 Controller 객체를 찾아 반환
        Controller controller = mappings.get(uri);
        if (controller == null) {
            logger.warn("No controller found for URI: {}", uri);
        }
        return controller;
    }
}