package model.manager.interview;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.InterviewDAO;
import model.domain.Interview;
import model.dto.InterviewDTO;
import model.dao.StudentDAO;

public class InterviewManager {

    private final InterviewDAO interviewDAO = new InterviewDAO();
    private final StudentDAO studentDAO = new StudentDAO();

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

    //특정 교수 ID와 STATUS로 면담 리스트 조회
    public List<InterviewDTO> getInterviewListByProfessorIdAndStatus(int professorId) throws SQLException{
    	
    	List<Interview> interviews = interviewDAO.getInterviewListByProfessorIdAndStatus(professorId);
    	List<InterviewDTO> dtos = new ArrayList<>();
        
    	// 학생 정보 조회 로직 추가 (예: DB에서 가져오기)
    	for(Interview interview : interviews) {
    		String studentName = studentDAO.getStudentNameById(interview.getStudentId());
    		String studentMajor = studentDAO.getStudentMajorById(interview.getStudentId());
    		String studentId = String.valueOf(interview.getStudentId());
            System.out.println("인터뷰 데이터: " + studentName);

    		InterviewDTO dto = new InterviewDTO();
    	        dto.setInterviewId(interview.getInterviewId());
    	        dto.setStudentName(studentName);
    	        dto.setStudentMajor(studentMajor);
    	        dto.setStudentId(studentId);
    	        dto.setRequestedDate(interview.getRequestedDate());
    	        dto.setInterviewCategory(interview.getInterviewCategory());
    	        dto.setInterviewNote(interview.getInterviewNote());
    	        dto.setCreatedAt(interview.getCreatedAt());
    	       
    	        dtos.add(dto);

    	}
    	return dtos;
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