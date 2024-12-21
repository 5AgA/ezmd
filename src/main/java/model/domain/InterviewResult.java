package model.domain;

import java.sql.Timestamp;

public class InterviewResult {

    private int interviewId; //PK&FK
    private String interviewTopic;
    private String interviewSummary;
    private int reviewId;
	private String reviewOfInterview;
	private int reviewRating;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 기본 생성자
    public InterviewResult() {}

    // 모든 필드를 초기화하는 생성자
    public InterviewResult(int interviewId, String interviewTopic, String interviewSummary, int reviewId, String reviewOfInterview, int reviewRating,Timestamp createdAt, Timestamp updatedAt) {
        this.interviewId = interviewId;
        this.interviewTopic = interviewTopic;
        this.interviewSummary = interviewSummary;
    	this.reviewId = reviewId;
		this.reviewOfInterview = reviewOfInterview;
		this.reviewRating = reviewRating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter와 Setter 메서드
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public String getInterviewTopic() {
        return interviewTopic;
    }

    public void setInterviewTopic(String interviewTopic) {
        this.interviewTopic = interviewTopic;
    }

    public String getInterviewSummary() {
        return interviewSummary;
    }

    public void setInterviewSummary(String interviewSummary) {
        this.interviewSummary = interviewSummary;
    }

 // Getter와 Setter 메서드
 	public int getReviewId() {
 		return reviewId;
 	}

 	public void setReviewId(int reviewId) {
 		this.reviewId = reviewId;
 	}

 	public String getReviewOfInterview() {
 		return reviewOfInterview;
 	}

 	public void setReviewOfInterview(String reviewOfInterview) {
 		this.reviewOfInterview = reviewOfInterview;
 	}

 	public int getReviewRating() {
 		return reviewRating;
 	}

 	public void setReviewRating(int reviewRating) {
 		this.reviewRating = reviewRating;
 	}

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "InterviewResultDTO{" +
                "interviewId=" + interviewId +
                ", interviewTopic='" + interviewTopic + '\'' +
                ", interviewSummary='" + interviewSummary + '\'' +
                ", createAt=" + createdAt +
                ", updateAt=" + updatedAt +
                '}';
    }
}
