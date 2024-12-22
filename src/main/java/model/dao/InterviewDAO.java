package model.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.domain.Interview;

public class InterviewDAO {
	private JDBCUtil jdbcUtil = null;
	
	public InterviewDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	//면담 예약 생성
	public int createInterview(Interview interview) {
		String sql = "INSERT INTO interview(requested_date, interview_category, interview_note, interview_status, is_completed, created_at, updated_at, student_id, professor_id) VALUES (?, ?, ?, 'pending', 'N', SYSTIMESTAMP, SYSTIMESTAMP, ?, ?)";
		int result = 0;
		Object[] params = {
				Timestamp.valueOf(interview.getRequestedDate()),
				interview.getInterviewCategory(),
				interview.getInterviewNote(),
				interview.getStudentId(),
				interview.getProfessorId()
		};
		jdbcUtil.setSqlAndParameters(sql,  params);
		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			
			return result;
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return result;
	}
	//특정 학생 면담 다 불러오기
	public List<Interview> getInterviewListByStudentId(int studentId){
		List<Interview> interviews = new ArrayList<>();
		String sql = "SELECT * FROM interview WHERE student_id = ?";
		
		Object[] params = { studentId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while(rs.next()) {
				interviews.add( new Interview(
						rs.getInt("interview_id"),
						rs.getTimestamp("requested_date").toLocalDateTime(),
						rs.getString("interview_category"),
						rs.getString("interview_note"),
						rs.getString("interview_status"),
						rs.getString("is_completed"),
						rs.getInt("student_id"),
						rs.getInt("professor_id")
					));
			}
		} catch(Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();;
		} finally {
			jdbcUtil.close();
		}
		return interviews;
	}
	
