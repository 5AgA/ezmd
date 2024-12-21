package model.dto;

import java.time.LocalDateTime;

public class InterviewDTO {
		
		private int interviewId;
	    private String studentName;
	    private String studentMajor;
	    private String studentId;
	    private LocalDateTime requestedDate;
	    private String interviewCategory;
	    private String interviewNote;
	    private LocalDateTime createdAt;
	    
	    
		public String getInterviewCategory() {
			return interviewCategory;
		}
		public void setInterviewCategory(String interviewCategory) {
			this.interviewCategory = interviewCategory;
		}
		public String getInterviewNote() {
			return interviewNote;
		}
		public void setInterviewNote(String interviewNote) {
			this.interviewNote = interviewNote;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public int getInterviewId() {
			return interviewId;
		}
		public void setInterviewId(int interviewId) {
			this.interviewId = interviewId;
		}
		public String getStudentName() {
			return studentName;
		}
		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}
		public String getStudentMajor() {
			return studentMajor;
		}
		public void setStudentMajor(String studentMajor) {
			this.studentMajor = studentMajor;
		}
		public String getStudentId() {
			return studentId;
		}
		public void setStudentId(String studentId) {
			this.studentId = studentId;
		}
		public LocalDateTime getRequestedDate() {
			return requestedDate;
		}
		public void setRequestedDate(LocalDateTime requestedDate) {
			this.requestedDate = requestedDate;
		}

	
}
