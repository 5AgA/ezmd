package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.dao.mybatis.mapper.ScheduleMapper;
import model.domain.Schedule;

public class ScheduleDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ScheduleDAO() {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public Schedule create(Schedule schedule) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ScheduleMapper.class).insertSchedule(schedule);
            if (result > 0) {
                sqlSession.commit();
            }
            return schedule;
        } finally {
            sqlSession.close();
        }
    }

    public int update(Schedule schedule) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ScheduleMapper.class).updateSchedule(schedule);
            if (result > 0) {
                sqlSession.commit();
            }
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public int remove(int scheduleId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ScheduleMapper.class).deleteSchedule(scheduleId);
            if (result > 0) {
                sqlSession.commit();
            }
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public Schedule findSchedule(int scheduleId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.getMapper(ScheduleMapper.class).getScheduleById(scheduleId);
        } finally {
            sqlSession.close();
        }
    }

    public List<Schedule> findSchedulesByUserId(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.getMapper(ScheduleMapper.class).getSchedulesByUserId(userId);
        } finally {
            sqlSession.close();
        }
    }
}
