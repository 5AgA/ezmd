package model.dao.mybatis.mapper;

import model.domain.Professor;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProfessorMapper {

    @Insert("INSERT INTO professor (name, email, password, dept, professor_office, deleted) " +
            "VALUES (#{name}, #{email}, #{password}, #{dept}, #{professorOffice}, #{deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "professorId", keyColumn = "professor_id")
    int insertProfessor(Professor professor);

    @Select("SELECT * FROM professor WHERE professor_id = #{professorId}")
    @Results({
            @Result(property = "professorId", column = "professor_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "dept", column = "dept"),
            @Result(property = "professorOffice", column = "professor_office"),
            @Result(property = "deleted", column = "deleted")
    })
    Professor getProfessorById(int professorId);

    @Select("SELECT * FROM professor WHERE deleted = 'N'")
    @Results({
            @Result(property = "professorId", column = "professor_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "dept", column = "dept"),
            @Result(property = "professorOffice", column = "professor_office"),
            @Result(property = "deleted", column = "deleted")
    })
    List<Professor> getAllProfessors();

    @Select("SELECT * FROM professor WHERE name LIKE '%' || #{keyword} || '%' AND deleted = 'N'")
    @Results({
            @Result(property = "professorId", column = "professor_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "dept", column = "dept"),
            @Result(property = "professorOffice", column = "professor_office"),
            @Result(property = "deleted", column = "deleted")
    })
    List<Professor> searchProfessorsByKeyword(String keyword);

    @Update("UPDATE professor SET name = #{name}, email = #{email}, password = #{password}, " +
            "dept = #{dept}, professor_office = #{professorOffice}, deleted = #{deleted} " +
            "WHERE professor_id = #{professorId}")
    int updateProfessor(Professor professor);

    @Delete("UPDATE professor SET deleted = 'Y' WHERE professor_id = #{professorId}")
    int softDeleteProfessor(int professorId);
}
