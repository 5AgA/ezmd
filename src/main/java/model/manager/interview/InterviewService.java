package model.manager.interview;

import model.dao.mybatis.mapper.InterviewMapper;
import model.domain.Interview;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class InterviewService {
    private final SqlSessionFactory sqlSessionFactory;

    public InterviewService() {
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

    // 1. 특정 학생의 인터뷰 목록 가져오기
    public List<Interview> getInterviewsByStudentId(int studentId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InterviewMapper mapper = session.getMapper(InterviewMapper.class);
            return mapper.getInterviewsByStudentId(studentId);
        }
    }

    // 2. 인터뷰 ID로 특정 인터뷰 가져오기
    public Interview getInterviewById(int interviewId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InterviewMapper mapper = session.getMapper(InterviewMapper.class);
            return mapper.getInterviewById(interviewId);
        }
    }

    // 3. 새로운 인터뷰 추가
    public int addInterview(Interview interview) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InterviewMapper mapper = session.getMapper(InterviewMapper.class);

            interview.setCreatedAt(LocalDateTime.now());
            interview.setUpdatedAt(LocalDateTime.now());
            int rowsAffected = mapper.insertInterview(interview);

            session.commit();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 4. 인터뷰 업데이트
    public int updateInterview(Interview interview) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InterviewMapper mapper = session.getMapper(InterviewMapper.class);

            interview.setUpdatedAt(LocalDateTime.now());
            int rowsAffected = mapper.updateInterview(interview);

            session.commit();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int approveInterview(int interviewId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InterviewMapper mapper = session.getMapper(InterviewMapper.class);
            int rowsAffected = mapper.approveInterview(interviewId);

            session.commit();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 5. 인터뷰 삭제
    public int deleteInterview(int interviewId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InterviewMapper mapper = session.getMapper(InterviewMapper.class);
            int rowsAffected = mapper.deleteInterview(interviewId);

            session.commit();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