	//특정 교수 면담 다 불러오기
	public List<Interview> getInterviewListByProfessorId(int professorId){
		List<Interview> interviews = new ArrayList<>();
		String sql = "SELECT * FROM interview WHERE student_id = ?";
		
		Object[] params = { professorId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while(rs.next()) {
				interviews.add( new Interview(
						rs.getInt("interview_id"),
						rs.getTimestamp("requested_date").toLocalDateTime(),
						rs.getString("interview_category"),
						rs.getString("interview_note"),
						rs.getString("interview_status"),
						rs.getString("is_completed"),
						rs.getInt("student_id"),
						rs.getInt("professor_id")
					));
			}
		} catch(Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();;
		} finally {
			jdbcUtil.close();
		}
		return interviews;
	}
	//특정 교수의 비승인 면담 다 불러오기
	public List<Interview> getInterviewListByProfessorIdAndStatus(int professorId){
		List<Interview> interviews = new ArrayList<>();
		String sql = "SELECT * FROM interview WHERE professor_id = ? AND interview_status = 'pending'";
		
		Object[] params = { professorId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while(rs.next()) {
				interviews.add( new Interview(
						rs.getInt("interview_id"),
						rs.getTimestamp("requested_date").toLocalDateTime(),
						rs.getString("interview_category"),
						rs.getString("interview_note"),
						rs.getString("interview_status"),
						rs.getString("is_completed"),
						rs.getInt("student_id"),
						rs.getInt("professor_id")
					));
			}
		} catch(Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();;
		} finally {
			jdbcUtil.close();
		}
		return interviews;
	}
	//인터뷰 한개 불러오기
	public Interview getInterviewById(int interviewId) {
		String sql = "SELECT * FROM interview WHERE interview_id = ?";
		
		Object[] params = { interviewId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				return new Interview(
						rs.getInt("interview_id"),
						rs.getTimestamp("requested_date").toLocalDateTime(),
						rs.getString("interview_category"),
						rs.getString("interview_note"),
						rs.getString("interview_status"),
						rs.getString("is_completed"),
						rs.getInt("student_id"),
						rs.getInt("professor_id")
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}
	//면담신청 수정
	public boolean updateInterview(Interview interview) {
		String sql = "UPDATE interview SET requested_date = ?, interview_category = ?, interview_note = ?, updated_at = SYSTIMESTAMP "
				+ "WHERE interview_id = ?";
		
		Object[] params = {
				interview.getRequestedDate(),
				interview.getInterviewCategory(),
				interview.getInterviewNote(),
				interview.getInterviewId()
		};
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			int result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			return result > 0;
		} catch(Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return false;
	}
	
	//면담신청 삭제
	public boolean deleteInterview(int interviewId) {
		String sql = "DELETE FROM interview WHERE interview_id = ?";
		
		Object[] params = { interviewId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			int result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			return result > 0;
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
			
		} finally {
			jdbcUtil.close();
		}
		return false;
	}
	
	//교수의 면담 신청 수락
	public boolean approveInterview(int interviewId) {
		String sql = "UPDATE interview SET interview_status = 'approved', updated_at = SYSTIMESTAMP"
				+ "WHERE interview_id = ?";
		Object[] params = { interviewId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			int result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			return result > 0;
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return false;
	}
	//교수의 면담 신청 반려
	public boolean rejectInterview (int interviewId) {
		String sql = "UPDATE interview SET interview_status = 'rejected', updated_at = SYSTIMESTAMP "
		           + "WHERE interview_id = ?";
		Object[] params = { interviewId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			int result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			return result > 0;
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return false;
	}
	
	//면담 완료 리스트 조회
	public List<Interview> getCompletedInterviewsByStudentId(int studentId){
		List<Interview> interviews = new ArrayList<>();
		String sql = "SELECT i.interview_id, i.requested_date, p.name AS professor_name, p.professor_id"
				+ " FROM interview i" 
				+ " JOIN professor p ON i.professor_id = p.professor_id"
				+ " WHERE i.student_id = ? AND TRIM(i.is_completed) = 'Y'";

		
		Object[] params = {studentId};
		
		jdbcUtil.setSqlAndParameters(sql, params);
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			 if (!rs.isBeforeFirst()) { // 데이터가 있는지 확인
			        System.out.println("No rows returned from the query.");
			    }
				while (rs.next()) {
				    interviews.add(new Interview(
				        rs.getInt("interview_id"),
				        rs.getTimestamp("requested_date").toLocalDateTime(),
				        studentId,
				        rs.getInt("professor_id"),
				        rs.getString("professor_name")
				    ));
				}
		} catch (Exception e) {
			e.printStackTrace();
			 System.out.println("Error Message: " + e.getMessage());
		} finally {
			jdbcUtil.close();
		}
		return interviews;
	
	}
	//학과별 교수 리스트 조회
	/*
	public List<Professor> getAllProfessors() {
		List<Professor> professors = new ArrayList<>();
		String sql = "SELECT * FROM processor WHERE deleted = 'N'";
		
		jdbcUtil.setSqlAndParameters(sql, null);
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while(rs.next()) {
				professors.add(new Professor (
						rs.getInt("professor_id"),
						rs.getString("name"),
						rs.getString("dept")
					));
			}
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return professors;
	}
	
	//이름으로 검색된 교수 반환
	public List<Professor> getProfessorsByName(String name) {
		List<Professor> professors = new ArrayList<>();
		String sql = "SELECT * FROM professor WHERE name = ?";
		
		Object[] params = { name }
		jdbcUtil.setSqlAndParameters(sql, params); 
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if(rs.next()) {
				professors.add(new Professor (
						rs.getInt("professor_id"),
						rs.getString("name"),
						rs.getString("dept")
				));
			}
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return professors;
	}

	public List<ProfessorReview> getReviewsByProfessorId(int professorId){
		List<ProfessorReview> reviews = new ArrayList<>();
		String sql = "SELECT * FROM professor_review WHERE interview_id IN (SELECT interview_id FROM interview WHERE professor_id = ?)";
		
		Object[] params = { professorId };
		
		jdbcUtil.setSqlAndParameters(sql, params);
		try {
			
			ResultSet rs = jdbcUtil.executeQuery();
			while(rs.next()) {
				reviews.add(new ProfessorReview(
						rs.getInt("review_id"),
						rs.getString("review_of_interview"),
						rs.getInt("review_rating")
					));
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return reviews;
	}
	*/

}
