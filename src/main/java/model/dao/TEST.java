package model.dao;

import model.domain.Schedule;

import java.util.Date;

public class TEST {
    public static void main(String[] args) {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule = new Schedule(0, 1, "테스트 일정", new Date(), new Date(),
                0, "테스트 장소", "테스트 메모", 1, 1, 1);
        scheduleDAO.create(schedule);
    }
}
