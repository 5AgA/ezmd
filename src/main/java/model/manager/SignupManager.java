package model.manager;

import java.sql.SQLException;

import model.dao.UserDAO;
import model.domain.Student;
import model.domain.Professor;

public class SignupManager {
	
	private UserDAO userDAO;
	
	public SignupManager(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	//학생 회원가입 처리
	public boolean registerStudent(Student student) throws SQLException {
		//email 중복체크
		if(userDAO.findAllStudents().stream()
				.anyMatch(s -> s.getEmail().equals(student.getEmail()))) {
			System.out.println("이미 사용중인 이메일 입니다.");
			return false;
		}
		
		//회원가입 진행
		int result = userDAO.createStudent(student);
		return result > 0;
	}
	
	//교수 회원가입 처리
	public boolean registerProfessor(Professor professor) throws SQLException {
		//이메일 중복 체크
		if(userDAO.findAllProfessors().stream()
				.anyMatch(p -> p.getEmail().equals(professor.getEmail()))) {
			System.out.println("이미 사용 중인 이메일입니다.");
			return false;
		}
		
		// 회원가입 진행
		int result = userDAO.createProfessor(professor);
		return result > 0;
	}
}
