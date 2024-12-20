//package controller.schedule;
//
//import com.google.gson.Gson;
//import controller.Controller;
//import model.domain.Schedule;
//import model.manager.schedule.ScheduleService;
//import model.manager.user.LogoutManager;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@WebServlet("/schedule")
//public class ScheduleController extends HttpServlet implements Controller {
//
//    private ScheduleService scheduleService;
//
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return null;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 사용자 ID 가져오기 (예: 세션에서)
//        Integer userId = (Integer) request.getSession().getAttribute("userId");
//        if (userId == null) {
//            userId = 20210670; // 테스트용 사용자 ID 설정
//            request.getSession().setAttribute("userId", userId);
//        }
//
//        // 날짜가 없으면 기본적으로 모든 스케줄을 가져옵니다
//        List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
//        request.setAttribute("schedules", schedules);
//        request.getRequestDispatcher("/WEB-INF/schedule/schedule.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 새 스케줄 추가 처리
//        String title = request.getParameter("scheduleTitle");
//        String start = request.getParameter("scheduleStart");
//        String end = request.getParameter("scheduleEnd");
//        int repeat = Integer.parseInt(request.getParameter("scheduleRepeat"));
//        String place = request.getParameter("schedulePlace");
//        String memo = request.getParameter("scheduleMemo");
//        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
//        Integer userId = (Integer) request.getSession().getAttribute("userId");
//
//        if (userId != null) {
//            Schedule schedule = new Schedule();
//            schedule.setScheduleTitle(title);
//            schedule.setScheduleStart(Schedule.parseDateTime(start));
//            schedule.setScheduleEnd(Schedule.parseDateTime(end));
//            schedule.setScheduleRepeat(repeat);
//            schedule.setSchedulePlace(place);
//            schedule.setScheduleMemo(memo);
//            schedule.setCategoryId(categoryId);
//            schedule.setUserId(userId);
//
//            // DB에 새 스케줄 추가
//            scheduleService.insertSchedule(schedule);
//
//            // 스케줄 목록으로 리다이렉트
//            response.sendRedirect("/schedule");
//        } else {
//            response.sendRedirect("/login"); // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 스케줄 수정 처리
//        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
//        String title = request.getParameter("scheduleTitle");
//        String start = request.getParameter("scheduleStart");
//        String end = request.getParameter("scheduleEnd");
//        int repeat = Integer.parseInt(request.getParameter("scheduleRepeat"));
//        String place = request.getParameter("schedulePlace");
//        String memo = request.getParameter("scheduleMemo");
//        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
//        Integer userId = (Integer) request.getSession().getAttribute("userId");
//
//        Schedule schedule = new Schedule();
//        schedule.setScheduleId(scheduleId);
//        schedule.setScheduleTitle(title);
//        schedule.setScheduleStart(Schedule.parseDateTime(start));
//        schedule.setScheduleEnd(Schedule.parseDateTime(end));
//        schedule.setScheduleRepeat(repeat);
//        schedule.setSchedulePlace(place);
//        schedule.setScheduleMemo(memo);
//        schedule.setCategoryId(categoryId);
//        schedule.setUserId(userId);
//
//        // 스케줄 수정
//        scheduleService.updateSchedule(schedule);
//
//        // 수정 후 스케줄 목록으로 리다이렉트
//        response.sendRedirect("/schedule");
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 스케줄 삭제 처리
//        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
//
//        // 스케줄 삭제
//        scheduleService.deleteSchedule(scheduleId);
//
//        // 삭제 후 스케줄 목록으로 리다이렉트
//        response.sendRedirect("/schedule");
//    }
//
//}
