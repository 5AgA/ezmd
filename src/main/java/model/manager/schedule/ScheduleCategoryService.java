package model.manager.schedule;

import model.dao.mybatis.mapper.ScheduleCategoryMapper;
import model.dao.mybatis.mapper.ScheduleMapper;
import model.domain.ScheduleCategory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ScheduleCategoryService {
    private SqlSessionFactory sqlSessionFactory;

    public ScheduleCategoryService() {
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

    public List<ScheduleCategory> getCategoriesByUserId(int userId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleCategoryMapper scheduleCategoryMapper = sqlSession.getMapper(ScheduleCategoryMapper.class);
            return scheduleCategoryMapper.selectCategoriesByUserId(userId);
        }
    }

    public int addScheduleCategory(ScheduleCategory category) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleCategoryMapper scheduleCategoryMapper = sqlSession.getMapper(ScheduleCategoryMapper.class);
            int result = scheduleCategoryMapper.insertScheduleCategory(category);
            sqlSession.commit();
            return result;
        }
    }
}
