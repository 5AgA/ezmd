package controller;

import java.util.HashMap;
import java.util.Map;

import controller.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	// 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
	private Map<String, Controller> mappings = new HashMap<String, Controller>();

	public void initMapping() {
		// 각 uri에 대응되는 controller 객체를 생성 및 저장
		mappings.put("/", new ForwardController("/index/index.jsp"));
		mappings.put("/login/form", new ForwardController("/user/loginForm.jsp"));

		// 페이지 이동
		mappings.put("/home", new ForwardController("/user/home.jsp"));
		mappings.put("/mypage", new ForwardController("/user/mypage.jsp"));
		mappings.put("/schedule", new ForwardController("/schedule/schedule.jsp"));

		mappings.put("/register/form", new ForwardController("/user/registerForm.jsp"));

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
		//mappings.put("/interviewResult/create", new InterviewResultController());

		// 면담 승인 (교수)
        mappings.put("/interview-check", new ForwardController("/interview/interview-check.jsp"));

        logger.info("Initialized Request Mapping!");
    }

	public Controller findController(String uri) {
		// 주어진 uri에 대응되는 controller 객체를 찾아 반환
		return mappings.get(uri);
	}
}