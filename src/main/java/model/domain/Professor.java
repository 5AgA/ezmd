package model.domain;

public class Professor {
    private int professorId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String dept;
    private String professorOffice;  // 연구실 위치
    private char deleted;            // 삭제 여부 (Y or N)

    public Professor() {}

    public Professor(int professorId, String name, String email, String password, 
                        String phone, String dept, String professorOffice, char deleted) {
        this.professorId = professorId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dept = dept;
        this.professorOffice = professorOffice;
        this.deleted = deleted;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
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
