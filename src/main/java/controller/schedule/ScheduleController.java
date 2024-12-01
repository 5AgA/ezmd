package controller.schedule;

import model.domain.Schedule;
import model.manager.schedule.ScheduleManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/schedule")
public class ScheduleController extends HttpServlet {

    private final ScheduleManager scheduleManager = new ScheduleManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // `execute` 메서드 호출 및 반환된 뷰 처리
            String view = execute(request, response);
            request.getRequestDispatcher(view).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "스케줄 처리 중 오류 발생");
        }
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                return createSchedule(request, response);
            case "read":
                return readSchedule(request, response);
            case "readByDate":
                return readScheduleByDate(request, response);
            case "getToday":
                return getTodaySchedules(request, response);
            case "update":
                return updateSchedule(request, response);
            case "delete":
                return deleteSchedule(request, response);
            default:
                request.setAttribute("errorMessage", "Invalid action");
                return "error.jsp";
        }
    }

    private String createSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Schedule schedule = new Schedule();
        schedule.setScheduleTitle(request.getParameter("scheduleTitle"));
        schedule.setScheduleStart(LocalDateTime.parse(request.getParameter("scheduleStart")));
        schedule.setScheduleEnd(LocalDateTime.parse(request.getParameter("scheduleEnd")));
        schedule.setScheduleRepeat(Integer.parseInt(request.getParameter("scheduleRepeat")));
        schedule.setSchedulePlace(request.getParameter("schedulePlace"));
        schedule.setScheduleMemo(request.getParameter("memo"));
        schedule.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        schedule.setProfessorId(Integer.parseInt(request.getParameter("professorId")));
        schedule.setStudentId(Integer.parseInt(request.getParameter("studentId")));

        Schedule createdSchedule = scheduleManager.createSchedule(schedule);
        if (createdSchedule != null) {
            request.setAttribute("message", "Schedule created successfully: " + createdSchedule.toString());
            return "scheduleSuccess.jsp"; // 성공 시 JSP 반환
        } else {
            request.setAttribute("errorMessage", "Failed to create schedule");
            return "scheduleError.jsp"; // 실패 시 JSP 반환
        }
    }

    private String readSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String monthStartParam = request.getParameter("monthStart");
        String monthEndParam = request.getParameter("monthEnd");

        LocalDateTime monthStart = LocalDateTime.parse(monthStartParam);
        LocalDateTime monthEnd = LocalDateTime.parse(monthEndParam);

        List<Schedule> schedules = scheduleManager.findSchedulesByDateRange(monthStart, monthEnd);

        List<Schedule> allSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getScheduleRepeat() > 0) {
                allSchedules.addAll(scheduleManager.generateRepeatingSchedulesForMonth(schedule, monthStart, monthEnd));
            } else {
                allSchedules.add(schedule);
            }
        }

        request.setAttribute("scheduleList", allSchedules);
        return "scheduleList.jsp"; // 스케줄 목록을 보여줄 JSP 반환
    }

    private String readScheduleByDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dateParam = request.getParameter("date"); // 선택한 날짜
        LocalDate date = LocalDate.parse(dateParam);

        List<Schedule> schedules = scheduleManager.findScheduleByDate(date);

        request.setAttribute("scheduleList", schedules);
        return "scheduleList.jsp"; // JSP에서 해당 날짜 일정 출력
    }

    private String updateSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        Schedule schedule = scheduleManager.findScheduleById(scheduleId);

        if (schedule != null) {
            schedule.setScheduleTitle(request.getParameter("scheduleTitle"));
            schedule.setScheduleStart(LocalDateTime.parse(request.getParameter("scheduleStart")));
            schedule.setScheduleEnd(LocalDateTime.parse(request.getParameter("scheduleEnd")));
            schedule.setScheduleRepeat(Integer.parseInt(request.getParameter("scheduleRepeat")));
            schedule.setSchedulePlace(request.getParameter("schedulePlace"));
            schedule.setScheduleMemo(request.getParameter("memo"));
            schedule.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));

            boolean success = scheduleManager.updateSchedule(schedule);
            if (success) {
                request.setAttribute("message", "Schedule updated successfully");
                return "scheduleSuccess.jsp"; // 성공 시 JSP 반환
            } else {
                request.setAttribute("errorMessage", "Failed to update schedule");
                return "scheduleError.jsp"; // 실패 시 JSP 반환
            }
        } else {
            request.setAttribute("errorMessage", "Schedule not found");
            return "scheduleError.jsp";
        }
    }

    private String deleteSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        boolean success = scheduleManager.deleteSchedule(scheduleId);

        if (success) {
            request.setAttribute("message", "Schedule deleted successfully");
            return "scheduleSuccess.jsp"; // 성공 시 JSP 반환
        } else {
            request.setAttribute("errorMessage", "Schedule not found");
            return "scheduleError.jsp"; // 실패 시 JSP 반환
        }
    }

    private String getTodaySchedules(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Schedule> todaySchedules = scheduleManager.getTodaySchedules();

        // 결과를 JSON으로 변환해서 반환하거나 JSP로 전달
        request.setAttribute("todaySchedules", todaySchedules);
        return "todayScheduleList.jsp"; // JSP 파일에 오늘 일정 전달
    }

}