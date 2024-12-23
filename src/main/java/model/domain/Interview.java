package model.domain;

import java.time.LocalDateTime;

public class Interview {
    private int interviewId;
    private LocalDateTime requestedDate;
    private String interviewCategory;
    private String interviewNote;
    private String interviewStatus;
    private String interviewNotice;
    private char isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int studentId;
    private String studentName;
    private int professorId;
    private String professorName;

    // 기본 생성자
    public Interview() {}
    // 모든 필드를 포함한 생성자
    public Interview(int interviewId, LocalDateTime requestedDate, String interviewCategory, String interviewNote,
                     String interviewStatus, String interviewNotice, char isCompleted, LocalDateTime createdAt, LocalDateTime updatedAt,
                     int studentId, int professorId) {
        this.interviewId = interviewId;
        this.requestedDate = requestedDate;
        this.interviewCategory = interviewCategory;
        this.interviewNote = interviewNote;
        this.interviewStatus = interviewStatus;
        this.interviewNotice = interviewNotice;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studentId = studentId;
        this.professorId = professorId;
    }
    public Interview(int interviewId, LocalDateTime requestedDate, String interviewCategory, String interviewNote, String interviewStatus, String isCompleted, int studentId, int professorId) {
    	this.interviewId = interviewId;
    	this.requestedDate = requestedDate;
    	this.interviewCategory = interviewCategory;
    	this.interviewNote = interviewNote;
    	this.interviewStatus = interviewStatus;
    	this.isCompleted = isCompleted.charAt(0);
    	this.studentId = studentId;
    	this.professorId = professorId;
    }
    public Interview(int interviewId, LocalDateTime requestedDate,int professorId, int studentId, String professorName) {
        this.interviewId = interviewId;
        this.requestedDate = requestedDate;
        this.studentId = studentId;
        this.professorId = professorId;
        this.professorName = professorName;
    }
    public Interview(int interviewId, LocalDateTime requestedDate, int studentId, String studentName,int professorId) {
        this.interviewId = interviewId;
        this.requestedDate = requestedDate;
        this.studentId = studentId;
        this.studentName = studentName;
        this.professorId = professorId;
    }

    // Getters and Setters
    public int getInterviewId() { return interviewId; }
    public void setInterviewId(int interviewId) { this.interviewId = interviewId; }

    public LocalDateTime getRequestedDate() { return requestedDate; }
    public void setRequestedDate(LocalDateTime requestedDate) { this.requestedDate = requestedDate; }

    public String getInterviewCategory() { return interviewCategory; }
    public void setInterviewCategory(String interviewCategory) { this.interviewCategory = interviewCategory; }

    public String getInterviewNote() { return interviewNote; }
    public void setInterviewNote(String interviewNote) { this.interviewNote = interviewNote; }

    public String getInterviewStatus() { return interviewStatus; }
    public void setInterviewStatus(String interviewStatus) { this.interviewStatus = interviewStatus; }

    public String getInterviewNotice() { return interviewNotice; }
    public void setInterviewNotice(String interviewNotice) { this.interviewNotice = interviewNotice; }

    public char getIsCompleted() { return isCompleted; }
    public void setIsCompleted(char isCompleted) { this.isCompleted = isCompleted; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }

    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }

}
