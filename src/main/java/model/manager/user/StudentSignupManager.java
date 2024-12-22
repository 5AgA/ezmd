
package model.manager.user;

import java.sql.SQLException;

import model.dao.ProfessorDAO;
import model.dao.StudentDAO;
import model.domain.Professor;
import model.domain.Student;
import org.mindrot.jbcrypt.BCrypt;

public class StudentSignupManager {

	private ProfessorDAO professorDAO;
	private StudentDAO studentDAO;

	public StudentSignupManager() {
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
		}	}

	public boolean registerStudent(Student student) throws SQLException {
		// 비밀번호 해싱
		String hashedPassword = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt());
		student.setPassword(hashedPassword);
		return studentDAO.createStudent(student) > 0;
	}
}