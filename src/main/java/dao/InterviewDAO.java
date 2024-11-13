package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Interview;

public class InterviewDAO {

	private JDBCUtil jdbcUtil = null;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public InterviewDAO(){
		jdbcUtil = new JDBCUtil();
	}
	public InterviewDAO(Connection conn) {
		this.conn = conn;
	}
	
	//면담 예약 생성
	public boolean createInterview(Interview interview) {
		String sql = "INSERT INTO interview(interview_id, requested_date, interview_category, interview_note, interview_status, is_completed, created_at, updated_at, student_id, professor_id)" 
				+ "VALUES (INTERVIEW_SEQ.NEXTVAL, ?, ?, ?, 'pending', 'N', SYSDATE, SYSDATE, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, interview.getRequestedDate());
			pstmt.setString(2, interview.getInterviewCategory());
			pstmt.setString(3,  interview.getInterviewNote());
			pstmt.setInt(4, interview.getStudentId());
			pstmt.setInt(5, interview.getProfessorId());
			int result = pstmt.executeUpdate();
			
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	//인터뷰 불러오기
	public Interview getInterviewById(int interviewId) {
		String sql = "SELECT * FROM interview WHERE interview_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, interviewId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Interview(
						rs.getInt("interview_id"),
						rs.getDate("requested_date"),
						rs.getString("interview_category"),
						rs.getString("interview_note"),
						rs.getString("interview_status"),
						rs.getString("is_completed"),
						rs.getInt("student_id"),
						rs.getInt("professor_id")
					);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
	//면담신청 수정
	public boolean updatedInterview(Interview interview) {
		String sql = "UPDATE interview SET requested_date = ?, interview_category = ?, interview_note = ?, updated_at = SYSDATE "
				+ "WHERE interview_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, interview.getRequestedDate());
			pstmt.setString(2, interview.getInterviewCategory());
			pstmt.setString(3, interview.getInterviewNote());
			pstmt.setInt(4, interview.getInterviewId());
			
			int result = pstmt.executeUpdate();
			return result > 0;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	//면담신청 삭제
	public boolean deleteInterview(int interviewId) {
		String sql = "DELETE FROM interview WHERE interview_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, interviewId);
			
			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		return false;
	}
	
	//교수의 면담 신청 수락
	public boolean approveInterview(int interviewId) {
		String sql = "UPDATE interview SET interview_status = 'approved', updated_at = SYSDATE"
				+ "WHERE interview_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(interviewId, interviewId);
			
			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	//교수의 면담 신청 반려
	public boolean rejectInterview (int interviewId) {
		String sql = "UPDATE interview SETinterview_status = 'rejected', update_at = SYSDATE"
				+ "WHERE interview_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, interviewId);
			
			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	//학과별 교수 리스트 조회
	public List<Professor> getAllProfessors() {
		List<Professor> professors = new ArrayList<>();
		
		String sql = "SELECT * FROM processor WHERE deleted = 'N'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				professors.add(new Professor (
						rs.getInt("professor_id"),
						rs.getString("name"),
						rs.getString("dept")
					));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return professors;
	}
	
	//이름으로 검색된 교수 반환
	public List<Professor> getProfessorsByName(String name) {
		List<Professor> professors = new ArrayList<>();
		String sql = "SELECT * FROM professor WHERE name = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				professors.add(new Professor (
						rs.getInt("professor_id"),
						rs.getString("name"),
						rs.getString("dept")
					));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return professors;
	}
	
	public List<ProfessorReview> getReviewsByProfessorId(int professorId){
		List<ProfessorReview> reviews = new ArrayList<>();
		String sql = "SELECT * FROM professor_review WHERE interview_id IN (SELECT interview_id FROM interview WHERE professor_id = ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, professorId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				reviews.add(new ProfessorReview(
						rs.getInt("review_id"),
						rs.getString("review_of_interview"),
						rs.getInt("review_rating")
					));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return reviews;
	}
	private void close() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
