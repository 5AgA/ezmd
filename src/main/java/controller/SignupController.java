package controller;

import model.dao.UserDAO;
import model.domain.Student;
import model.domain.Professor;
import model.manager.SignupManager;

public class SignupController {
	
	private SignupManager signupManager;
	
	public SignupController() {
		signupManager = new SignupManager(new UserDAO());
	}
	
	// 학생 회원가입
	public String signupStudent(Student student) {
		try {
			boolean result = signupManager.registerStudent(student);
			if(result) {
				return "학생 회원가입이 성공적으로 완료되었습니다.";
			}
			else {
				return "학생 회원가입 중 문제가 발생했습니다.";
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "회원가입 중 오류가 발생했습니다.";
		}
	}
	
	//교수 회원가입
	public String signupProfessor(Professor professor) {
		try {
			boolean result = signupManager.registerProfessor(professor);
			if(result) {
				return "교수 회원가입이 성공적으로 완료되었습니다.";
			}
			else {
				return "교수 회원가입 중 문제가 발생했습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "회원가입 중 오류가 발생했습니다.";
		}
	}
}
