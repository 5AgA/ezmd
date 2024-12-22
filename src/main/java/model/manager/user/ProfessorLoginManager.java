
package model.manager.user;

import java.sql.SQLException;

import model.dao.ProfessorDAO;
import model.domain.Professor;
import org.mindrot.jbcrypt.BCrypt;

public class ProfessorLoginManager {
	private final ProfessorDAO professorDAO;

	public ProfessorLoginManager() {
		professorDAO = new ProfessorDAO();
	}

	/**
	 * 이메일과 비밀번호로 로그인 인증
	 * @param email 사용자의 이메일
	 * @param password 사용자의 비밀번호
	 * @return 인증에 성공하면 Professor 객체, 실패하면 null
	 * @throws SQLException 데이터베이스 오류 발생 시
	 */
	public Professor authenticate(String email, String password) throws SQLException {
		// 교수 테이블에서 이메일로 교수 정보 가져오기
		Professor professor = professorDAO.findProfPwdByEmail(email);
		if (professor != null) {
			// 비밀번호 확인
			if (BCrypt.checkpw(password, professor.getPassword())) {
				return professor;
			}
		}
		return null;
	}
}