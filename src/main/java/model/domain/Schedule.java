package model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private int scheduleId;
    private int scheduleType;
    private String scheduleTitle;
    private LocalDateTime scheduleStart;
    private LocalDateTime scheduleEnd;
    private int scheduleRepeat;
    private String schedulePlace;
    private String scheduleMemo;
    private int categoryId;
    private int professorId;
    private int studentId;

    public Schedule() {}
    public Schedule(int scheduleId, int scheduleType, String scheduleTitle, LocalDateTime scheduleStart, LocalDateTime scheduleEnd, int scheduleRepeat, String schedulePlace, String scheduleMemo, int categoryId, int professorId, int studentId) {
        this.scheduleId = scheduleId;
        this.scheduleType = scheduleType;
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
    public String toString() {
        return "Schedule [scheduleId=" + scheduleId + ", scheduleType=" + scheduleType + ", scheduleTitle=" + scheduleTitle + ", scheduleStart=" + scheduleStart + ", scheduleEnd=" + scheduleEnd + ", scheduleRepeat=" + scheduleRepeat + ", schedulePlace=" + schedulePlace + ", scheduleMemo=" + scheduleMemo + ", categoryId=" + categoryId + ", professorId=" + professorId + ", studentId=" + studentId + "]";
    }

    public String getScheduleTypeString(int scheduleType) {
        if (scheduleType == 0) {
            return "비정기적 일정";
        } else if (scheduleType == 1) {
            return "주간 일정";
        } else if (scheduleType == 2) {
            return "면담 일정";
        }
        return null;
    }

    public int convertRepeatDayToInt(List<String> repeatedDays) {
        int repeatDay = 0;
        if (repeatedDays == null) {
            return repeatDay;
        }
        for (String day : repeatedDays) {
            if (day.equals("일")) {
                repeatDay += 1;
            } else if (day.equals("월")) {
                repeatDay += 2;
            } else if (day.equals("화")) {
                repeatDay += 4;
            } else if (day.equals("수")) {
                repeatDay += 8;
            } else if (day.equals("목")) {
                repeatDay += 16;
            } else if (day.equals("금")) {
                repeatDay += 32;
            } else if (day.equals("토")) {
                repeatDay += 64;
            }
        }
        return repeatDay;
    }

    public List<String> getRepeatedDays(int scheduleRepeat) {
        List<String> repeatedDays = new ArrayList<String>();
        if (scheduleRepeat == 0) {
            return repeatedDays;
        }
        if (scheduleRepeat % 2 == 1) {
            repeatedDays.add("일");
        }
        if (scheduleRepeat % 4 >= 2) {
            repeatedDays.add("월");
        }
        if (scheduleRepeat % 8 >= 4) {
            repeatedDays.add("화");
        }
        if (scheduleRepeat % 16 >= 8) {
            repeatedDays.add("수");
        }
        if (scheduleRepeat % 32 >= 16) {
            repeatedDays.add("목");
        }
        if (scheduleRepeat % 64 >= 32) {
            repeatedDays.add("금");
        }
        if (scheduleRepeat % 128 >= 64) {
            repeatedDays.add("토");
        }
        return repeatedDays;
    }

    public int getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    public int getScheduleType() {
        return scheduleType;
    }
    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
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
