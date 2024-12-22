package model.manager.user;
import model.dao.ProfessorDAO;
import model.dao.StudentDAO;
import model.domain.Professor;
import model.domain.Student;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class ChangePasswordManager {
    private ProfessorDAO professorDAO;
    private StudentDAO studentDAO;

    public ChangePasswordManager() {
        professorDAO = new ProfessorDAO();
        studentDAO = new StudentDAO();
    }

    /**
     * 비밀번호 변경 로직
     *
     * @param userType        "Professor" 또는 "Student"
     * @param userId          교수 ID 또는 학생 ID
     * @param currentPassword 현재 비밀번호
     * @param newPassword     새로운 비밀번호
     * @return 변경 성공 여부
     * @throws SQLException 데이터베이스 오류 발생 시
     */
    public boolean changePassword(String userType, int userId, String currentPassword, String newPassword) throws SQLException {
        if ("Professor".equalsIgnoreCase(userType)) {
            Professor professor = professorDAO.findProfessorById(userId);
            if (professor != null && BCrypt.checkpw(currentPassword, professor.getPassword())) {
                String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                return professorDAO.updatePassword(userId, hashedNewPassword) > 0;
            }
        } else if ("Student".equalsIgnoreCase(userType)) {
            Student student = studentDAO.findStudentById(userId);
            if (student != null && BCrypt.checkpw(currentPassword, student.getPassword())) {
                String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                return studentDAO.updatePassword(userId, hashedNewPassword) > 0;
            }
        }
        return false;
    }
}