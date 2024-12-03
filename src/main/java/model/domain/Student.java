package model.domain;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String password;
    private String dept;
    private Integer grade;     // 학년
    private char deleted;      // 삭제 여부 (Y or N)

    public Student() {}

    public Student(int studentId, String name, String email, String password, 
                       String dept, Integer grade, char deleted) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dept = dept;
        this.grade = grade;
        this.deleted = deleted;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public char getDeleted() {
        return deleted;
    }

    public void setDeleted(char deleted) {
        this.deleted = deleted;
    }
}
