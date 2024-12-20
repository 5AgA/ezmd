package controller.schedule;

import com.google.gson.Gson;
import controller.Controller;
import model.domain.Schedule;
import model.manager.schedule.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class AddScheduleController implements Controller {
    private ScheduleService scheduleService;

    public AddScheduleController() {
        scheduleService = new ScheduleService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST 메서드만 지원합니다.");
            return null;
        }

        // 클라이언트에서 보낸 JSON 데이터를 받기 위한 준비
        StringBuilder jsonString = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }

        String jsonData = jsonString.toString();
        System.out.println(jsonData);

        // 받은 JSON 데이터를 Schedule 객체로 변환
        Gson gson = new Gson();
        Schedule schedule = gson.fromJson(jsonString.toString(), Schedule.class);

        System.out.println(schedule);

        // 스케줄을 DB에 추가
        int result = scheduleService.addSchedule(schedule);

        // 응답 처리: 성공/실패에 대한 메시지
        response.setContentType("application/json; charset=UTF-8");
        String jsonResponse;
        if (result == 1) {
            jsonResponse = gson.toJson(new ResponseMessage("success", "일정이 성공적으로 추가되었습니다."));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("error", "일정 추가에 실패했습니다."));
        }

        response.getWriter().write(jsonResponse);
        return null;
    }

    // 응답 메시지 객체
    private static class ResponseMessage {
        private String status;
        private String message;

        public ResponseMessage(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
