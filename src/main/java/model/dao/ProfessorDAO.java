package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Professor;
import model.domain.Student;

import static java.sql.DriverManager.getConnection;

public class ProfessorDAO {

    private JDBCUtil jdbcUtil;

    public ProfessorDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // Create - 교수 추가
    public int createProfessor(Professor professor) {
        String sql = "INSERT INTO professor (professor_id, name, email, password, dept, professor_office, deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {
            professor.getProfessorId(),
            professor.getName(),
            professor.getEmail(),
            professor.getPassword(),
            professor.getDept(),
            professor.getProfessorOffice(),
            professor.getDeleted()
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

    // Read - ID로 교수 조회
    public Professor findProfessorById(int professorId) throws SQLException {
        String sql = "SELECT professor_id, name, email, password, dept, professor_office, deleted "
                   + "FROM professor WHERE professor_id=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{professorId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return new Professor(
                    rs.getInt("professor_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
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

    // Read - 전체 교수 조회
    public List<Professor> findAllProfessors() throws SQLException {
        String sql = "SELECT professor_id, name, email, password, dept, professor_office, deleted FROM professor ORDER BY professor_id";
        jdbcUtil.setSqlAndParameters(sql, null);

        List<Professor> professorList = new ArrayList<>();
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                professorList.add(new Professor(
                    rs.getInt("professor_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("dept"),
                    rs.getString("professor_office"),
                    rs.getString("deleted").charAt(0)
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return professorList;
    }

    // Update - 교수 정보 수정
    public int updateProfessor(Professor professor) {
        String sql = "UPDATE professor SET name=?, email=?, password=?, dept=?, professor_office=?, deleted=? WHERE professor_id=?";
        Object[] params = {
            professor.getName(),
            professor.getEmail(),
            professor.getPassword(),
            professor.getDept(),
            professor.getProfessorOffice(),
            professor.getDeleted(),
            professor.getProfessorId()
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

    // Delete - 교수 삭제
    public int deleteProfessor(Professor professor) {
        String sql = "DELETE FROM professor WHERE professor_id=?";
        Object[] params = {professor.getProfessorId()};
        jdbcUtil.setSqlAndParameters(sql, params);

        try {
            int result = jdbcUtil.executeUpdate();
            if (result > 0) {
                professor.setDeleted('Y');  // 삭제 성공 시 deleted 업데이트
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


    public boolean findUserByEmail(String email) throws SQLException {
    	String sql = "SELECT COUNT(*) as count FROM ("
    	           + "SELECT email FROM STUDENT WHERE email = ? "
    	           + "UNION "
    	           + "SELECT email FROM PROFESSOR WHERE email = ?"
    	           + ")";
    	
        jdbcUtil.setSqlAndParameters(sql, new Object[]{email, email});
        try{
        	ResultSet rs= jdbcUtil.executeQuery();
        	if(rs.next()) {
        		return rs.getInt("count") > 0;
        	}
        	
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            jdbcUtil.close();
        }
        return false;
    }
    
    public Professor findProfPwdByEmail(String email){
        String sql = "SELECT * FROM professor WHERE email = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{email});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("professor_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("dept"),
                        rs.getString("professor_office"),
                        rs.getString("deleted").charAt(0)
                );
                return professor;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
}
