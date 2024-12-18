
package model.manager;

import java.sql.SQLException;

import model.dao.StudentDAO;
import model.dao.ProfessorDAO;
import model.domain.Student;
import model.domain.Professor;
import model.domain.Student;
import org.mindrot.jbcrypt.BCrypt;

public class StudentLoginManager {
	private final StudentDAO studentDAO;
	
	public StudentLoginManager() {
		studentDAO = new StudentDAO();
	}
	
	//이메일과 비밀번호로 로그인 인증
	public Object authenticate(String email, String password) throws SQLException {
		Student student = studentDAO.findStudPwdByEmail(email);
		if(student !=null){
			if (BCrypt.checkpw(password, student.getPassword())) {
				return student;
			}
		}
		return null;
	}
}
