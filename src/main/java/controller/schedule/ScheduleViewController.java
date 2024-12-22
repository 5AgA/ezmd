package controller.schedule;

import com.google.gson.Gson;
import controller.Controller;
import model.domain.Schedule;
import model.manager.schedule.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ScheduleViewController implements Controller {
    private ScheduleService scheduleService;

    public ScheduleViewController() {
        scheduleService = new ScheduleService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());

        // selectedDate 파라미터 가져오기
        String date = request.getParameter("date");
        if (date == null || date.isEmpty()) {
            List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(new Gson().toJson(schedules));
            return null; // JSON만 반환
        }

        // DB에서 해당 날짜의 일정 가져오기
        List<Schedule> schedules = scheduleService.getSchedulesByDate(userId, date);

        // JSON 응답
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new Gson().toJson(schedules));
        return null; // JSON만 반환
    }
}
