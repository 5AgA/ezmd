package model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private int scheduleId; // 스케줄 ID
    private String scheduleTitle; // 스케줄 제목
    private LocalDateTime scheduleStart; // 시작 시간
    private LocalDateTime scheduleEnd; // 종료 시간
    private int scheduleRepeat; // 반복 요일 비트 값
    private String schedulePlace; // 장소
    private String scheduleMemo; // 메모
    private int categoryId; // 카테고리 ID
    private int professorId; // 교수 ID
    private int studentId; // 학생 ID

    // **기본 생성자**
    public Schedule() {}

    // **전체 필드를 포함한 생성자**
    public Schedule(int scheduleId, String scheduleTitle, LocalDateTime scheduleStart, LocalDateTime scheduleEnd,
                    int scheduleRepeat, String schedulePlace, String scheduleMemo, int categoryId, int professorId, int studentId) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
        this.scheduleRepeat = scheduleRepeat;
        this.schedulePlace = schedulePlace;
        this.scheduleMemo = scheduleMemo;
        this.categoryId = categoryId;
        this.professorId = professorId;
        this.studentId = studentId;
    }

    // **toString 메서드** (디버깅 용도)
    @Override
    public String toString() {
        return "Schedule [scheduleId=" + scheduleId + ", scheduleTitle=" + scheduleTitle
                + ", scheduleStart=" + scheduleStart + ", scheduleEnd=" + scheduleEnd + ", scheduleRepeat=" + scheduleRepeat
                + ", schedulePlace=" + schedulePlace + ", scheduleMemo=" + scheduleMemo + ", categoryId=" + categoryId
                + ", professorId=" + professorId + ", studentId=" + studentId + "]";
    }

    // **요일 관련 유틸리티 메서드**
    public static int convertRepeatDayToInt(List<String> repeatedDays) {
        int repeatDay = 0;
        if (repeatedDays == null) return repeatDay;

        for (String day : repeatedDays) {
            switch (day) {
                case "일": repeatDay |= 1; break;
                case "월": repeatDay |= 2; break;
                case "화": repeatDay |= 4; break;
                case "수": repeatDay |= 8; break;
                case "목": repeatDay |= 16; break;
                case "금": repeatDay |= 32; break;
                case "토": repeatDay |= 64; break;
            }
        }
        return repeatDay;
    }

    public static List<String> getRepeatedDays(int scheduleRepeat) {
        List<String> repeatedDays = new ArrayList<>();
        if ((scheduleRepeat & 1) > 0) repeatedDays.add("일");
        if ((scheduleRepeat & 2) > 0) repeatedDays.add("월");
        if ((scheduleRepeat & 4) > 0) repeatedDays.add("화");
        if ((scheduleRepeat & 8) > 0) repeatedDays.add("수");
        if ((scheduleRepeat & 16) > 0) repeatedDays.add("목");
        if ((scheduleRepeat & 32) > 0) repeatedDays.add("금");
        if ((scheduleRepeat & 64) > 0) repeatedDays.add("토");
        return repeatedDays;
    }

    // **Getter 및 Setter**
    public int getScheduleId() {
        return scheduleId;
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
        return scheduleStart;
    }

    public void setScheduleStart(LocalDateTime scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public LocalDateTime getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(LocalDateTime scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    public int getScheduleRepeat() {
        return scheduleRepeat;
    }

    public void setScheduleRepeat(int scheduleRepeat) {
        this.scheduleRepeat = scheduleRepeat;
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

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}