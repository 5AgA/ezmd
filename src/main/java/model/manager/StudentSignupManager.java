
package model.manager;

import java.sql.SQLException;

import model.dao.StudentDAO;
import model.domain.Student;

public class StudentSignupManager {
	
	private StudentDAO studentDAO;
	
	public StudentSignupManager() {
		this.studentDAO = new StudentDAO();
	}

	//교수 회원가입 처리
	public boolean registerStudent(Student student) throws SQLException {
		//이메일 중복 체크
		if(studentDAO.findAllStudents().stream()
				.anyMatch(p -> p.getEmail().equals(student.getEmail()))) {
			System.out.println("이미 사용 중인 이메일입니다.");
			return false;
		}
		
		// 회원가입 진행
		int result = studentDAO.createStudent(student);
		return result > 0;
	}
}