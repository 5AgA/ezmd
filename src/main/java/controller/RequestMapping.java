package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.user.StudentLoginController;
import controller.user.ProfessorLoginController;
import controller.user.LogoutController;
import controller.user.StudentSignupController;
import controller.user.ProfessorSignupController;
import controller.Interview.InterviewClearListController;
public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	// 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
	private Map<String, Controller> mappings = new HashMap<String, Controller>();

	public void initMapping() {
		// 각 uri에 대응되는 controller 객체를 생성 및 저장
		mappings.put("/", new ForwardController("/index/index.jsp"));
		mappings.put("/login/form", new ForwardController("/user/loginForm.jsp"));
//        mappings.put("/login", new LoginController());
//        mappings.put("/logout", new LogoutController());
//        mappings.put("/user/list", new ListUserController());
//        mappings.put("/user/view", new ViewUserController());

		// 회원 가입 폼 요청과 가입 요청 처리 병합 (폼에 커뮤니티 선택 메뉴 추가를 위함)
		// mappings.put("/register", new SignupController());

		// 사용자 정보 수정 폼 요청과 수정 요청 처리 병합
//      mappings.put("/user/update/form", new UpdateUserFormController());
//      mappings.put("/user/update", new UpdateUserController());        
//        mappings.put("/user/update", new UpdateUserController());
//        mappings.put("/user/delete", new DeleteUserController());

		// 페이지 이동
		mappings.put("/home", new ForwardController("/user/home.jsp"));
		mappings.put("/myPage", new ForwardController("/user/mypage.jsp"));
		mappings.put("/schedule", new ForwardController("/schedule/schedule.jsp"));

		mappings.put("/register/form", new ForwardController("/user/registerForm.jsp"));

		mappings.put("/interviewResult/clear", new ForwardController("/interviewManager_clear/interviewClearList.jsp"));
		mappings.put("/interviewResult/management", new ForwardController("/interviewManager_clear/interviewResultManagement.jsp"));
		//mappings.put("/interviewResult/create", new InterviewResultController());

		logger.info("Initialized Request Mapping!");
	}

	public Controller findController(String uri) {
		// 주어진 uri에 대응되는 controller 객체를 찾아 반환
		return mappings.get(uri);
	}
}