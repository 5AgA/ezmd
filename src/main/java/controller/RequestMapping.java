package controller;

import java.util.HashMap;
import java.util.Map;

import controller.Interview.InterviewListController;
import controller.schedule.*;
import controller.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	// 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
	private Map<String, Controller> mappings = new HashMap<String, Controller>();

	public void initMapping() {
		// 시작 페이지
		mappings.put("/", new ForwardController("/index/index.jsp"));

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

        // 사용자 관련 페이지
        mappings.put("/home", new InterviewListController());
        mappings.put("/mypage", new ForwardController("/user/myPage.jsp"));
        mappings.put("/mypage/update", new MyPageUpdateController());
        mappings.put("/mypage/update/password", new ForwardController("/user/changePassword.jsp"));
        mappings.put("/mypage/update/info", new ChangePasswordController());

        // 스케줄
        mappings.put("/schedule", new ForwardController("/schedule/schedule.jsp"));
        mappings.put("/schedule/view", new ScheduleViewController());
        mappings.put("/schedule/info", new ScheduleController());
        mappings.put("/schedule/categories", new CategoryListController());
        mappings.put("/schedule/add", new AddScheduleController());
        mappings.put("/schedule/delete", new DeleteScheduleController());
        mappings.put("/schedule/update", new UpdateScheduleController());

        // 카테고리
        mappings.put("/category/add", new AddCategoryController());

		// 면담 신청 (학생)
        mappings.put("/interview", new ForwardController("/interview/selectProf.jsp"));
        mappings.put("/interview/reservation", new ForwardController("/interview/reservation.jsp"));
        mappings.put("/interview/reservation/submit", new ForwardController("/interview/reservation-submit.jsp"));

		// 교수 목록 조회/검색
		mappings.put("/profs/view", new ProfessorsViewController());
		mappings.put("/profs/search", new SearchProfController());

		// 면담 결과 (학생)
		mappings.put("/interview/result", new ForwardController("/interviewManager_clear/interviewClearList.jsp"));
		mappings.put("/interviewResult/management", new ForwardController("/interviewManager_clear/interviewResultManagement.jsp"));

		// 면담 승인 (교수)
        mappings.put("/interview/check", new InterviewListController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}