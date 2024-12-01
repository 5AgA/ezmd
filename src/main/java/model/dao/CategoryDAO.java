package model.dao;

import model.domain.ScheduleCategory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private JDBCUtil jdbcUtil = null;

    public CategoryDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // **1. Create (카테고리 생성)**
    public ScheduleCategory create(ScheduleCategory category) {
        String query = "INSERT INTO ScheduleCategory (category_id, category_name, category_color) " +
                "VALUES (category_seq.nextval, ?, ?)";

        String[] key = {"category_id"};
        try {
            jdbcUtil.setSqlAndParameters(query, new Object[] { category.getCategoryName(), category.getCategoryColor() });
            jdbcUtil.executeUpdate(key);
            ResultSet rs = jdbcUtil.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                category.setCategoryId(generatedKey);
            }
            return category;
        } catch (Exception e) {
            jdbcUtil.rollback();
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return null;
    }

    // **2. Update (카테고리 업데이트)**
    public int update(ScheduleCategory category) {
        String query = "UPDATE ScheduleCategory SET category_name=?, category_color=? WHERE category_id=?";
        Object[] param = new Object[] { category.getCategoryName(), category.getCategoryColor(), category.getCategoryId() };

        jdbcUtil.setSqlAndParameters(query, param);
        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // **3. Delete (카테고리 삭제)**
    public int remove(int categoryId) {
        String query = "DELETE FROM ScheduleCategory WHERE category_id=?";
        jdbcUtil.setSqlAndParameters(query, new Object[] { categoryId });
        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // **4. Get All Categories (모든 카테고리 조회)**
    public List<ScheduleCategory> getCategoryList() {
        String query = "SELECT * FROM ScheduleCategory";
        List<ScheduleCategory> categoryList = new ArrayList<>();
        jdbcUtil.setSqlAndParameters(query, null);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                categoryList.add(mapResultSetToCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return categoryList;
    }

    // **5. Find by ID (특정 ID로 카테고리 조회)**
    public ScheduleCategory findCategoryById(int categoryId) {
        String query = "SELECT * FROM ScheduleCategory WHERE category_id=?";
        jdbcUtil.setSqlAndParameters(query, new Object[] { categoryId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return mapResultSetToCategory(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // **6. ResultSet -> ScheduleCategory 변환**
    private ScheduleCategory mapResultSetToCategory(ResultSet rs) throws Exception {
        ScheduleCategory category = new ScheduleCategory();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setCategoryColor(rs.getString("category_color"));
        return category;
    }

    // **7. Name Duplicate Check (카테고리 이름 중복 확인)**
    public boolean isCategoryNameDuplicate(String categoryName) {
        String query = "SELECT COUNT(*) AS count FROM ScheduleCategory WHERE LOWER(category_name) = LOWER(?)";
        jdbcUtil.setSqlAndParameters(query, new Object[] { categoryName });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0; // 중복된 이름이 있으면 true 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }
}