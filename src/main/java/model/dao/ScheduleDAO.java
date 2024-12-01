package model.dao;

import model.domain.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private JDBCUtil jdbcUtil = null;

    public ScheduleDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // Create
    public Schedule create(Schedule schedule) throws SQLException {
        String query = "INSERT INTO Schedule (schedule_id, schedule_type, schedule_title, schedule_start, schedule_end, schedule_repeat, schedule_place, memo, category_id, professor_id, student_id) " +
                       "VALUES (schedule_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] param = new Object[]{
                schedule.getScheduleType(),
                schedule.getScheduleTitle(),
                Timestamp.valueOf(schedule.getScheduleStart()),
                Timestamp.valueOf(schedule.getScheduleEnd()),
                schedule.getScheduleRepeat(),
                schedule.getSchedulePlace(),
                schedule.getScheduleMemo(),
                schedule.getCategoryId(),
                schedule.getProfessorId(),
                schedule.getStudentId()
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

    // Update
    public int update(Schedule schedule) throws SQLException {
        String query = "UPDATE Schedule SET schedule_type=?, schedule_title=?, schedule_start=?, schedule_end=?, schedule_repeat=?, schedule_place=?, memo=?, " +
                "category_id=? WHERE schedule_id=?";
        Object[] param = new Object[]{schedule.getScheduleType(), schedule.getScheduleTitle(), Timestamp.valueOf(schedule.getScheduleStart()),
                Timestamp.valueOf(schedule.getScheduleEnd()), schedule.getScheduleRepeat(), schedule.getSchedulePlace(), schedule.getScheduleMemo(), schedule.getCategoryId(),
                schedule.getScheduleId()};

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
    
    // Delete
    public int remove(int scheduleId) throws SQLException {
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

    // Read
    public List<Schedule> getSchedules(int userId) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE student_id = ? OR professor_id = ?";
        jdbcUtil.setSqlAndParameters(query, new Object[]{userId, userId});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Schedule> scheduleList = new ArrayList<>();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setScheduleType(rs.getInt("schedule_type"));
                schedule.setScheduleTitle(rs.getString("schedule_title"));
                schedule.setScheduleStart(rs.getTimestamp("schedule_start").toLocalDateTime());
                schedule.setScheduleEnd(rs.getTimestamp("schedule_end").toLocalDateTime());
                schedule.setScheduleRepeat(rs.getInt("schedule_repeat"));
                schedule.setSchedulePlace(rs.getString("schedule_place"));
                schedule.setScheduleMemo(rs.getString("memo"));
                schedule.setCategoryId(rs.getInt("category_id"));
                schedule.setProfessorId(rs.getInt("professor_id"));
                schedule.setStudentId(rs.getInt("student_id"));
                scheduleList.add(schedule);
            }
            return scheduleList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

}
