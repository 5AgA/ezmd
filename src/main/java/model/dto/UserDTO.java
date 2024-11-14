package model.dto;

public class UserDTO {
	private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String dept;
    private Integer grade;            // 학생일 경우 학년
    private String professorOffice;   // 교수일 경우 교수 연구실 위치
    private char deleted;             // 삭제 여부 (Y or N)	
    
    public UserDTO() {}
    
    public UserDTO(int userId, String name, String email, String password, 
            String phone, String dept, Integer grade, String professorOffice, char deleted) {
    	this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.dept = dept;
		this.grade = grade;
		this.professorOffice = professorOffice;
		this.deleted = deleted;
	}

    public int getUserId() {
    	return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getProfessorOffice() {
        return professorOffice;
    }

    public void setProfessorOffice(String professorOffice) {
        this.professorOffice = professorOffice;
    }

    public char getDeleted() {
        return deleted;
    }

    public void setDeleted(char deleted) {
        this.deleted = deleted;
    }
}
