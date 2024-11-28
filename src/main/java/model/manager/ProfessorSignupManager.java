package model.manager;

import java.sql.SQLException;

import model.dao.ProfessorDAO;
import model.domain.Professor;

public class ProfessorSignupManager {
	
	private ProfessorDAO professorDAO;
	
	public ProfessorSignupManager() {
		this.professorDAO = new ProfessorDAO();
	}

	//교수 회원가입 처리
	public boolean registerProfessor(Professor professor) throws SQLException {
		//이메일 중복 체크
		if(professorDAO.findAllProfessors().stream()
				.anyMatch(p -> p.getEmail().equals(professor.getEmail()))) {
			System.out.println("이미 사용 중인 이메일입니다.");
			return false;
		}
		
		// 회원가입 진행
		int result = professorDAO.createProfessor(professor);
		return result > 0;
	}
}