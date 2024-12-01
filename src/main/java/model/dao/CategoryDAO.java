package model.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.domain.ScheduleCategory;

public class CategoryDAO {
	private JDBCUtil jdbcUtil = null;
	
	public CategoryDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	// Create 
	public ScheduleCategory create(ScheduleCategory category) {
		String query = "INSERT INTO ScheduleCategory (category_id, category_name, category_color) " +
                "VALUES (category_seq.nextval, ?, ?)";
		
		String[] key = {"category_id"};
        try {
        	jdbcUtil.setSqlAndParameters(query, new Object[]{category.getCategoryName(), category.getCategoryColor()});
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
	
	// Update
    public int update(ScheduleCategory category) {
        String query = "UPDATE ScheduleCategory SET category_name=?, category_color=? WHERE category_id=?";
        Object[] param = new Object[]{category.getCategoryName(), category.getCategoryColor(), category.getCategoryId()};

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
    
    // Delete
    public int remove(int categoryId) {
        String query = "DELETE FROM ScheduleCategory WHERE category_id=?";
        jdbcUtil.setSqlAndParameters(query, new Object[]{categoryId});
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

    // Read
    public List<ScheduleCategory> getCategoryList() {
        String qeury = "SELECT * FROM ScheduleCategory";
        List<ScheduleCategory> categoryList = new ArrayList<>();
        jdbcUtil.setSqlAndParameters(qeury, null);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                do {
                    ScheduleCategory c = 
                    		new ScheduleCategory(rs.getInt("category_id"), rs.getString("category_name"), rs.getString("category_color"));
                    categoryList.add(c);
                } while(rs.next());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return categoryList;
    }
	
}