package model.dao;

import model.domain.Schedule;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private JDBCUtil jdbcUtil = null;

    public ScheduleDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // **1. Create (스케줄 생성)**
    public Schedule create(Schedule schedule) {
        String query = "INSERT INTO Schedule (schedule_id, schedule_title, schedule_start, schedule_end, schedule_repeat, schedule_place, memo, category_id, user_id) " +
                "VALUES (schedule_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] param = new Object[] {
                schedule.getScheduleTitle(),
                Timestamp.valueOf(schedule.getScheduleStart()),
                Timestamp.valueOf(schedule.getScheduleEnd()),
                schedule.getScheduleRepeat(),
                schedule.getSchedulePlace(),
                schedule.getScheduleMemo(),
                schedule.getCategoryId(),
                schedule.getUserId()
        };

        String[] key = {"schedule_id"};
        try {
            jdbcUtil.setSqlAndParameters(query, param);
            jdbcUtil.executeUpdate(key);
            ResultSet rs = jdbcUtil.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                schedule.setScheduleId(generatedKey);
            }
            return schedule;
        } catch (Exception e) {
            jdbcUtil.rollback();
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return null;
    }

    // **2. Update (스케줄 업데이트)**
    public int update(Schedule schedule) {
        String query = "UPDATE Schedule SET schedule_title=?, schedule_start=?, schedule_end=?, schedule_repeat=?, schedule_place=?, memo=?, " +
                "category_id=? WHERE schedule_id=?";
        Object[] param = new Object[] {
                schedule.getScheduleTitle(),
                Timestamp.valueOf(schedule.getScheduleStart()),
                Timestamp.valueOf(schedule.getScheduleEnd()),
                schedule.getScheduleRepeat(),
                schedule.getSchedulePlace(),
                schedule.getScheduleMemo(),
                schedule.getCategoryId(),
                schedule.getScheduleId()
        };

        jdbcUtil.setSqlAndParameters(query, param);
        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // **3. Delete (스케줄 삭제)**
    public int remove(int scheduleId) {
        String query = "DELETE FROM Schedule WHERE schedule_id=?";
        jdbcUtil.setSqlAndParameters(query, new Object[]{scheduleId});
        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // **4. Find by ID (스케줄 ID로 조회)**
    public Schedule findScheduleById(Integer scheduleId) {
        String query = "SELECT * FROM Schedule WHERE schedule_id=?";
        jdbcUtil.setSqlAndParameters(query, new Object[]{scheduleId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return mapResultSetToSchedule(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // **5. Find by Date Range (날짜 범위로 조회)**
    public List<Schedule> findSchedulesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        String query = "SELECT * FROM Schedule WHERE schedule_start BETWEEN ? AND ?";
        jdbcUtil.setSqlAndParameters(query, new Object[] {
                Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate)
        });

        List<Schedule> scheduleList = new ArrayList<>();
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                scheduleList.add(mapResultSetToSchedule(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return scheduleList;
    }

    // **6. Private Utility Method: ResultSet -> Schedule 변환**
    private Schedule mapResultSetToSchedule(ResultSet rs) throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(rs.getInt("schedule_id"));
        schedule.setScheduleTitle(rs.getString("schedule_title"));
        schedule.setScheduleStart(rs.getTimestamp("schedule_start").toLocalDateTime());
        schedule.setScheduleEnd(rs.getTimestamp("schedule_end").toLocalDateTime());
        schedule.setScheduleRepeat(rs.getInt("schedule_repeat"));
        schedule.setSchedulePlace(rs.getString("schedule_place"));
        schedule.setScheduleMemo(rs.getString("memo"));
        schedule.setCategoryId(rs.getInt("category_id"));
        schedule.setUserId(rs.getInt("user_id"));
        return schedule;
    }

    public List<Schedule> findTodaySchedules() { //오늘 날짜값 세팅
        LocalDateTime startOfToday = LocalDateTime.now().with(LocalTime.MIN); // 00:00:00
        LocalDateTime endOfToday = LocalDateTime.now().with(LocalTime.MAX);  // 23:59:59

        return findSchedulesByDateRange(startOfToday, endOfToday);
    }


}