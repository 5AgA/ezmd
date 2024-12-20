package controller.schedule;

import com.google.gson.Gson;
import controller.Controller;
import model.domain.Schedule;
import model.manager.schedule.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ScheduleController implements Controller {
    private ScheduleService scheduleService;

    public ScheduleController() {
        scheduleService = new ScheduleService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int scheduleId = Integer.parseInt(request.getParameter("id"));

        // DB에서 해당 날짜의 일정 가져오기
        Schedule schedules = scheduleService.getScheduleById(scheduleId);

        // JSON 응답
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new Gson().toJson(schedules));
        return null; // JSON만 반환
    }
}
