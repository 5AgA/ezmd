package model.dao.mybatis.mapper;

import model.domain.Interview;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InterviewMapper {

    // 특정 학생의 인터뷰 가져오기
    @Select("SELECT * FROM interview WHERE student_id = #{studentId}")
    @Results({
            @Result(property = "interviewId", column = "interview_id"),
            @Result(property = "requestedDate", column = "requested_date"),
            @Result(property = "interviewCategory", column = "interview_category"),
            @Result(property = "interviewNote", column = "interview_note"),
            @Result(property = "interviewStatus", column = "interview_status"),
            @Result(property = "interviewNotice", column = "interview_notice"),
            @Result(property = "isCompleted", column = "is_completed"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "professorId", column = "professor_id")
    })
    List<Interview> getInterviewsByStudentId(int studentId);

    // 특정 교수의 인터뷰 가져오기
    @Select("SELECT * FROM interview WHERE professor_id = #{professorId}")
    @Results({
            @Result(property = "interviewId", column = "interview_id"),
            @Result(property = "requestedDate", column = "requested_date"),
            @Result(property = "interviewCategory", column = "interview_category"),
            @Result(property = "interviewNote", column = "interview_note"),
            @Result(property = "interviewStatus", column = "interview_status"),
            @Result(property = "interviewNotice", column = "interview_notice"),
            @Result(property = "isCompleted", column = "is_completed"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "professorId", column = "professor_id")
    })
    List<Interview> getInterviewsByProfId(int professorId);

    // 인터뷰 ID로 가져오기
    @Select("SELECT * FROM interview WHERE interview_id = #{interviewId}")
    @Results({
            @Result(property = "interviewId", column = "interview_id"),
            @Result(property = "requestedDate", column = "requested_date"),
            @Result(property = "interviewCategory", column = "interview_category"),
            @Result(property = "interviewNote", column = "interview_note"),
            @Result(property = "interviewStatus", column = "interview_status"),
            @Result(property = "interviewNotice", column = "interview_notice"),
            @Result(property = "isCompleted", column = "is_completed"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "professorId", column = "professor_id")
    })
    Interview getInterviewById(int interviewId);

    // 새로운 인터뷰 추가
    @Insert("INSERT INTO interview (requested_date, interview_category, interview_note, interview_status, interview_notice, " +
            "is_completed, created_at, updated_at, student_id, professor_id) " +
            "VALUES (#{requestedDate}, #{interviewCategory}, #{interviewNote}, #{interviewStatus}, #{interviewNotice}, " +
            "#{isCompleted}, #{createdAt}, #{updatedAt}, #{studentId}, #{professorId})")
    @Options(useGeneratedKeys = true, keyProperty = "interviewId", keyColumn = "interview_id")
    int insertInterview(Interview interview);

    // 인터뷰 업데이트
    @Update("UPDATE interview SET requested_date = #{requestedDate}, interview_category = #{interviewCategory}, " +
            "interview_note = #{interviewNote}, interview_status = #{interviewStatus}, interview_notice = #{interviewNotice}, " +
            "is_completed = #{isCompleted}, updated_at = #{updatedAt}, student_id = #{studentId}, professor_id = #{professorId} " +
            "WHERE interview_id = #{interviewId}")
    int updateInterview(Interview interview);

    @Update("UPDATE interview SET interview_status = 'approved' WHERE interview_id = #{interviewId}")
    int approveInterview(int interviewId);

    // 인터뷰 삭제
    @Delete("DELETE FROM interview WHERE interview_id = #{interviewId}")
    int deleteInterview(int interviewId);
}
