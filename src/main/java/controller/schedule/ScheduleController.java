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
        String idParam = request.getParameter("id");
        int scheduleId = 0;
        if (idParam == null) {
            System.out.println("id 파라미터가 없습니다.");
        } else {
            System.out.println("받은 id: " + idParam);
            scheduleId = Integer.parseInt(idParam);
        }

        // DB에서 해당 날짜의 일정 가져오기
        Schedule schedule = scheduleService.getScheduleById(scheduleId);

        // JSON 응답
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new Gson().toJson(schedule));
        return null; // JSON만 반환
    }
}
