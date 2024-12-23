package model.dao;

import java.sql.ResultSet;
import model.domain.InterviewResult;
import model.domain.ProfessorReview;

public class InterviewResultDAO {
    private JDBCUtil jdbcUtil = null;

    public InterviewResultDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // 면담 결과 생성
    public int createInterviewResult(int interviewId, String interviewTopic, String interviewSummary, String reviewOfInterview, int reviewRating) {
        int result = 0;
        String insertInterviewResultQuery = "BEGIN"
        		+ " INSERT INTO interview_result (interview_id, interview_topic, interview_summary, created_at)"
        		+ " VALUES (?, ?, ?, systimestamp);"
        		+ " INSERT INTO professor_review (interview_id, review_of_interview, review_rating, created_at)"
        		+ " VALUES (?, ?, ?, systimestamp);"
        		+ "END;";
        
          try {
            jdbcUtil.setSqlAndParameters(insertInterviewResultQuery, new Object[] {interviewId, interviewTopic, interviewSummary, interviewId, reviewOfInterview, reviewRating});
            result += jdbcUtil.executeUpdate();
        
        } catch (Exception ex) {
            // 롤백
            jdbcUtil.rollback();
            ex.printStackTrace();
            result = 0; // 오류 발생 시 결과 초기화
        } finally {
        	jdbcUtil.commit();
            jdbcUtil.close();
        }

        return result;
    }
    
 // 면담 결과 생성
    public int updateInterviewResult(int interviewId, String interviewTopic, String interviewSummary, String reviewOfInterview, int reviewRating) {
        int result = 0;
        String updateInterviewResultQuery =  "BEGIN "
        	    + "UPDATE interview_result "
        	    + "SET interview_topic = ?, interview_summary = ?, updated_at = systimestamp "
        	    + "WHERE interview_id = ?; "
        	    + "UPDATE professor_review "
        	    + "SET review_of_interview = ?, review_rating = ?, updated_at = systimestamp "
        	    + "WHERE interview_id = ?; "
        	    + "END;";
        
        try {
           
            jdbcUtil.setSqlAndParameters(updateInterviewResultQuery, new Object[] {interviewTopic, interviewSummary, interviewId, reviewOfInterview, reviewRating, interviewId});
            result += jdbcUtil.executeUpdate();
         
        } catch (Exception ex) {
            // 롤백
            jdbcUtil.rollback();
            ex.printStackTrace();
            result = 0; // 오류 발생 시 결과 초기화
        } finally {
        	jdbcUtil.commit();
            jdbcUtil.close();
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

    public InterviewResult getSavedData(int interviewId) {
    	String query = "SELECT ir.interview_id, ir.interview_topic, ir.interview_summary, "
                + "pr.review_of_interview, pr.review_rating "
                + "FROM interview_result ir "
                + "JOIN professor_review pr "
                + "ON ir.interview_id = pr.interview_id "
                + "WHERE ir.interview_id = ?";

        Object[] params = {interviewId};

        jdbcUtil.setSqlAndParameters(query, params);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                // Create and populate InterviewResult object with the retrieved data
                InterviewResult interviewResult = new InterviewResult();
                interviewResult.setInterviewId(rs.getInt("interview_id"));
                interviewResult.setInterviewTopic(rs.getString("interview_topic"));
                interviewResult.setInterviewSummary(rs.getString("interview_summary"));
                interviewResult.setReviewOfInterview(rs.getString("review_of_interview"));
                interviewResult.setReviewRating(rs.getInt("review_rating"));
                return interviewResult;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    public boolean isInterviewResultExists(int interviewId) {
        String query = "SELECT COUNT(*) AS count FROM interview_result WHERE interview_id = ?";
        Object[] params = {interviewId};

        jdbcUtil.setSqlAndParameters(query, params);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("isInterviewResultExists - interviewId: " + interviewId + ", count: " + count); // 디버깅 로그
                return count > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }

}
