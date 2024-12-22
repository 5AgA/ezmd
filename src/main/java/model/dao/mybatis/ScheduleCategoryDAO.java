package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.dao.mybatis.mapper.ScheduleCategoryMapper;
import model.domain.ScheduleCategory;

public class ScheduleCategoryDAO {
    private SqlSessionFactory sqlSessionFactory;

    public ScheduleCategoryDAO() {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public ScheduleCategory create(ScheduleCategory scheduleCategory) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ScheduleCategoryMapper.class).insertScheduleCategory(scheduleCategory);
            if (result > 0) {
                sqlSession.commit();
            }
            return scheduleCategory;
        } finally {
            sqlSession.close();
        }
    }

    public int update(ScheduleCategory scheduleCategory) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ScheduleCategoryMapper.class).updateScheduleCategory(scheduleCategory);
            if (result > 0) {
                sqlSession.commit();
            }
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public int remove(int categoryId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ScheduleCategoryMapper.class).deleteScheduleCategory(categoryId);
            if (result > 0) {
                sqlSession.commit();
            }
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public ScheduleCategory findCategory(int categoryId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.getMapper(ScheduleCategoryMapper.class).selectScheduleCategoryById(categoryId);
        } finally {
            sqlSession.close();
        }
    }

    public List<ScheduleCategory> findCategoriesByUserId(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.getMapper(ScheduleCategoryMapper.class).selectCategoriesByUserId(userId);
        } finally {
            sqlSession.close();
        }
    }
}
