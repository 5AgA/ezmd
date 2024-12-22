package controller.schedule;

import com.google.gson.Gson;
import controller.Controller;
import model.manager.schedule.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteScheduleController implements Controller {
    private ScheduleService scheduleService;

    public DeleteScheduleController() {
        scheduleService = new ScheduleService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!"DELETE".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "DELETE 메서드만 지원합니다.");
            return null;
        }

        // 쿼리 파라미터에서 scheduleId 추출
        String scheduleIdStr = request.getParameter("id");

        if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"scheduleId 파라미터가 필요합니다.\"}");
            return null;
        }

        int scheduleId = Integer.parseInt(scheduleIdStr);

        // 스케줄 삭제
        int result = scheduleService.deleteSchedule(scheduleId);

        // 응답 처리: 성공/실패에 대한 메시지
        response.setContentType("application/json; charset=UTF-8");
        String jsonResponse;

        if (result == 1) {
            jsonResponse = new Gson().toJson(new ResponseMessage("success", "일정이 성공적으로 삭제되었습니다."));
        } else {
            jsonResponse = new Gson().toJson(new ResponseMessage("error", "일정 삭제에 실패했습니다."));
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
