package model.dao;

import java.sql.ResultSet;
import model.domain.InterviewResult;

public class InterviewResultDAO {
    private JDBCUtil jdbcUtil = null;

    public InterviewResultDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // 면담 결과 생성
    public int createInterviewResult(String interviewTopic, String interviewSummary) {
        int result = 0;
        String query = "INSERT INTO InterviewResult (interview_topic, interview_summary, created_time) VALUES (?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";
        Object[] params = {interviewTopic, interviewSummary};
        
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

    // 면담 ID로 면담 결과 조회
    public InterviewResult getInterviewResult(int interviewId) {
        String query = "SELECT * FROM InterviewResult WHERE interview_id = ?";
        Object[] params = {interviewId};
        
        jdbcUtil.setSqlAndParameters(query, params);
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                InterviewResult dto = new InterviewResult();
                dto.setInterviewId(rs.getInt("interview_id"));
                dto.setInterviewTopic(rs.getString("interview_topic"));
                dto.setInterviewSummary(rs.getString("interview_summary"));
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

    // 기존 면담 결과 수정
    public int updateInterviewResult(int interviewId, String interviewTopic, String interviewSummary) {
        int result = 0;
        String query = "UPDATE InterviewResult SET interview_topic = ?, interview_summary = ?, updated_at = SYSTIMESTAMP WHERE interview_id = ?";
        Object[] params = {interviewTopic, interviewSummary};
        
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

    // 면담 결과 삭제
    public int deleteInterviewResult(int interviewId) {
        int result = 0;
        String query = "DELETE FROM InterviewResult WHERE interview_id = ?";
        Object[] params = {interviewId};
        
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
}
