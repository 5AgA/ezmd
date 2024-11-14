package model.dto;

import java.sql.Timestamp;

public class InterviewResultDTO {

    private int interviewId;
    private String interviewTopic;
    private String interviewSummary;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 기본 생성자
    public InterviewResultDTO() {}

    // 모든 필드를 초기화하는 생성자
    public InterviewResultDTO(int interviewId, String interviewTopic, String interviewSummary, Timestamp createdAt, Timestamp updatedAt) {
        this.interviewId = interviewId;
        this.interviewTopic = interviewTopic;
        this.interviewSummary = interviewSummary;
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
