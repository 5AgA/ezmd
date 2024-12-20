package model.dao.mybatis.mapper;

import java.time.LocalDateTime;
import java.util.List;
import model.domain.Schedule;
import org.apache.ibatis.annotations.*;

public interface ScheduleMapper {
    // 특정 사용자의 스케줄 가져오기
    @Select("SELECT * FROM schedule WHERE user_id = #{userId}")
    @Results({
            @Result(property = "scheduleId", column = "schedule_id"),
            @Result(property = "scheduleTitle", column = "schedule_title"),
            @Result(property = "scheduleStart", column = "schedule_start"),
            @Result(property = "scheduleEnd", column = "schedule_end"),
            @Result(property = "scheduleRepeat", column = "schedule_repeat"),
            @Result(property = "schedulePlace", column = "schedule_place"),
            @Result(property = "scheduleMemo", column = "schedule_memo"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "userId", column = "user_id")
    })
    List<Schedule> getSchedulesByUserId(int userId);

    @Select("SELECT * FROM schedule WHERE "
            + "(schedule_start <= #{endDate} AND schedule_end >= #{startDate}) "
            + "AND user_id = #{userId}")
    @Results({
            @Result(property = "scheduleId", column = "schedule_id"),
            @Result(property = "scheduleTitle", column = "schedule_title"),
            @Result(property = "scheduleStart", column = "schedule_start"),
            @Result(property = "scheduleEnd", column = "schedule_end"),
            @Result(property = "scheduleRepeat", column = "schedule_repeat"),
            @Result(property = "schedulePlace", column = "schedule_place"),
            @Result(property = "scheduleMemo", column = "schedule_memo"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "userId", column = "user_id")
    })
    List<Schedule> getSchedulesByDate(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 스케줄 ID로 가져오기
    @Select("SELECT * FROM schedule WHERE schedule_id = #{scheduleId}")
    @Results({
            @Result(property = "scheduleId", column = "schedule_id"),
            @Result(property = "scheduleTitle", column = "schedule_title"),
            @Result(property = "scheduleStart", column = "schedule_start"),
            @Result(property = "scheduleEnd", column = "schedule_end"),
            @Result(property = "scheduleRepeat", column = "schedule_repeat"),
            @Result(property = "schedulePlace", column = "schedule_place"),
            @Result(property = "scheduleMemo", column = "schedule_memo"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "userId", column = "user_id")
    })
    Schedule getScheduleById(int scheduleId);

    // 새로운 스케줄 추가
    @Insert("INSERT INTO schedule (schedule_title, schedule_start, schedule_end, schedule_repeat, schedule_place, schedule_memo, category_id, user_id) " +
            "VALUES (#{scheduleTitle}, #{scheduleStart}, #{scheduleEnd}, #{scheduleRepeat}, #{schedulePlace}, #{scheduleMemo}, #{categoryId}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "scheduleId", keyColumn = "schedule_id")
    int insertSchedule(Schedule schedule);

    // 스케줄 업데이트
    @Update("UPDATE schedule SET schedule_title = #{scheduleTitle}, schedule_start = #{scheduleStart}, schedule_end = #{scheduleEnd}, " +
            "schedule_repeat = #{scheduleRepeat}, schedule_place = #{schedulePlace}, schedule_memo = #{scheduleMemo}, " +
            "category_id = #{categoryId} WHERE schedule_id = #{scheduleId}")
    int updateSchedule(Schedule schedule);

    // 스케줄 삭제
    @Delete("DELETE FROM schedule WHERE schedule_id = #{scheduleId}")
    int deleteSchedule(int scheduleId);
}
