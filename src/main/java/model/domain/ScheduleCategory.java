package model.domain;

public class ScheduleCategory {
    private int categoryId;
    private String categoryName;
    private String categoryColor;

    public ScheduleCategory() {}
    public ScheduleCategory(int categoryId, String categoryName, String categoryColor) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public String toString() {
        return "ScheduleCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryColor=" + categoryColor + "]";
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
}
