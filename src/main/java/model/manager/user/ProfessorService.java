package model.manager.user;

import model.dao.mybatis.mapper.ProfessorMapper;
import model.domain.Professor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ProfessorService {
    private SqlSessionFactory sqlSessionFactory;

    public ProfessorService() {
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

    public int addProfessor(Professor professor) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ProfessorMapper professorMapper = sqlSession.getMapper(ProfessorMapper.class);
            int result = professorMapper.insertProfessor(professor);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("addProfessor 실패", e);
        }
    }

    public Professor getProfessorById(int professorId) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ProfessorMapper professorMapper = sqlSession.getMapper(ProfessorMapper.class);
            Professor professor = professorMapper.getProfessorById(professorId);
            return professor;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getProfessorById 실패", e);
        }
    }

    public List<Professor> getAllProfessors() {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ProfessorMapper professorMapper = sqlSession.getMapper(ProfessorMapper.class);
            List<Professor> professors = professorMapper.getAllProfessors();
            return professors;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getProfessorsByDept 실패", e);
        }
    }

    public int updateProfessor(Professor professor) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ProfessorMapper professorMapper = sqlSession.getMapper(ProfessorMapper.class);
            int result = professorMapper.updateProfessor(professor);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("updateProfessor 실패", e);
        }
    }

    public int softDeleteProfessor(int professorId) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            ProfessorMapper professorMapper = sqlSession.getMapper(ProfessorMapper.class);
            int result = professorMapper.softDeleteProfessor(professorId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("softDeleteProfessor 실패", e);
        }
    }
}