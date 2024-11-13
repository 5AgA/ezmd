package model.dao;

import model.domain.Schedule;

import java.sql.ResultSet;
import java.util.List;

public class ScheduleDAO {
    private JDBCUtil jdbcUtil = null;

    public ScheduleDAO() {
        jdbcUtil = new JDBCUtil();
    }

    public Schedule create(Schedule schedule) {
        String query = "INSERT INTO Schedule VALUES (schedule_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] param = new Object[]{schedule.getScheduleType(), schedule.getScheduleTitle(), schedule.getScheduleStart(),
                schedule.getScheduleEnd(), schedule.getScheduleRepeat(), schedule.getSchedulePlace(), schedule.getScheduleMemo(), schedule.getCategoryId(),
                schedule.getProfessorId(), schedule.getStudentId()};

        String key[] = {"scheduleId"};
        try {
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

    public int update(Schedule schedule) {
        String query = "UPDATE Schedule SET schedule_type=?, schedule_title=?, schedule_start=?, schedule_end=?, schedule_repeat=?, schedule_place=?, memo=?, " +
                "category_id=? WHERE shcedule_id=?";
        Object[] param = new Object[]{schedule.getScheduleType(), schedule.getScheduleTitle(), schedule.getScheduleStart(),
                schedule.getScheduleEnd(), schedule.getScheduleRepeat(), schedule.getSchedulePlace(), schedule.getScheduleMemo(), schedule.getCategoryId(),
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

    public List<Schedule> findScheduleByStartDate(String startDate) {
        String qeury = "SELECT * FROM Schedule WHERE schedule_start=TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')";
        List<Schedule> scheduleList = null;
        jdbcUtil.setSqlAndParameters(qeury, new Object[]{startDate});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                do {
                    Schedule s = new Schedule();
                    s.setScheduleId(rs.getInt("schedule_id"));
                    s.setScheduleType(rs.getInt("schedule_type"));
                    s.setScheduleTitle(rs.getString("schedule_title"));
                    s.setScheduleStart(rs.getTimestamp("schedule_start"));
                    s.setScheduleEnd(rs.getTimestamp("schedule_end"));
                    s.setScheduleRepeat(rs.getInt("schedule_repeat"));
                    s.setSchedulePlace(rs.getString("schedule_place"));
                    s.setScheduleMemo(rs.getString("memo"));
                    s.setCategoryId(rs.getInt("category_id"));
                    s.setProfessorId(rs.getInt("professor_id"));
                    s.setStudentId(rs.getInt("student_id"));
                    scheduleList.add(s);
                } while(rs.next());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return scheduleList;
    }
}
