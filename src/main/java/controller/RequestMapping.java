package controller;

import java.util.HashMap;
import java.util.Map;

import controller.schedule.*;
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
        
        // 회원 가입 폼 요청과 가입 요청 처리 병합 (폼에 커뮤니티 선택 메뉴 추가를 위함)
        mappings.put("/register/form", new ForwardController("/user/registerForm.jsp"));

        // 페이지 이동
        mappings.put("/home", new ForwardController("/user/home.jsp"));
        mappings.put("/myPage", new ForwardController("/user/mypage.jsp"));

        // 스케줄
        mappings.put("/schedule", new ForwardController("/schedule/schedule.jsp"));
        mappings.put("/schedule/view", new ScheduleViewController());
        mappings.put("/schedule/info", new ScheduleController());
        mappings.put("/schedule/categories", new CategoryListController());
        mappings.put("/schedule/add", new AddScheduleController());
        mappings.put("/schedule/delete", new DeleteScheduleController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}