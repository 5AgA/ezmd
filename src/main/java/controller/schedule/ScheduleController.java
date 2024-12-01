package controller;

import model.domain.Schedule;
import model.manager.schedule.ScheduleManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/schedules")
public class ScheduleController extends HttpServlet {
    private ScheduleManager scheduleManager;

    public ScheduleController() {
        scheduleManager = new ScheduleManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId")); // Assuming userId is passed as a request parameter
        try {
            List<Schedule> schedules = scheduleManager.getSchedules(userId);
            request.setAttribute("schedules", schedules);
            request.getRequestDispatcher("/WEB-INF/schedule/calendar.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}