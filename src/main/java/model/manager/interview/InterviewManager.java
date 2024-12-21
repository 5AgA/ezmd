package model.manager.interview;

import java.util.List;
import model.dao.InterviewDAO;
import model.domain.Interview;

public class InterviewManager {

    private final InterviewDAO interviewDAO = new InterviewDAO();

    // 면담 예약 생성
    public int createInterview(Interview interview) {
        return interviewDAO.createInterview(interview);
    }

    // 특정 학생 ID로 면담 리스트 조회
    public List<Interview> getInterviewListByStudentId(int studentId) {
        return interviewDAO.getInterviewListByStudentId(studentId);
    }
    
    // 특정 교수 ID로 면담 리스트 조회
    public List<Interview> getInterviewListByProfessorId(int professorId) {
        return interviewDAO.getInterviewListByProfessorId(professorId);
    }

    // 특정 면담 ID로 면담 조회
    public Interview getInterviewById(int interviewId) {
        return interviewDAO.getInterviewById(interviewId);
    }

    // 면담 업데이트
    public boolean updateInterview(Interview interview) {
        return interviewDAO.updateInterview(interview);
    }

    // 면담 삭제
    public boolean deleteInterview(int interviewId) {
        return interviewDAO.deleteInterview(interviewId);
    }

    // 교수의 면담 승인
    public boolean approveInterview(int interviewId) {
        return interviewDAO.approveInterview(interviewId);
    }

    // 교수의 면담 반려
    public boolean rejectInterview(int interviewId) {
        // SQL 문법 오류 수정: updated_at 컬럼명 사용
        return interviewDAO.rejectInterview(interviewId);
    }
}