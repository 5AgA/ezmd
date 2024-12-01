package model.manager.schedule;

import model.dao.ScheduleDAO;
import model.domain.Schedule;

import java.sql.SQLException;
import java.util.List;

public class ScheduleManager {
    private ScheduleDAO scheduleDAO;

    public ScheduleManager() {
        scheduleDAO = new ScheduleDAO();
    }

    public List<Schedule> getSchedules(int userId) throws SQLException {
        return scheduleDAO.getSchedules(userId);
    }
}