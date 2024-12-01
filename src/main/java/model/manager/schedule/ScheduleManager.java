package model.manager.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.dao.ScheduleDAO;
import model.domain.Schedule;

public class ScheduleManager {

    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    // 스케줄 생성
    public Schedule createSchedule(Schedule schedule) {
        return scheduleDAO.create(schedule);
    }

    // 스케줄 ID로 찾기
    public Schedule findScheduleById(int scheduleId) {
        return scheduleDAO.findScheduleById(scheduleId);
    }

    // 특정 날짜 범위의 반복 스케줄 생성
    public List<Schedule> createRepeatingSchedule(Schedule schedule, LocalDate monthStart, LocalDate monthEnd) {
        List<Schedule> repeatingSchedules = new ArrayList<>();

        // 현재 월의 시작 날짜
        LocalDate current = monthStart;

        // 반복 스케줄 요일 비트 정보
        int repeatDays = schedule.getScheduleRepeat();

        while (!current.isAfter(monthEnd)) {
            if (isRepeatingDay(current, repeatDays)) {
                Schedule repeatedSchedule = new Schedule();
                repeatedSchedule.setScheduleTitle(schedule.getScheduleTitle());
                repeatedSchedule.setScheduleStart(LocalDateTime.of(current, schedule.getScheduleStart().toLocalTime()));
                repeatedSchedule.setScheduleEnd(LocalDateTime.of(current, schedule.getScheduleEnd().toLocalTime()));
                repeatedSchedule.setScheduleRepeat(schedule.getScheduleRepeat());
                repeatedSchedule.setSchedulePlace(schedule.getSchedulePlace());
                repeatedSchedule.setScheduleMemo(schedule.getScheduleMemo());
                repeatedSchedule.setCategoryId(schedule.getCategoryId());
                repeatedSchedule.setProfessorId(schedule.getProfessorId());
                repeatedSchedule.setStudentId(schedule.getStudentId());

                repeatingSchedules.add(repeatedSchedule);
            }
            current = current.plusDays(1);
        }
        return repeatingSchedules;
    }

    // 요일 반복 여부 확인
    private boolean isRepeatingDay(LocalDate date, int repeatDays) {
        int dayOfWeek = date.getDayOfWeek().getValue(); // 월요일=1, 일요일=7
        int bitPosition = dayOfWeek % 7; // 비트 계산용 (1~7)
        return (repeatDays & (1 << (bitPosition - 1))) > 0; // 해당 요일 반복 여부
    }
    public List<Schedule> generateRepeatingSchedulesForMonth(Schedule schedule, LocalDateTime monthStart, LocalDateTime monthEnd) {
        List<Schedule> repeatingSchedules = new ArrayList<>();
        LocalDate current = monthStart.toLocalDate();

        while (!current.isAfter(monthEnd.toLocalDate())) {
            if (isRepeatingDay(current, schedule.getScheduleRepeat())) {
                Schedule repeatedSchedule = new Schedule();
                repeatedSchedule.setScheduleTitle(schedule.getScheduleTitle());
                repeatedSchedule.setScheduleStart(LocalDateTime.of(current, schedule.getScheduleStart().toLocalTime()));
                repeatedSchedule.setScheduleEnd(LocalDateTime.of(current, schedule.getScheduleEnd().toLocalTime()));
                repeatedSchedule.setScheduleRepeat(schedule.getScheduleRepeat());
                repeatedSchedule.setSchedulePlace(schedule.getSchedulePlace());
                repeatedSchedule.setScheduleMemo(schedule.getScheduleMemo());
                repeatedSchedule.setCategoryId(schedule.getCategoryId());
                repeatedSchedule.setProfessorId(schedule.getProfessorId());
                repeatedSchedule.setStudentId(schedule.getStudentId());

                repeatingSchedules.add(repeatedSchedule);
            }
            current = current.plusDays(1);
        }
        return repeatingSchedules;
    }
    public List<Schedule> findScheduleByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return scheduleDAO.findSchedulesByDateRange(startOfDay, endOfDay);
    }


    // 스케줄 업데이트
    public boolean updateSchedule(Schedule schedule) {
        return scheduleDAO.update(schedule) > 0;
    }

    // 스케줄 삭제
    public boolean deleteSchedule(int scheduleId) {
        return scheduleDAO.remove(scheduleId) > 0;
    }

    // 특정 날짜 범위 스케줄 찾기 (컨트롤러 요청에 맞춤 추가)
    public List<Schedule> findSchedulesByDateRange(LocalDateTime start, LocalDateTime end) {
        return scheduleDAO.findSchedulesByDateRange(start, end);
    }

    public List<Schedule> getTodaySchedules() {
        return scheduleDAO.findTodaySchedules();
    }

}