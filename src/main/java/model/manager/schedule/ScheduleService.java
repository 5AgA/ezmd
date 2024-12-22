package model.manager.schedule;

import model.dao.mybatis.mapper.ScheduleMapper;
import model.domain.Schedule;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ScheduleService {
    private SqlSessionFactory sqlSessionFactory;

    public ScheduleService() {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ScheduleService 초기화 실패", e);
        }
    }

    public List<Schedule> getSchedulesByUserId(int userId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = sqlSession.getMapper(ScheduleMapper.class);
            return scheduleMapper.getSchedulesByUserId(userId);
        }
    }

    // 특정 날짜에 해당하는 스케줄 가져오기
    public List<Schedule> getSchedulesByDate(int userId, String date) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = sqlSession.getMapper(ScheduleMapper.class);
            return scheduleMapper.getSchedulesByDate(userId, Schedule.parseDateTime(date + " 00:00"), Schedule.parseDateTime(date + " 23:59"));
        }
    }

    public Schedule getScheduleById(int scheduleId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = sqlSession.getMapper(ScheduleMapper.class);
            return scheduleMapper.getScheduleById(scheduleId);
        }
    }

    public int addSchedule(Schedule schedule) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = sqlSession.getMapper(ScheduleMapper.class);
            int result = scheduleMapper.insertSchedule(schedule);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("스케줄 추가 실패", e);
        }
    }

    public int updateSchedule(Schedule schedule) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = sqlSession.getMapper(ScheduleMapper.class);
            int result = scheduleMapper.updateSchedule(schedule);
            sqlSession.commit();
            return result;
        }
    }

    public int deleteSchedule(int scheduleId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = sqlSession.getMapper(ScheduleMapper.class);
            int result = scheduleMapper.deleteSchedule(scheduleId);
            sqlSession.commit();
            return result;
        }
    }
}
