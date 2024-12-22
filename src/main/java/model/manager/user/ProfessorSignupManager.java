package model.manager.user;

import model.dao.ProfessorDAO;
import model.dao.StudentDAO;
import model.domain.Professor;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class ProfessorSignupManager {
	private ProfessorDAO professorDAO;
	private StudentDAO studentDAO;

	public ProfessorSignupManager() {
		professorDAO = new ProfessorDAO();
		studentDAO = new StudentDAO();
	}

	/**
	 * 이메일 중복 확인
	 * @param email 확인할 이메일
	 * @return 이메일이 이미 존재하면 true, 아니면 false
	 * @throws SQLException
	 */
	public boolean checkEmailExists(String email) throws SQLException {
		if(professorDAO.findUserByEmail(email)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 교수 데이터 저장
	 * @param professor 저장할 교수 객체
	 * @return 저장 성공 여부
	 * @throws SQLException
	 */
	public boolean registerProfessor(Professor professor) throws SQLException {
		// 비밀번호 해싱
		String hashedPassword = BCrypt.hashpw(professor.getPassword(), BCrypt.gensalt());
		professor.setPassword(hashedPassword);
		return professorDAO.createProfessor(professor) > 0;
	}
}