package model.dao.mybatis.mapper;

import model.domain.ScheduleCategory;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface ScheduleCategoryMapper {

    // 특정 사용자 아이디(userId)에 해당하는 카테고리 목록 조회
    @Select("SELECT * FROM schedule_category WHERE user_id = #{userId}")
    List<ScheduleCategory> selectCategoriesByUserId(int userId);

    // 일정 카테고리 추가
    @Insert("INSERT INTO schedule_category (category_id, category_name, category_color, user_id) " +
            "VALUES (#{categoryId}, #{categoryName}, #{categoryColor}, #{userId})")
    int insertScheduleCategory(ScheduleCategory scheduleCategory);

    // 일정 카테고리 조회 (ID로 검색)
    @Select("SELECT * FROM schedule_category WHERE category_id = #{categoryId}")
    ScheduleCategory selectScheduleCategoryById(int categoryId);

    // 일정 카테고리 수정
    @Update("UPDATE schedule_category SET category_name = #{categoryName}, category_color = #{categoryColor}, user_id = #{userId} " +
            "WHERE category_id = #{categoryId}")
    int updateScheduleCategory(ScheduleCategory scheduleCategory);

    // 일정 카테고리 삭제
    @Delete("DELETE FROM schedule_category WHERE category_id = #{categoryId}")
    int deleteScheduleCategory(int categoryId);
}
