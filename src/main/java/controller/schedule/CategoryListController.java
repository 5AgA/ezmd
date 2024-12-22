package controller.schedule;

import com.google.gson.Gson;
import controller.Controller;
import model.domain.ScheduleCategory;
import model.manager.schedule.ScheduleCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CategoryListController implements Controller {
    private ScheduleCategoryService categoryService;

    public CategoryListController() {
        categoryService = new ScheduleCategoryService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userIdParam = request.getParameter("userId");
        int userId;

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'userId' parameter");
            return null;
        }

        userId = Integer.parseInt(userIdParam);

        // 사용자 카테고리 가져오기
        List<ScheduleCategory> categories = categoryService.getCategoriesByUserId(userId);

        // JSON 응답
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new Gson().toJson(categories));
        return null; // JSON만 반환
    }
}