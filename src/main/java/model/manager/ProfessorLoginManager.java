
package model.manager;

import java.sql.SQLException;

import model.dao.ProfessorDAO;
import model.domain.Professor;

public class ProfessorLoginManager {
	private ProfessorDAO professorDAO;
	
	public ProfessorLoginManager() {
		professorDAO = new ProfessorDAO();
	}
	
	//이메일과 비밀번호로 로그인 인증
	public Object authenticate(String email, String password) throws SQLException {
		//교수 테이블에서 이메일을 이용해서 사용자 확인
		Professor professor = professorDAO.findProfessorByEmail(email);
		if (professor != null && professor.getPassword().equals(password)) {
			return professor;
		}
		
		return null;
	}
}
