package model.domain;

public class ScheduleCategory {
    private int categoryId;
    private String categoryName;
    private String categoryColor;
    private int userId;

    public ScheduleCategory() {}
    public ScheduleCategory(int categoryId, String categoryName, String categoryColor, int userId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.userId = userId;
    }

    public String toString() {
        return "ScheduleCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryColor=" + categoryColor + ", userId=" + userId + "]";
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryColor() {
        return categoryColor;
    }
    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
