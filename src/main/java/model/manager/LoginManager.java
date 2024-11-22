package model.manager;

import java.sql.SQLException;

import model.dao.UserDAO;
import model.domain.Student;
import model.domain.Professor;

public class LoginManager {
	private UserDAO userDAO;
	
	public LoginManager() {
		userDAO = new UserDAO();
	}
	
	//이메일과 비밀번호로 로그인 인증
	public Object authenticate(String email, String password) throws SQLException {
		//학생과 교수 테이블에서 이메일을 이용해서 사용자 확인
		Student student = userDAO.findStudentByEmail(email);
		if (student != null && student.getPassword().equals(password)) {
			return student;
		}
		
		Professor professor = userDAO.findProfessorByEmail(email);
		if(professor != null && professor.getPassword().equals(password)) {
			return professor;
		}
		return null;
	}
}
