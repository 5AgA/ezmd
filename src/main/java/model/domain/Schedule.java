package model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class Schedule {

    private Integer scheduleId; // 스케줄 ID
    private String scheduleTitle; // 스케줄 제목
    private String scheduleStart; // 시작 시간
    private String scheduleEnd; // 종료 시간
    private String schedulePlace; // 장소
    private String scheduleMemo; // 메모
    private Integer categoryId; // 카테고리 ID
    private Integer userId;

    // **기본 생성자**
    public Schedule() {}

    // **전체 필드를 포함한 생성자**
    public Schedule(int scheduleId, String scheduleTitle, String scheduleStart, String scheduleEnd,
                    String schedulePlace, String scheduleMemo, int categoryId, int userId) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
        this.schedulePlace = schedulePlace;
        this.scheduleMemo = scheduleMemo;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    // **toString 메서드** (디버깅 용도)
    @Override
    public String toString() {
        return "Schedule [scheduleId=" + scheduleId + ", scheduleTitle=" + scheduleTitle
                + ", scheduleStart=" + scheduleStart + ", scheduleEnd=" + scheduleEnd
                + ", schedulePlace=" + schedulePlace + ", scheduleMemo=" + scheduleMemo + ", categoryId=" + categoryId
                + ", userId=" + userId + "]";
    }

    // LocalDateTime을 String으로 변환
    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    // String을 LocalDateTime으로 변환
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    // **Getter 및 Setter**
    public int getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public LocalDateTime getScheduleStart() {
        return parseDateTime(scheduleStart);
    }

    public void setScheduleStart(LocalDateTime scheduleStart) {
        this.scheduleStart = formatDateTime(scheduleStart);
    }

    public LocalDateTime getScheduleEnd() {
        return parseDateTime(scheduleEnd);
    }

    public void setScheduleEnd(LocalDateTime scheduleEnd) {
        this.scheduleEnd = formatDateTime(scheduleEnd);
    }

    public String getSchedulePlace() {
        return schedulePlace;
    }

    public void setSchedulePlace(String schedulePlace) {
        this.schedulePlace = schedulePlace;
    }

    public String getScheduleMemo() {
        return scheduleMemo;
    }

    public void setScheduleMemo(String scheduleMemo) {
        this.scheduleMemo = scheduleMemo;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}