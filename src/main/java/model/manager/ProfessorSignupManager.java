package model.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.ProfessorDAO;
import model.dao.StudentDAO;
import model.domain.Professor;

import org.mindrot.jbcrypt.BCrypt;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProfessorSignupManager {
	private DataSource dataSource;

	public ProfessorSignupManager() {
		try {
			// JNDI를 통한 데이터소스 가져오기 (웹 애플리케이션 서버 설정 필요)
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/university_db");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("데이터소스 초기화 실패", e);
		}
	}

	/**
	 * 이메일 중복 확인
	 * @param email 확인할 이메일
	 * @return 이메일이 이미 존재하면 true, 아니면 false
	 * @throws SQLException
	 */
	public boolean checkEmailExists(String email) throws SQLException {
		String query = "SELECT COUNT(*) FROM professors WHERE email = ?";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

	/**
	 * 교수 데이터 저장
	 * @param professor 저장할 교수 객체
	 * @return 저장 성공 여부
	 * @throws SQLException
	 */
	public boolean registerProfessor(Professor professor) throws SQLException {
		String insertQuery = "INSERT INTO professors (professor_id, name, email, password_hash, department, office_location, deleted) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

			stmt.setInt(1, professor.getProfessorId());
			stmt.setString(2, professor.getName());
			stmt.setString(3, professor.getEmail());
			// 비밀번호 해싱
			String hashedPassword = BCrypt.hashpw(professor.getPassword(), BCrypt.gensalt());
			stmt.setString(4, hashedPassword);
			stmt.setString(5, professor.getDept());
			stmt.setString(6, professor.getProfessorOffice());
			stmt.setString(7, String.valueOf(professor.getDeleted()));

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		}
	}
}