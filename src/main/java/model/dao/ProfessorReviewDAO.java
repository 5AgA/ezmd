package model.dao;

import java.sql.ResultSet;

import model.domain.Professor;
import model.domain.ProfessorReview;
import model.domain.Student;
import java.sql.*;
public class ProfessorReviewDAO {
    private JDBCUtil jdbcUtil = null;

    public ProfessorReviewDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // 교수 후기 생성
    public int createProfessorReview(int interviewId, String reviewOfInterview, int reviewRating) {
        int result = 0;
        String query = "INSERT INTO ProfessorReview (interview_id, review_of_interview, review_rating, created_at, updated_at) VALUES (?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";
        Object[] params = {interviewId, reviewOfInterview, reviewRating};

        jdbcUtil.setSqlAndParameters(query, params);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit(); // 트랜잭션 커밋
        } catch (Exception ex) {
            jdbcUtil.rollback(); // 예외 발생 시 롤백
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // 자원 반환
        }
        return result;
    }

    // 리뷰 ID로 교수 후기 조회
    public ProfessorReview getProfessorReview(int reviewId) {
        String query = "SELECT * FROM ProfessorReview WHERE review_id = ?";
        Object[] params = {reviewId};

        jdbcUtil.setSqlAndParameters(query, params);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                ProfessorReview dto = new ProfessorReview();
                dto.setReviewId(rs.getInt("review_id"));
                dto.setInterviewId(rs.getInt("interview_id"));
                dto.setReviewOfInterview(rs.getString("review_of_interview"));
                dto.setReviewRating(rs.getInt("review_rating"));
                dto.setCreatedAt(rs.getTimestamp("created_at"));
                dto.setUpdatedAt(rs.getTimestamp("updated_at"));
                return dto;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // 기존 교수 후기 수정
    public int updateProfessorReview(int reviewId, String reviewOfInterview, int reviewRating) {
        int result = 0;
        String query = "UPDATE ProfessorReview SET review_of_interview = ?, review_rating = ?, updated_at = SYSTIMESTAMP WHERE review_id = ?";
        Object[] params = {reviewOfInterview, reviewRating, reviewId};

        jdbcUtil.setSqlAndParameters(query, params);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit(); // 커밋
        } catch (Exception ex) {
            jdbcUtil.rollback(); // 롤백
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 교수 후기 삭제
    public int deleteProfessorReview(int reviewId) {
        int result = 0;
        String query = "DELETE FROM ProfessorReview WHERE review_id = ?";
        Object[] params = {reviewId};

        jdbcUtil.setSqlAndParameters(query, params);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }
    
    public Professor findProfessorByEmail(String email) throws SQLException {
        String sql = "SELECT professor_id, name, email, password, phone, dept, professor_office, deleted FROM professor WHERE email=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{email});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return new Professor(
                    rs.getInt("professor_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone"),
                    rs.getString("dept"),
                    rs.getString("professor_office"),
                    rs.getString("deleted").charAt(0)
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
}
