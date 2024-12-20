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
    private ScheduleCategoryMapper scheduleCategoryMapper;

    public ScheduleCategoryService() {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            scheduleCategoryMapper = sqlSessionFactory.openSession().getMapper(ScheduleCategoryMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ScheduleService 초기화 실패", e);
        }
    }

    public List<ScheduleCategory> getCategoriesByUserId(int userId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ScheduleCategoryMapper mapper = sqlSession.getMapper(ScheduleCategoryMapper.class);
            return mapper.selectCategoriesByUserId(userId);
        }
    }
}
