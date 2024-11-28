
package model.manager;

import java.sql.SQLException;

import model.dao.StudentDAO;
import model.domain.Student;

public class StudentLoginManager {
	private StudentDAO studentDAO;
	
	public StudentLoginManager() {
		studentDAO = new StudentDAO();
	}
	
	//이메일과 비밀번호로 로그인 인증
	public Object authenticate(String email, String password) throws SQLException {
		//학생과 테이블에서 이메일을 이용해서 사용자 확인
		Student student = studentDAO.findStudentByEmail(email);
		if (student != null && student.getPassword().equals(password)) {
			return student;
		}
		
		return null;
	}
}
