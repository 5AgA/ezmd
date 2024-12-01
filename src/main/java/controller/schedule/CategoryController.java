package controller.schedule;

import model.domain.ScheduleCategory;
import model.manager.CategoryManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/schedule/category")
public class CategoryController extends HttpServlet {

    private final CategoryManager categoryManager = new CategoryManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // `execute` 메서드 호출 및 반환된 뷰 처리
            String view = execute(request, response);
            request.getRequestDispatcher(view).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "스케줄 카테고리 처리 중 오류 발생");
        }
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                return createCategory(request, response);
            case "getAll":
                return getCategoryList(request, response);
            case "update":
                return updateCategory(request, response);
            case "delete":
                return deleteCategory(request, response);
            default:
                request.setAttribute("errorMessage", "Invalid action");
                return "error.jsp"; // 잘못된 요청에 대한 JSP 반환
        }
    }

    private String createCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String categoryName = request.getParameter("categoryName");
        String categoryColor = request.getParameter("categoryColor");

        ScheduleCategory newCategory = new ScheduleCategory();
        newCategory.setCategoryName(categoryName);
        newCategory.setCategoryColor(categoryColor);

        ScheduleCategory createdCategory = categoryManager.createCategory(newCategory);
        if (createdCategory != null) {
            request.setAttribute("message", "Category created successfully: " + createdCategory.toString());
            return "categorySuccess.jsp"; // 성공 시 JSP 반환
        } else {
            request.setAttribute("errorMessage", "Failed to create category");
            return "categoryError.jsp"; // 실패 시 JSP 반환
        }
    }

    private String getCategoryList(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryManager.getCategoryList());
        return "categoryList.jsp"; // 카테고리 목록을 보여줄 JSP
    }

    private String updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String categoryName = request.getParameter("categoryName");
        String categoryColor = request.getParameter("categoryColor");

        ScheduleCategory category = new ScheduleCategory();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        category.setCategoryColor(categoryColor);

        boolean success = categoryManager.updateCategory(category);
        if (success) {
            request.setAttribute("message", "Category updated successfully");
            return "categorySuccess.jsp"; // 성공 시 JSP 반환
        } else {
            request.setAttribute("errorMessage", "Failed to update category");
            return "categoryError.jsp"; // 실패 시 JSP 반환
        }
    }

    private String deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        boolean success = categoryManager.deleteCategory(categoryId);
        if (success) {
            request.setAttribute("message", "Category deleted successfully");
            return "categorySuccess.jsp"; // 성공 시 JSP 반환
        } else {
            request.setAttribute("errorMessage", "Failed to delete category");
            return "categoryError.jsp"; // 실패 시 JSP 반환
        }
    }
}
