package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Student;

public class StudentDAO {

    private JDBCUtil jdbcUtil;

    public StudentDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // Create - 학생 추가
    public int createStudent(Student student) {
        String sql = "INSERT INTO student (student_id, name, email, password, phone, dept, grade, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {
            student.getStudentId(),
            student.getName(),
            student.getEmail(),
            student.getPassword(),
            student.getPhone(),
            student.getDept(),
            student.getGrade(),
            student.getDeleted()
        };
        jdbcUtil.setSqlAndParameters(sql, params);

        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // Read - ID로 학생 조회
    public Student findStudentById(int studentId) throws SQLException {
        String sql = "SELECT student_id, name, email, password, phone, dept, grade, deleted "
                   + "FROM student WHERE student_id=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{studentId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone"),
                    rs.getString("dept"),
                    rs.getInt("grade"),
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

    // Read - 전체 학생 조회
    public List<Student> findAllStudents() throws SQLException {
        String sql = "SELECT student_id, name, email, password, phone, dept, grade, deleted FROM student ORDER BY student_id";
        jdbcUtil.setSqlAndParameters(sql, null);

        List<Student> studentList = new ArrayList<>();
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                studentList.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone"),
                    rs.getString("dept"),
                    rs.getInt("grade"),
                    rs.getString("deleted").charAt(0)
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return studentList;
    }

    // Update - 학생 정보 수정
    public int updateStudent(Student student) {
        String sql = "UPDATE student SET name=?, email=?, password=?, phone=?, dept=?, grade=?, deleted=? WHERE student_id=?";
        Object[] params = {
            student.getName(),
            student.getEmail(),
            student.getPassword(),
            student.getPhone(),
            student.getDept(),
            student.getGrade(),
            student.getDeleted(),
            student.getStudentId()
        };
        jdbcUtil.setSqlAndParameters(sql, params);

        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // Delete - 학생 삭제
    public int deleteStudent(Student student) {
        String sql = "DELETE FROM student WHERE student_id=?";
        Object[] params = {student.getStudentId()};
        jdbcUtil.setSqlAndParameters(sql, params);

        try {
            int result = jdbcUtil.executeUpdate();
            if (result > 0) {
                student.setDeleted('Y');  // 삭제 성공 시 deleted 업데이트
            }
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }
}