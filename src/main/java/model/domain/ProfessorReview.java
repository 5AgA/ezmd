package model.domain;

import java.sql.Timestamp;

public class ProfessorReview {
    private int reviewId;
    private String reviewOfInterview;
    private int reviewRating;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int interviewId;

    // 기본 생성자
    public ProfessorReview() {}

    // 모든 필드를 초기화하는 생성자
    public ProfessorReview(int reviewId, String reviewOfInterview, int reviewRating, Timestamp createdAt, Timestamp updatedAt, int interviewId) {
        this.reviewId = reviewId;
        this.reviewOfInterview = reviewOfInterview;
        this.reviewRating = reviewRating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.interviewId = interviewId;
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

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public String toString() {
        return "ProfessorReviewDTO{" +
                "reviewId=" + reviewId +
                ", reviewOfInterview='" + reviewOfInterview + '\'' +
                ", reviewRating=" + reviewRating +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", interviewId=" + interviewId +
                '}';
    }
}
