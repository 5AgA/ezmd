package model.manager.schedule;

import java.util.List;

import model.dao.CategoryDAO;
import model.domain.ScheduleCategory;

public class CategoryManager {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    // 카테고리 생성
    public ScheduleCategory createCategory(ScheduleCategory category) {
        return categoryDAO.create(category);
    }

    // 모든 카테고리 조회
    public List<ScheduleCategory> getCategoryList() {
        return categoryDAO.getCategoryList();
    }

    // 카테고리 업데이트
    public boolean updateCategory(ScheduleCategory category) {
        return categoryDAO.update(category) > 0; // 성공 시 true 반환
    }

    // 카테고리 삭제
    public boolean deleteCategory(int categoryId) {
        return categoryDAO.remove(categoryId) > 0; // 성공 시 true 반환
    }

    // 특정 카테고리 ID로 카테고리 찾기 (컨트롤러에서 필요할 수 있음)
    public ScheduleCategory findCategoryById(int categoryId) {
        return categoryDAO.findCategoryById(categoryId);
    }

    // 카테고리 이름 중복 확인
    public boolean isCategoryNameDuplicate(String categoryName) {
        return categoryDAO.getCategoryList()
                .stream()
                .anyMatch(category -> category.getCategoryName().equalsIgnoreCase(categoryName));
    }
}